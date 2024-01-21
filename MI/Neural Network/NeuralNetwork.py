import pandas as pd
import numpy as np
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.callbacks import EarlyStopping

# Tanító és teszt adatok betöltése
X_train = pd.read_csv('housing_x_train_02e74f.csv').values
y_train = pd.read_csv('housing_y_train_02e74f.csv').values
X_test = pd.read_csv('housing_x_test_02e74f.csv').values

# Normalizálás a tanító és teszt adaton
scaler = MinMaxScaler()
X_train_normalized = scaler.fit_transform(X_train)
X_test_normalized = scaler.transform(X_test)

# Modell architektúra
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

# Optimalizáló és modell összeállítása
custom_optimizer = Adam(learning_rate=0.0009)
model.compile(optimizer=custom_optimizer, loss='mean_squared_error')

# Korai leállítás beállítása
early_stopping = EarlyStopping(monitor='val_loss', patience=14)

# Modell tanítása
model.fit(X_train_normalized, y_train, epochs=100, batch_size=64, validation_split=0.2, callbacks=[early_stopping])

# Predikció a teszt adaton
predictions = model.predict(X_test_normalized)

# Eredmények exportálása .csv fájlba
np.savetxt('housing_y_test.csv', predictions, delimiter=",", fmt="%g")