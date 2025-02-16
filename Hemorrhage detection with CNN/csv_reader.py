import pandas as pd
import csv
from tqdm import tqdm

def read_csv(name, header):
    return pd.read_csv(name, header = header)


def csv_to_dictionary(df):
  

  # Extract the ID prefix and create a new column
  df['ID'] = df['ID'].str.extract(r'(\w+)_(\w+)_(\w+)')[1]

  # Group the data by ID and create a list of vectors
  id_vectors = {}
  for _, group in tqdm(df.groupby('ID'), desc="Processing csv"):
    vector = group['Label'].values.tolist()    
    if len(vector) != 6: continue
    vector = vector[:-1]
    id_vectors[_] = [float(label) for label in vector]

  df_vectors = pd.DataFrame.from_dict(id_vectors, orient='index')
  df_vectors.to_csv('id_vectors_no_any.csv')




def read_csv_dictionary_back(csv_name):
  print("reading csv to dictionary")
  result = {}
  with open(csv_name, 'r') as f:
    reader = csv.reader(f)
    next(reader)  # Skip the header row
    for row in reader:
      key = row[0]
      values = [float(value) for value in row[1:]]
      result[key] = values
  print("csv red")
  return result

if __name__ == "__main__":
    csv_to_dictionary(read_csv("stage_2_train.csv", 0))
    # id_vector = read_csv_dictionary_back("id_vectors.csv")
    # print("a")
