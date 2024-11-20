import torch
from torch.utils.data import DataLoader
from tqdm import tqdm
from model import CNNClassifier
from dcm_sampler import OnesProportionSampler
from dataset import HeadCTDataset
from torchvision import transforms
import gradio as gr



def test_any(model, dataloader, batch_number, device):
    model.eval()
    total = 0
    correct = 0
    print("TESTING")
    with torch.no_grad():
        for inputs, labels in tqdm(dataloader, total=batch_number):
            inputs, labels = inputs.to(device), labels.to(device)
            outputs = torch.sigmoid(model(inputs))
            outputs = torch.round(outputs)
            label_anys = labels[:, 5]
            output_anys = outputs[:, 5]
            equal_elements = (label_anys == output_anys).sum().item()
            correct += equal_elements
            total += labels.size(0)

        percentage = (correct / total) * 100                
        print(f"Percentage of equal anys: {percentage:.6f}%")
        return percentage

def test_x_class(model, dataloader, batch_number, device, x):
    model.eval()

    class_correct = [0] * x
    class_total = [0] * x

    print("TESTING")
    with torch.no_grad():
        for inputs, labels in tqdm(dataloader, total=batch_number):
            inputs, labels = inputs.to(device), labels.to(device)
            outputs = torch.sigmoid(model(inputs))
            outputs = torch.round(outputs)
            labels_x = labels[:, :x]
            outputs_x = outputs[:, :x]
            for i in range(x):
                correct = (outputs_x[:, i] == labels_x[:, i]).sum().item()
                total = labels_x[:, i].size(0)
                class_correct[i] += correct
                class_total[i] += total

        
        class_accuracy = [correct / total for correct, total in zip(class_correct, class_total)]
        return class_accuracy




def test_one(img):
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    model = CNNClassifier(num_classes=6).to(device)
    model.load_state_dict(torch.load('model_6_class.pth', weights_only=True, map_location=device))
    model.eval()
    img = transforms.ToTensor()(img)
    img = img.unsqueeze(0)
    img = img.to(device)

    class_labels = ["epidural", "intraparenchymal", "intraventricular", "subarachnoid", "subdural", "any"]

    with torch.no_grad():
        output = torch.sigmoid(model(img))
        confidences = output.tolist()[0]
        result_dict = dict(zip(class_labels, confidences))

    return result_dict

iface = gr.Interface(
    fn=test_one,
    inputs=gr.Image(label="Imput image"),
    outputs=gr.Label(num_top_classes=6),
    title="ICH detection"
)


def test_result_ratios(model, dataloader, batch_number, device, x):
    model.eval()

    class_tp = [0] * x
    class_tn = [0] * x
    class_fp = [0] * x
    class_fn = [0] * x

    print("TESTING")
    with torch.no_grad():
        for inputs, labels in tqdm(dataloader, total=batch_number):
            inputs, labels = inputs.to(device), labels.to(device)
            outputs = torch.sigmoid(model(inputs))
            outputs = torch.round(outputs)
            labels_x = labels[:, :x]
            outputs_x = outputs[:, :x]

            for i in range(x):
                for j in range(len(labels_x)):
                    if outputs_x[j, i] == 1 and labels_x[j, i] == 1:
                        class_tp[i] += 1
                    elif outputs_x[j, i] == 0 and labels_x[j, i] == 0:
                        class_tn[i] += 1
                    elif outputs_x[j, i] == 1 and labels_x[j, i] == 0:
                        class_fp[i] += 1
                    elif outputs_x[j, i] == 0 and labels_x[j, i] == 1:
                        class_fn[i] += 1
    class_labels = ["epidural", "intraparenchymal", "intraventricular", "subarachnoid", "subdural", "any"]
    for i in range(x):
        precision = class_tp[i] / (class_tp[i] + class_fp[i] + (1e-8)) if (class_tp[i] + class_fp[i]) > 0 else 0

        recall = class_tp[i] / (class_tp[i] + class_fn[i] + (1e-8)) if (class_tp[i] + class_fn[i]) > 0 else 0

        f1 = 2 * (precision * recall) / (precision + recall + (1e-8)) if (precision + recall) > 0 else 0

        print(f"{class_labels[i]} performance:")
        print(f"\tTrue Positives (TP): {class_tp[i]}")
        print(f"\tTrue Negatives (TN): {class_tn[i]}")
        print(f"\tFalse Positives (FP): {class_fp[i]}")
        print(f"\tFalse Negatives (FN): {class_fn[i]}")
        print(f"\tPrecision: {precision:.4f}")
        print(f"\tRecall: {recall:.4f}")
        print(f"\tF1 Score: {f1:.4f}")
        print("-" * 20)


if __name__ == "__main__":
    
    
    # iface.launch()
    

    loaded_device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    loaded_model = CNNClassifier(num_classes=6).to(loaded_device)
    loaded_model.load_state_dict(torch.load('model_6_class.pth', weights_only=True, map_location=loaded_device))
    dataset = HeadCTDataset("stage_2_train_png", "id_vectors.csv")
    labels = dataset.get_labels()

    used_batches_count = 1000
    siez_of_batch = 64
    
    sampler = OnesProportionSampler(labels=labels, num_samples=siez_of_batch*used_batches_count)
    test_dataloader = DataLoader(dataset=dataset, batch_size=siez_of_batch, sampler=sampler)
    test_result_ratios(model=loaded_model, dataloader=test_dataloader, batch_number=used_batches_count, device=loaded_device, x=loaded_model.classes)

