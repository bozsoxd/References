---
title: AI Projects
sidebar_position: 5
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

|Folder(s)|about|
|-|-|
|Connect4|Mini-Max algoritmus Connect4 játékhoz|
|Decision tree|Döntési fa algoritmus|
|Neural network|Neurális háló ingatlan predikcióhoz|

<Tabs>
<TabItem value="Connect4" label="Connect4" default>
## Description

Ez egy minimax algoritmus, amelynek célja a Connect4 játék megnyerése. 9 méjségig megy az algoritmus és alpha/beta nyesést használ, a jobb teljesítmény érdekében. Így a győzelmének nagyon magas esélye biztosított.

## Code fragments

```java title="max algoritmus" showLineNumbers
if(maxPlayer){
            int value = -9999999;
            int column = validSteps.get(new Random().nextInt(validSteps.size()));
            for(int i: validSteps){
                Board boardCopy = new Board(board);
                boardCopy.step(this.playerIndex, i);
                int newScore = miniMax(boardCopy, depth-1, alpha, beta, false)[1];
                if(newScore > value){
                    value = newScore;
                    column = i;
                }
                //highlight-start
                alpha = max(alpha, value);
                if(alpha >= beta){
                    break;
                }
                //highlight-end
            }
            return new int[]{column, value};
        }
```
:::note
A jelölt részen látható az említett alpha/beta nyesés.
:::
```java title="Score calcing" showLineNumbers
private int scoreOfFour(int[] four, int playerIndex){
        int score = 0;
        if(outOfFour(four, playerIndex) == 4) score += 100;
        else if(outOfFour(four, playerIndex) == 3 && outOfFour(four, 0) == 1) score += 5;
        else if(outOfFour(four, playerIndex) == 2 && outOfFour(four, 0) == 2) score += 2;
        if(outOfFour(four, 1) == 3 && outOfFour(four, 0) == 1) score -= 100;
        else if(outOfFour(four, 1) == 2 && outOfFour(four, 0) == 2) score -= 2;
        return score;
    }
```

</TabItem>
<TabItem value="decisionTree" label="Decision tree">

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

</TabItem>
<TabItem value="neuralNetwork" label="Neural Network">

## Description

Ez egy olyan neurális háló, amely tanulás után predikálni tudja egy ingatlan megvásárlásának valószínűségét a korábbi ingatlanvásárlások alapján.

## Code fragments

```python title="Modell architektúra"
model = Sequential([
    Dense(256, input_dim=X_train.shape[1], activation='relu'),
    Dropout(0.1),
    Dense(128, activation='relu'),
    Dropout(0.1),
    Dense(64, activation='relu'),
    Dense(32, activation='relu'),
    Dense(16, activation='relu'),
    Dense(1, activation='linear')
])
```

:::note
Ez egy 6 rétegű szekvenciális modell, amely relu függvények segítségével számolja az adott neuronok súlyozását.
:::

```python title="Modell tanítása"
model.fit(X_train_normalized, y_train, epochs=100, batch_size=64, validation_split=0.2, callbacks=[early_stopping])
```
:::danger Important
Ha a kimeneti adathalmaz elkezdene eltávolodni a validációs adathalmaztól, akkor leáll.
:::

</TabItem>
</Tabs>
