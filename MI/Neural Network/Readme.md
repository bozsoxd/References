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

Ez egy 6 rétegű szekvenciális modell, amely relu függvények segítségével számolja az adott neuronok súlyozását.


```python title="Modell tanítása"
model.fit(X_train_normalized, y_train, epochs=100, batch_size=64, validation_split=0.2, callbacks=[early_stopping])

Ha a kimeneti adathalmaz elkezdene eltávolodni a validációs adathalmaztól, akkor leáll.
