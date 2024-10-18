import torch
import torch.nn as nn

class CNNClassifier(nn.Module):
    def __init__(self, num_classes=6):
        super(CNNClassifier, self).__init__()

        # Define convolutional layers
        self.conv1 = nn.Conv2d(in_channels=3, out_channels=4, kernel_size=3, padding=1)
        self.conv2 = nn.Conv2d(in_channels=4, out_channels=4, kernel_size=3, padding=1)
        self.conv3 = nn.Conv2d(in_channels=4, out_channels=8, kernel_size=3, padding=1)
        self.conv4 = nn.Conv2d(in_channels=8, out_channels=8, kernel_size=3, padding=1)
        self.conv5 = nn.Conv2d(in_channels=8, out_channels=16, kernel_size=3, padding=1)
        self.conv6 = nn.Conv2d(in_channels=16, out_channels=16, kernel_size=3, padding=1)
        self.conv7 = nn.Conv2d(in_channels=16, out_channels=32, kernel_size=3, padding=1)
        self.conv8 = nn.Conv2d(in_channels=32, out_channels=32, kernel_size=3, padding=1)


        self.pool = nn.MaxPool2d(kernel_size=2, stride=2)

        self.fc1 = nn.Linear(32 * 4 * 4, 256)
        self.fc2 = nn.Linear(256, 128)
        self.fc3 = nn.Linear(128, num_classes)

        self.dropout = nn.Dropout(p=0.5)

    def forward(self, x):
        x = self.conv1(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv2(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv3(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv4(x)
        x = nn.functional.relu(x)
        x = self.pool(x)
        x = self.conv5(x)
        x = nn.functional.relu(x)

        x = self.pool(x)

        x = self.conv6(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = self.conv7(x)
        x = nn.functional.relu(x)
        x = self.pool(x)

        x = x.view(-1, 32 * 4 * 4)
        x = self.fc1(x)
        x = nn.functional.relu(x)
        # x = self.dropout(x)

        x = self.fc2(x)
        x = nn.functional.relu(x)
        # x = self.dropout(x)
        
        x = self.fc3(x)
        #x = nn.Sigmoid()(x)
        return x