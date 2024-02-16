## Description

Ez egy Pythonban írodott döntési fa megoldás, amely a mestreséges intelligencia alapjait képzi. A fa ingatlanok árak és korábbi vásárlások alapján próbálja predikálni, hogy az adott ingatlant megvennék-e.

## Code fragments

```python title="Entrópia számolás"
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
```
:::note
Az entrópia megadja az adott csomópont bizonytalanságát
:::


```python title="Döntési fa építése"
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
```



```python title="Predikció"
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
```