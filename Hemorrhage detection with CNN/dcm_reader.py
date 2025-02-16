import pydicom
import matplotlib.pyplot as plt
import os
import numpy as np
import cv2
import argparse
import multiprocessing


def read_dicom_files(input_dir, output_dir):

    with multiprocessing.Pool() as pool:
        pool.starmap(read_dicom_file, [(filename, os.path.join(input_dir, filename) , output_dir) for filename in os.listdir(input_dir) if filename.lower().endswith('.dcm')])



def read_dicom_file(file_name, file_path, output_dir):
    try:
        ds = pydicom.dcmread(file_path)
        ds.PatientName = "Anonymous"
        ds.PatientID = "ANON"
        ds.PatientBirthDate = "00000000"
        ds.PatientSex = "O"
        combined_image = create_windows(ds)
        output_file_path = os.path.join(output_dir, file_name[3:-4] + ".png")
        cv2.imwrite(output_file_path, combined_image)
    except Exception as e:
        print(f"Error reading {file_path}: {e}")



    


def display_dicom_image(dicoms):
    if dicoms:
        plt.imshow(dicoms[50].pixel_array, cmap='gray')
        plt.title(f"Patient: {dicoms[0].PatientName} | Study: {dicoms[0].StudyDescription}")
        plt.show()        
    else:
        print("No DICOM dataset available")


def create_windows(ds):
        pixel_array = ds.pixel_array.astype(np.int16)

        brain_min = 1040
        brain_max = 1080

        subdural_min = 1080
        subdural_max = 1200

        bone_min = 1600
        bone_max = 2800

        brain_window = np.clip(pixel_array, brain_min, brain_max)
        subdural_window = np.clip(pixel_array, subdural_min, subdural_max)
        bone_window = np.clip(pixel_array, bone_min, bone_max)
        

        normalized_brain_window = (brain_window - brain_min) / (brain_max - brain_min)
        normalized_brain_window = np.clip(normalized_brain_window, 0, 1)
        rgb_brain_window = normalized_brain_window * 255
        rgb_brain_window = rgb_brain_window.astype(np.int8)
        rgb_brain_window = np.clip(rgb_brain_window, 0, 127)
        rgb_brain_window = rgb_brain_window.astype(np.int16)
        rgb_brain_window = np.multiply(rgb_brain_window, 2)

        normalized_subdural_window = (subdural_window - subdural_min) / (subdural_max - subdural_min)
        normalized_subdural_window = np.clip(normalized_subdural_window, 0, 1)
        rgb_subdural_window = normalized_subdural_window * 255
        rgb_subdural_window = rgb_subdural_window.astype(np.int8)
        rgb_subdural_window = np.clip(rgb_subdural_window, 0, 127)
        rgb_subdural_window = rgb_subdural_window.astype(np.int16)
        rgb_subdural_window = np.multiply(rgb_subdural_window, 2)

        normalized_bone_window = (bone_window - bone_min) / (bone_max - bone_min)
        normalized_bone_window = np.clip(normalized_bone_window, 0, 1)
        rgb_bone_window  = normalized_bone_window * 255
        rgb_bone_window = rgb_bone_window.astype(np.int8)
        rgb_bone_window = np.clip(rgb_bone_window, 0, 127)
        rgb_bone_window = rgb_bone_window.astype(np.int16)
        rgb_bone_window = np.multiply(rgb_bone_window, 2)

        combined_image = np.concatenate([rgb_bone_window[:,:,np.newaxis], rgb_subdural_window[:,:,np.newaxis], rgb_brain_window[:,:,np.newaxis]],2)

        return combined_image

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--input_dir", type=str, help="Input .DCM directory path")
    parser.add_argument("--output_dir", type=str, help="Output .png directory path")
    
    args = parser.parse_args()

    read_dicom_files(input_dir=args.input_dir, output_dir=args.output_dir)