import boto3
import os
from tqdm import tqdm
from multiprocessing import Pool, Manager


def upload_file(file_path, bucket_name, prefix):
    s3 = boto3.client('s3')
    s3.upload_file(file_path, bucket_name, prefix + os.path.basename(file_path))


def upload_files(folder_path, bucket_name, prefix):
    
    files = [os.path.join(folder_path, f) for f in os.listdir(folder_path) if f.endswith('.png')]
    with Pool() as pool:
        pool.starmap(upload_file, [(f, bucket_name, prefix) for f in files])


if __name__ == '__main__':
    folder_path = 'stage_2_train_png'
    bucket_name = 'sagemaker-eu-north-1-575108954822'
    prefix = 'pngs/'

    upload_files(folder_path, bucket_name, prefix)