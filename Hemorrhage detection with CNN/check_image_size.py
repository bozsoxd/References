import os
import PIL.Image
import torch
from torchvision import transforms


def delete_non_512x512_png(directory):
  counter = 0
  for filename in os.listdir(directory):
    counter += 1
    print(counter)
    if filename.endswith('.png'):
      filepath = os.path.join(directory, filename)
      try:
        image = PIL.Image.open(filepath)
        image = transforms.ToTensor()(image)
        if image.shape != (3, 512, 512):
            os.remove(filepath)
            print(f"Deleted {filepath}: not 512x512")
      except Exception as e:
        print(f"Error processing {filepath}: {e}")

if __name__ == "__main__":
  directory = input("Enter the directory path: ")
  delete_non_512x512_png(directory)