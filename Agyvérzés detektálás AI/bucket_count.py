import boto3
import os
from tqdm import tqdm
from multiprocessing import Pool, Manager

def count_files_in_s3_folder(bucket_name, folder_path):
    s3 = boto3.client('s3')

    paginator = s3.get_paginator('list_objects_v2')
    page_iterator = paginator.paginate(Bucket=bucket_name, Prefix=folder_path)


    file_count = 0
    for page in page_iterator:
        if 'Contents' in page:
            file_count += len(page['Contents'])

    return file_count


if __name__ == '__main__':
    folder_path = 'stage_2_train_png'
    bucket_name = 'sagemaker-eu-north-1-575108954822'
    prefix = 'pngs/'

    count = count_files_in_s3_folder(bucket_name, folder_path)
    print(count)