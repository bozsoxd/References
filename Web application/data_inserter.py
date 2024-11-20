import pandas as pd
import openpyxl
import mariadb
import sys
import datetime

# Connect to the PostgreSQL database
try:
    conn = mariadb.connect(
        user="root",
        password="MitigiaPass",
        host="0.0.0.0",
        port=3306,
        database="Mitigia"

    )
except mariadb.Error as e:
    print(f"Error connecting to MariaDB Platform: {e}")
    sys.exit(1)

# Read the Excel file
df = pd.ExcelFile("mitigia_feladat3.xlsx")
sheet = pd.read_excel(df, "vehicle")

# Create a cursor object
cur = conn.cursor()

# Iterate over the DataFrame and execute INSERT statements
for index, row in sheet.iterrows():
    sql = "INSERT INTO vehicles (v_id, v_year, v_manufacturer, v_model, v_type, fuel_type, wltp_energy_consumption, nedc_energy_consumption) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"
    values = tuple(row)
    
    cur.execute(sql, values)

# Commit the changes
conn.commit()

# Close the cursor and connection
cur.close()
conn.close()