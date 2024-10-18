import torch
import torch.nn as nn
import torch.optim as optim
from torch.optim.lr_scheduler import CyclicLR
from model import CNNClassifier
from dataset import HeadCTDataset
from torch.utils.data import DataLoader
from dcm_sampler import OnesProportionSampler
from tqdm import tqdm
from test import test


def train(model, dataloader, num_epochs, optimizer, scheduler, batch_number):
    criterion = nn.BCEWithLogitsLoss(pos_weight = torch.FloatTensor([1.0, 1.0, 1.0, 1.0, 1.0, 1.0]).cuda())
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    model.to(device)

    best_loss = float('inf')
    best_percentage = 0.0
    early_stop_count = 0
    patience=10
    
    
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

        percentage = test(model, dataloader, batch_number, device)

        print(f"Epoch {epoch+1}/{num_epochs}, Loss: {loss.item():.8f}, Percentage: {percentage:.6f}")

        if loss.item() < best_loss:
            best_loss = loss.item()
            early_stop_count = 0
            
        if percentage > best_percentage:
             best_percentage = percentage
             early_stop_count = 0

        if early_stop_count != 0:
            early_stop_count += 1

        if early_stop_count >= patience:
            print(f"Early stopping triggered after {early_stop_count} epochs with no improvement.")
            break

       
if __name__ == "__main__":
    dataset = HeadCTDataset("stage_2_train_png", "id_vectors.csv")

    used_batches_count = 1000
    siez_of_batch = 64

    labels = dataset.get_labels()
    sampler = OnesProportionSampler(labels=labels, num_samples=siez_of_batch*used_batches_count)

    train_dataloader = DataLoader(dataset=dataset, batch_size=siez_of_batch, sampler=sampler)

    model = CNNClassifier(num_classes=6)
    optimizer = optim.Adam(model.parameters(), lr=0.0005, betas=(0.9, 0.999), eps=1e-08, weight_decay=0.00002)   
    scheduler = CyclicLR(optimizer, base_lr=5e-4, max_lr=1e-5, cycle_momentum=False)
    train(model, train_dataloader, num_epochs=10000, optimizer=optimizer, scheduler=scheduler, batch_number=used_batches_count)
    torch.save(model.state_dict(), "model_test1.pth")



