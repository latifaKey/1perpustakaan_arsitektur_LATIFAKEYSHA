# Anggota Service

Microservice untuk manajemen data anggota perpustakaan.

**Author**: Latifa Keysha (2311083019) - TRPL 3C

## Port
- Service: **8081**
- Eureka: 8761

## Run Local
```bash
mvn clean package -DskipTests
java -jar target/anggota-0.0.1-SNAPSHOT.jar
```

## API Endpoints
- GET `/api/anggota` - Get all anggota
- GET `/api/anggota/{id}` - Get by ID
- POST `/api/anggota` - Create anggota
- DELETE `/api/anggota/{id}` - Delete anggota

## Health Check
```bash
curl http://localhost:8081/actuator/health
```

## Docker Build
```bash
docker build -t localhost:5000/anggotaservice:1.0 .
docker push localhost:5000/anggotaservice:1.0
```

## Deploy ke Kubernetes
Semua konfigurasi Kubernetes ada di folder `../kubernetes/`
```bash
# Deploy dari root folder perpustakaan
cd ..

# Gunakan script otomatis
.\deploy-anggota.ps1

# Atau manual:
kubectl apply -f kubernetes/04-services/anggota-deployment.yaml

# Check pods
kubectl get pods -n perpustakaan-ns

# Check logs
kubectl logs -f deployment/anggota-deployment -n perpustakaan-ns
```

## Jenkins Pipeline
Pipeline CI/CD sudah dikonfigurasi di `Jenkinsfile`
- Otomatis build Maven
- Build Docker image
- Push ke registry
- Deploy ke Kubernetes

## Struktur Folder
```
anggota/
├── src/                    # Source code
├── target/                 # Build output
├── dockerfile             # Docker image config
├── Jenkinsfile            # CI/CD pipeline
├── pom.xml               # Maven config
└── README.md             # This file
```

Konfigurasi Kubernetes terpisah di `../kubernetes/`
Jenkinsfile sudah dikonfigurasi untuk:
1. Build Maven project
2. Build Docker image
3. Push ke registry
4. Deploy ke Kubernetes
5. Rollout restart

Trigger: Git push atau manual build di Jenkins
