import torch
from torch.utils.data import DataLoader
from tqdm import tqdm
from model import CNNClassifier
from dcm_sampler import OnesProportionSampler
from dataset import HeadCTDataset



def test(model, dataloader, batch_number, device):
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

if __name__ == "__main__":
    loaded_device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    loaded_model = CNNClassifier(num_classes=6).to(loaded_device)
    loaded_model.load_state_dict(torch.load('model_test.pth', weights_only=True, map_location=loaded_device))
    dataset = HeadCTDataset("stage_2_train_png", "id_vectors.csv")
    labels = dataset.get_labels()

    used_batches_count = 1000
    siez_of_batch = 64
    
    sampler = OnesProportionSampler(labels=labels, num_samples=siez_of_batch*used_batches_count)
    test_dataloader = DataLoader(dataset=dataset, batch_size=siez_of_batch, sampler=sampler)
    test(model=loaded_model, dataloader=test_dataloader, batch_number=used_batches_count, device=loaded_device)

