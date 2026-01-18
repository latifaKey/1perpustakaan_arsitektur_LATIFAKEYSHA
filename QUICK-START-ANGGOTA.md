# 🚀 Quick Start - Deploy Service Anggota
**Author**: Latifa Keysha (2311083019) - TRPL 3C

## 📍 Lokasi File Kubernetes
```
d:\5. SEMESTER 5\ARSITEKTUR\perpustakaan\kubernetes\
```

---

## ⚡ CARA CEPAT - Deploy Service Anggota

### **Option 1: Gunakan Script Otomatis (PALING MUDAH)**

```powershell
# Dari root folder perpustakaan
cd "d:\5. SEMESTER 5\ARSITEKTUR\perpustakaan"

# Jalankan script
.\deploy-anggota.ps1
```

Script akan otomatis:
1. ✅ Setup local registry
2. ✅ Build Maven
3. ✅ Build Docker image
4. ✅ Deploy Kubernetes (namespace, secrets, infrastructure, services)
5. ✅ Deploy Jenkins

---

### **Option 2: Manual Step-by-Step**

#### **Step 1: Setup Local Registry**
```powershell
docker run -d -p 5000:5000 --restart=always --name registry registry:2
```

#### **Step 2: Build Service**
```powershell
cd anggota
mvn clean package -DskipTests
```

#### **Step 3: Build & Push Docker Image**
```powershell
docker build -t localhost:5000/anggotaservice:1.0 .
docker push localhost:5000/anggotaservice:1.0
cd ..
```

#### **Step 4: Deploy ke Kubernetes**
```powershell
# Deploy namespace & secrets
kubectl apply -f kubernetes/01-namespace/namespace.yaml
kubectl apply -f kubernetes/02-secrets/

# Deploy infrastructure (MongoDB & RabbitMQ)
kubectl apply -f kubernetes/03-infrastructure/

# Tunggu infrastructure ready
kubectl wait --for=condition=ready pod -l app=mongodb -n perpustakaan-ns --timeout=120s
kubectl wait --for=condition=ready pod -l app=rabbitmq -n perpustakaan-ns --timeout=120s

# Deploy Eureka (HARUS PERTAMA sebelum service lain)
kubectl apply -f kubernetes/04-services/eureka-deployment.yaml
kubectl wait --for=condition=ready pod -l app=eureka -n perpustakaan-ns --timeout=180s

# Deploy Anggota Service
kubectl apply -f kubernetes/04-services/anggota-deployment.yaml
kubectl wait --for=condition=ready pod -l app=anggota -n perpustakaan-ns --timeout=180s
```

---

## ✅ Verifikasi Deployment

```powershell
# Cek semua pods
kubectl get pods -n perpustakaan-ns

# Harus muncul:
# - mongodb-xxx        1/1  Running
# - rabbitmq-xxx       1/1  Running
# - eureka-xxx         1/1  Running
# - anggota-xxx (2x)   1/1  Running
```

---

## 🌐 Akses Service

### **1. Eureka Dashboard**
```powershell
kubectl port-forward -n perpustakaan-ns svc/eureka-service 8761:8761
```
Buka: http://localhost:8761

### **2. Anggota Service**
```powershell
kubectl port-forward -n perpustakaan-ns svc/anggota-service 8081:8081
```
Test: 
```powershell
curl http://localhost:8081/actuator/health
```

### **3. RabbitMQ Management**
```powershell
kubectl port-forward -n perpustakaan-ns svc/rabbitmq 15672:15672
```
Buka: http://localhost:15672 (guest/guest)

---

## 🛠️ Deploy Jenkins (Opsional)

```powershell
# Deploy Jenkins
kubectl apply -f kubernetes/07-jenkins/

# Tunggu ready
kubectl wait --for=condition=ready pod -l app=jenkins -n jenkins-ns --timeout=300s

# Akses Jenkins di: http://localhost:30080

# Get password
$podName = kubectl get pods -n jenkins-ns -l app=jenkins -o jsonpath='{.items[0].metadata.name}'
kubectl exec -n jenkins-ns $podName -- cat /var/jenkins_home/secrets/initialAdminPassword
```

---

## 🐛 Troubleshooting

### Cek Logs Pod
```powershell
kubectl logs -n perpustakaan-ns <pod-name>
```

### Restart Deployment
```powershell
kubectl rollout restart deployment/anggota-deployment -n perpustakaan-ns
```

### Clean Up (Mulai dari Awal)
```powershell
kubectl delete namespace perpustakaan-ns
kubectl delete namespace jenkins-ns
docker stop registry
docker rm registry
```

---

## 📝 File Penting

- **Dockerfile**: `anggota/dockerfile`
- **Jenkinsfile**: `anggota/Jenkinsfile`
- **K8s Config**: `kubernetes/04-services/anggota-deployment.yaml`
- **Deploy Script**: `deploy-anggota.ps1`

---

## ✅ Checklist

- [ ] Docker Desktop running
- [ ] Kubernetes enabled
- [ ] Local registry running
- [ ] Maven build sukses
- [ ] Docker image di-push
- [ ] Namespace created
- [ ] Infrastructure (MongoDB & RabbitMQ) running
- [ ] Eureka running
- [ ] Anggota service running
- [ ] Service ter-register di Eureka
- [ ] Health check OK

---

**Status**: READY TO DEPLOY ✅  
Semua file sudah ada, tinggal jalankan!
