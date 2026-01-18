# Kubernetes Deployment Guide - Perpustakaan Microservices

## 📋 Struktur Service yang Tersedia

### Infrastructure
- **RabbitMQ**: Message broker (port 5672, management UI 15672)
- **Eureka Server**: Service discovery (port 8761)

### Application Services
1. **API Gateway** - Port 8080 (NodePort 30080) - Entry point untuk semua service
2. **Anggota Service** - Port 8081 - Manajemen data anggota
3. **Buku Service** - Port 8082 - Manajemen data buku
4. **Peminjaman Service** - Port 8084 - Proses peminjaman buku
5. **Pengembalian Service** - Port 8085 - Proses pengembalian buku
6. **Command Service** - Port 8088 - CQRS command handler
7. **Query Service** - Port 8087 - CQRS query handler
8. **RabbitMQ Peminjaman** - Port 8086 - Message consumer untuk peminjaman
9. **Rabbit Pengembalian** - Port 8089 - Message consumer untuk pengembalian

## 🚀 Quick Start

### 1. Build Docker Images (Jika belum ada)
```powershell
# Build semua images
./build-all.ps1

# Atau build manual per service
docker build -t perpustakaan/eureka-service:latest ./eureka
docker build -t perpustakaan/api-gateway:latest ./api-get-away
docker build -t perpustakaan/anggota-service:latest ./anggota
docker build -t perpustakaan/buku-service:latest ./buku
docker build -t perpustakaan/peminjaman-service:latest ./peminjaman
docker build -t perpustakaan/pengembalian-service:latest ./pengembalian
docker build -t perpustakaan/command-service:latest ./command-service
docker build -t perpustakaan/query-service:latest ./query-service
docker build -t perpustakaan/rabbitmq-peminjaman-service:latest ./rabbitmq-peminjaman-service
docker build -t perpustakaan/rabbit-pengembalian-service:latest ./rabbit_pengembalian
```

### 2. Deploy ke Kubernetes

**Opsi A: Deploy Semua Sekaligus**
```bash
kubectl apply -f kubernetes/deploy-all.yaml
```

**Opsi B: Deploy Bertahap**
```bash
# 1. Buat namespace
kubectl apply -f kubernetes/namespace.yaml

# 2. Deploy infrastructure (RabbitMQ & Eureka)
kubectl apply -f kubernetes/rabbitmq-deployment.yaml
kubectl apply -f kubernetes/eureka-deployment.yaml

# 3. Tunggu infrastructure ready
kubectl wait --for=condition=ready pod -l app=rabbitmq -n perpustakaan-ns --timeout=300s
kubectl wait --for=condition=ready pod -l app=eureka-server -n perpustakaan-ns --timeout=300s

# 4. Deploy API Gateway
kubectl apply -f kubernetes/api-gateway-deployment.yaml

# 5. Deploy business services
kubectl apply -f kubernetes/anggota-service-deployment.yaml
kubectl apply -f kubernetes/buku-service-deployment.yaml
kubectl apply -f kubernetes/peminjaman-service-deployment.yaml
kubectl apply -f kubernetes/pengembalian-service-deployment.yaml

# 6. Deploy CQRS services
kubectl apply -f kubernetes/command-service-deployment.yaml
kubectl apply -f kubernetes/query-service-deployment.yaml
kubectl apply -f kubernetes/rabbitmq-peminjaman-service-deployment.yaml
kubectl apply -f kubernetes/rabbit-pengembalian-service-deployment.yaml
```

### 3. Verifikasi Deployment

```bash
# Cek semua pods
kubectl get pods -n perpustakaan-ns

# Cek semua services
kubectl get svc -n perpustakaan-ns

# Cek logs specific service
kubectl logs -f deployment/anggota-deployment -n perpustakaan-ns

# Cek detail deployment
kubectl describe deployment anggota-deployment -n perpustakaan-ns
```

## 🔍 Monitoring & Troubleshooting

### Akses Services

**Dari dalam cluster:**
- Eureka: http://eureka-server:8761
- API Gateway: http://api-gateway-service:8080
- RabbitMQ Management: http://rabbitmq:15672

**Dari luar cluster (NodePort):**
- API Gateway: http://<node-ip>:30080
- Eureka: Buat NodePort service jika perlu akses eksternal

### Melihat Logs
```bash
# Real-time logs
kubectl logs -f deployment/peminjaman-deployment -n perpustakaan-ns

# Last 100 lines
kubectl logs --tail=100 deployment/buku-deployment -n perpustakaan-ns

# Logs dari semua pods dengan label
kubectl logs -l app=anggota-service -n perpustakaan-ns
```

### Port Forwarding untuk Testing
```bash
# Forward Eureka ke localhost
kubectl port-forward svc/eureka-server 8761:8761 -n perpustakaan-ns

# Forward RabbitMQ Management UI
kubectl port-forward svc/rabbitmq 15672:15672 -n perpustakaan-ns

# Forward API Gateway
kubectl port-forward svc/api-gateway-service 8080:8080 -n perpustakaan-ns
```

## 🔄 Update & Rollback

### Update Image
```bash
# Update image version
kubectl set image deployment/anggota-deployment \
  anggota-container=perpustakaan/anggota-service:v2.0 \
  -n perpustakaan-ns

# Restart deployment
kubectl rollout restart deployment/anggota-deployment -n perpustakaan-ns

# Cek status rollout
kubectl rollout status deployment/anggota-deployment -n perpustakaan-ns
```

### Rollback
```bash
# Rollback ke versi sebelumnya
kubectl rollout undo deployment/anggota-deployment -n perpustakaan-ns

# Rollback ke revision tertentu
kubectl rollout undo deployment/anggota-deployment --to-revision=2 -n perpustakaan-ns

# Lihat history
kubectl rollout history deployment/anggota-deployment -n perpustakaan-ns
```

## 📊 Scaling

```bash
# Scale replicas
kubectl scale deployment/anggota-deployment --replicas=3 -n perpustakaan-ns

# Auto-scale based on CPU
kubectl autoscale deployment/anggota-deployment \
  --min=2 --max=5 --cpu-percent=80 \
  -n perpustakaan-ns
```

## 🗑️ Cleanup

```bash
# Hapus semua resources
kubectl delete -f kubernetes/deploy-all.yaml

# Atau hapus per namespace
kubectl delete namespace perpustakaan-ns

# Hapus specific deployment
kubectl delete deployment anggota-deployment -n perpustakaan-ns
```

## ⚙️ Configuration Notes

### Environment Variables
Semua services dikonfigurasi dengan:
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`: Pointing ke `http://eureka-server:8761/eureka/`
- `SPRING_RABBITMQ_HOST`: Pointing ke `rabbitmq`
- Database: Menggunakan H2 in-memory (data hilang saat restart)

### Image Pull Policy
- `IfNotPresent`: Kubernetes akan gunakan local image jika ada
- Pastikan images sudah di-build di semua nodes atau push ke registry

### Resource Limits (Optional)
Untuk production, tambahkan resource limits di deployment:
```yaml
resources:
  requests:
    memory: "256Mi"
    cpu: "250m"
  limits:
    memory: "512Mi"
    cpu: "500m"
```

## 🔐 Security Considerations

1. **Secrets**: RabbitMQ credentials saat ini hardcoded. Untuk production, gunakan Kubernetes Secrets
2. **Network Policy**: Implement network policies untuk isolasi service
3. **RBAC**: Setup proper Role-Based Access Control
4. **TLS**: Enable TLS untuk komunikasi antar services

## 📝 Service Mapping

| Service | Docker Port | K8s Port | Docker Compose External Port |
|---------|------------|----------|------------------------------|
| Eureka | 8761 | 8761 | 10761 |
| API Gateway | 8080 | 8080 | 10080 |
| Anggota | 8081 | 8081 | 10081 |
| Buku | 8082 | 8082 | 10082 |
| Peminjaman | 8084 | 8084 | 10083 |
| Pengembalian | 8085 | 8085 | 10084 |
| Command | 8088 | 8088 | 10085 |
| Query | 8087 | 8087 | 10086 |
| RabbitMQ Peminjaman | 8086 | 8086 | 10087 |
| Rabbit Pengembalian | 8089 | 8089 | 10088 |
| RabbitMQ AMQP | 5672 | 5672 | 5672 |
| RabbitMQ Management | 15672 | 15672 | 15672 |

## 🎯 Best Practices

1. **Health Checks**: Implementasikan liveness dan readiness probes
2. **Logging**: Centralized logging dengan ELK/Loki
3. **Monitoring**: Setup Prometheus & Grafana untuk monitoring
4. **CI/CD**: Integrate dengan Jenkins/GitLab CI untuk automated deployment
5. **GitOps**: Consider using ArgoCD/Flux untuk declarative deployments
