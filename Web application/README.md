# Description 
Ezen alkalmazás egy teljes webalkalmazást foglal magába, adatbázissal, backend API-val, frontend felülettel és Dockerizációval 

# Build project
1. mitigia-task mappában ```mvn clean package``` parancs kiadása a .jar file létrehozásához
2. Gyökérkönyvtárban Dockerfile buildelése: ```docker build .```

# Deploy project
- docker-compose file futtatása: ```docker compose up```