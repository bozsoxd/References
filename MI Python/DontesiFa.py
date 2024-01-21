import math

import numpy as np  # (működik a Moodle-ben is)
import csv


######################## 1. feladat, entrópiaszámítás #########################
def get_entropy(n_cat1: int, n_cat2: int) -> float:
    entropy = 0
    if n_cat1 == 0 or n_cat2 == 0:
        return 0
    total = n_cat1 + n_cat2
    if total == 0:
        return 0
    p1 = n_cat1 / total
    p2 = n_cat2 / total

    if p1 == 0 or p1 == 1 or p2 == 0 or p2 == 1:
        return 0

    entropy = - p1 * math.log2(p1) - p2 * math.log2(p2)
    return entropy


###################### 2. feladat, optimális szeparáció #######################
def get_best_separation(features: list,
                        labels: list) -> (int, int):
    best_feature_index, best_threshold = calculate_information_gain(features, labels)
    return best_feature_index, best_threshold


def calculate_information_gain(feature_values, labels):
    total_entropy = get_entropy(np.sum(labels == 0), np.sum(labels == 1))
    total_samples = len(labels)

    best_gain = 0
    best_feature_index = -1
    best_threshold = None

    for feature_index in range(feature_values.shape[1]):
        unique_values = np.unique(feature_values[:, feature_index])
        for threshold in unique_values:
            left_mask = feature_values[:, feature_index] <= threshold
            right_mask = ~left_mask

            left_labels = labels[left_mask]
            right_labels = labels[right_mask]

            if len(left_labels) > 0 and len(right_labels) > 0:
                left_entropy = get_entropy(np.sum(left_labels == 0), np.sum(left_labels == 1))
                right_entropy = get_entropy(np.sum(right_labels == 0), np.sum(right_labels == 1))

                information_gain = total_entropy - (
                        (len(left_labels) / total_samples) * left_entropy +
                        (len(right_labels) / total_samples) * right_entropy
                )

                if information_gain > best_gain:
                    best_gain = information_gain
                    best_feature_index = feature_index
                    best_threshold = threshold

    return best_feature_index, best_threshold


################### 3. feladat, döntési fa implementációja ####################
def main():
    train_data = read_csv('train.csv')
    features = train_data[:, :-1]
    labels = train_data[:, -1]
    deceision_tree = build_decision_tree(features, labels)

    test_data = read_csv('test.csv')

    predictions = make_predictions(deceision_tree, test_data)
    write_csv(predictions)

    return 0


class DecisionNode:
    def __init__(self, feature_index=None, threshold=None, left=None, right=None, value=None):
        self.feature_index = feature_index
        self.threshold = threshold
        self.left = left
        self.right = right
        self.value = value


def build_decision_tree(features, labels):
    if np.all(labels == labels[0]):
        return DecisionNode(value=labels[0])

    feature_index, threshold = calculate_information_gain(features, labels)

    if feature_index == -1:
        return DecisionNode(value=max(set(labels), key=labels.tolist().count))

    left_mask = features[:, feature_index] <= threshold
    right_mask = ~left_mask

    left_tree = build_decision_tree(features[left_mask], labels[left_mask])
    right_tree = build_decision_tree(features[right_mask], labels[right_mask])

    return DecisionNode(feature_index, threshold, left_tree, right_tree)


def predict(tree, sample):
    if tree.value is not None:
        return tree.value
    if sample[tree.feature_index] <= tree.threshold:
        return predict(tree.left, sample)
    else:
        return predict(tree.right, sample)


def make_predictions(tree, test_data):
    predictions = []
    for sample in test_data:
        prediction = predict(tree, sample)
        predictions.append(prediction)
    return np.array(predictions)


def read_csv(file_path):
    data = []
    with open(file_path, 'r') as file:
        csv_reader = csv.reader(file)
        for row in csv_reader:
            data.append(row)
    return np.array(data, dtype=float)


def write_csv(predictions, output_file='results.csv'):
    with open(output_file, 'w', newline='') as file:
        csv_writer = csv.writer(file)
        for prediction in predictions:
            csv_writer.writerow([int(prediction)])


if __name__ == "__main__":
    main()
