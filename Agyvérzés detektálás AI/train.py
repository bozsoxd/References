import torch
import torch.nn as nn
import torch.optim as optim
from torch.optim.lr_scheduler import OneCycleLR
from model import CNNClassifier
from dataset import HeadCTDataset
from torch.utils.data import DataLoader
from dcm_sampler import OnesProportionSampler
from tqdm import tqdm
from test import test_x_class
import matplotlib.pyplot as plt
import argparse


def train(model, dataloader, num_epochs, optimizer, scheduler, batch_number):
    if(torch.cuda.is_available()):
        pos_weight = torch.ones(model.classes, dtype=torch.float32).cuda()
        criterion = nn.BCEWithLogitsLoss(pos_weight = pos_weight.cuda())
    else:
        pos_weight = torch.ones(model.classes, dtype=torch.float32)
        criterion = nn.BCEWithLogitsLoss(pos_weight = pos_weight)
    
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    model.to(device)
    print(device)

    best_percentages = []
    early_stop_count = 0
    patience=1
    

    for epoch in range(num_epochs):
        model.train()
        print("TRAINING")
        for inputs, labels in tqdm(dataloader, total=batch_number):
            inputs, labels = inputs.to(device), labels.to(device)
            optimizer.zero_grad()
            outputs = model(inputs)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()
            scheduler.step()


        if epoch % 20 == 0:
            percentages = test_x_class(model, dataloader, batch_number, device, model.classes)
            percentages = compare_vectors(best_percentages, percentages, model.classes)
            if not best_percentages or percentages != best_percentages:
                best_percentages = percentages
                early_stop_count = 0
            else:
                early_stop_count += 1

        print(f"Epoch {epoch+1}/{num_epochs}, Loss: {loss.item():.8f}")

        if early_stop_count >= patience:
            print(f"Early stopping triggered after {early_stop_count} epochs with no improvement.")
            break
    
def compare_vectors(old, new, length):
    class_labels = ["epidural", "intraparenchymal", "intraventricular", "subarachnoid", "subdural", "any"]
    bests = []
    if not old:
        for i in range(length):
            print(f"{class_labels[i]}: {new[i]*100.0} %\n")
        return new

    for i in range(length):
        if new[i] > old[i]:
            bests.append(new[i])
        else:
            bests.append(old[i])
        print(f"{class_labels[i]}: {bests[i]*100.0} %\n")

    return bests  
       
if __name__ == "__main__":

    parser = argparse.ArgumentParser()
    parser.add_argument("--input_dir", type=str, default="stage_2_train_png", help="Input .png directory path")
    parser.add_argument("--labels", type=str, default="id_vectors.csv", help="Training labels with .csv")
    parser.add_argument("--output_model", type=str, default="model_6_class.pth", help="Saved output model's name with .pth")
    parser.add_argument("--class_number", type=int, default=6, help="Saved output model's name with .pth")
    
    args = parser.parse_args()


    print("Arguments:")
    for arg, value in vars(args).items():
        print(f"  {arg}: {value}")

    dataset = HeadCTDataset(args.input_dir, args.labels)

    used_batches_count = 1000
    siez_of_batch = 64

    labels = dataset.get_labels()
    sampler = OnesProportionSampler(labels=labels, num_samples=siez_of_batch*used_batches_count)

    train_dataloader = DataLoader(dataset=dataset, batch_size=siez_of_batch, sampler=sampler)

    model = CNNClassifier(num_classes=args.class_number)
    optimizer = optim.Adam(model.parameters(), lr=0.0005, betas=(0.9, 0.999), eps=1e-08, weight_decay=0.00002)   
    scheduler = optim.lr_scheduler.CosineAnnealingLR(optimizer, T_max=50)
    train(model, train_dataloader, num_epochs=1000, optimizer=optimizer, scheduler=scheduler, batch_number=used_batches_count)
    torch.save(model.state_dict(), args.output_model)