# Anggota Service

Microservice untuk manajemen data anggota perpustakaan.

## Port
- Service: **8081**
- Eureka: 8761

## Run Local
```bash
mvn clean package
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

## Deploy ke Kubernetes
```bash
# Create namespace (first time only)
kubectl apply -f k8s-namespace.yaml

# Deploy service
kubectl apply -f k8s-deployment.yaml

# Check pods
kubectl get pods -n perpustakaan-ns

# Check logs
kubectl logs -f deployment/anggota-deployment -n perpustakaan-ns
```

## Jenkins Pipeline
Jenkinsfile sudah dikonfigurasi untuk:
1. Build Maven project
2. Build Docker image
3. Push ke registry
4. Deploy ke Kubernetes
5. Rollout restart

Trigger: Git push atau manual build di Jenkins
