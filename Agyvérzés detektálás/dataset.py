import torch
import os
from torch.utils.data import Dataset
import csv_reader
from torchvision import transforms
from PIL import Image

class HeadCTDataset(Dataset):
    def __init__(self, img_dir, formated_csv):
        self.image_dir = img_dir
        self.label_dictionary = csv_reader.read_csv_dictionary_back(formated_csv)

        file_names = os.listdir(img_dir)
        id_list = []
        for  file_name in file_names:
            extracted_id = file_name[0:-4]
            if extracted_id in self.label_dictionary:
                id_list.append(extracted_id)
        self.dcm_id_list = id_list

        # self.transform = transforms.Compose([
        #     transforms.RandomHorizontalFlip(),
        #     transforms.RandomRotation(15)
        # ])

    def __len__(self):
        return len(self.dcm_id_list)

    def __getitem__(self, idx):
        dcm_id = self.dcm_id_list[idx]
        image_path = os.path.join(self.image_dir, dcm_id + ".png")        

        image = Image.open(image_path)
        # image = self.transform(image)
        image = transforms.ToTensor()(image)
        if image.shape != (3, 512, 512):
            print(self.label_dictionary[dcm_id])
        label = torch.tensor(self.label_dictionary[dcm_id])


        return image, label
    
    def get_labels(self):
        labels = []
        for dcm_id in self.dcm_id_list:
            labels.append(self.label_dictionary[dcm_id])
        return torch.tensor(labels)