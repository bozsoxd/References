import torch
import torch.nn as nn

class CNNClassifier(nn.Module):
    def __init__(self, num_classes):
        super(CNNClassifier, self).__init__()
        self.classes = num_classes
        self.conv1 = nn.Conv2d(in_channels=3, out_channels=4, kernel_size=3, padding=1)
        self.bn1 = nn.BatchNorm2d(4)
        self.conv2 = nn.Conv2d(in_channels=4, out_channels=4, kernel_size=3, padding=1)
        self.bn2 = nn.BatchNorm2d(4)
        self.conv3 = nn.Conv2d(in_channels=4, out_channels=8, kernel_size=3, padding=1)
        self.bn3 = nn.BatchNorm2d(8)
        self.conv4 = nn.Conv2d(in_channels=8, out_channels=8, kernel_size=3, padding=1)
        self.bn4 = nn.BatchNorm2d(8)
        self.conv5 = nn.Conv2d(in_channels=8, out_channels=16, kernel_size=3, padding=1)
        self.bn5 = nn.BatchNorm2d(16)
        self.conv6 = nn.Conv2d(in_channels=16, out_channels=16, kernel_size=3, padding=1)
        self.bn6 = nn.BatchNorm2d(16)
        self.conv7 = nn.Conv2d(in_channels=16, out_channels=32, kernel_size=3, padding=1)
        self.bn7 = nn.BatchNorm2d(32)
        self.conv8 = nn.Conv2d(in_channels=32, out_channels=32, kernel_size=3, padding=1)
        self.bn8 = nn.BatchNorm2d(32)

        self.pool = nn.MaxPool2d(kernel_size=2, stride=2)

        self.fc1 = nn.Linear(32 * 4 * 4, 256)
        self.fc2 = nn.Linear(256, 128)
        self.fc3 = nn.Linear(128, num_classes)

    def forward(self, x):
        x = self.conv1(x)
        x = self.bn1(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv2(x)
        x = self.bn2(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv3(x)
        x = self.bn3(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv4(x)
        x = self.bn4(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv5(x)
        x = self.bn5(x)
        x = nn.functional.relu(x)

        x = self.pool(x)

        x = self.conv6(x)
        x = self.bn6(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv7(x)
        x = self.bn7(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = x.view(-1, 32 * 4 * 4)
        x = self.fc1(x)
        x = nn.functional.relu(x)

        x = self.fc2(x)
        x = nn.functional.relu(x)

        x = self.fc3(x)
        return x