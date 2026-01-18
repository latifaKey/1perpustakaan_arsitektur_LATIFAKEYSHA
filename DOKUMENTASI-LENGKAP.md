<div align="center">

```
═══════════════════════════════════════════════════════════════════════════════
                                                                               
    ╔═══════════════════════════════════════════════════════════════════════╗
    ║                                                                       ║
    ║                 LAPORAN IMPLEMENTASI MICROSERVICES                    ║
    ║                   SISTEM PERPUSTAKAAN BERBASIS                        ║
    ║                    KUBERNETES DAN JENKINS CI/CD                       ║
    ║                                                                       ║
    ╚═══════════════════════════════════════════════════════════════════════╝
                                                                               
                                                                               
                                                                               
                           Disusun Oleh:                                       
                        LATIFA KEYSHA                                          
                         NIM: 2311083019                                       
                                                                               
                                                                               
                           Program Studi:                                      
                   Teknik Rekayasa Perangkat Lunak                            
                              Kelas 3C                                         
                                                                               
                                                                               
                    POLITEKNIK NEGERI PADANG                                  
                           PADANG                                              
                            2026                                               
                                                                               
═══════════════════════════════════════════════════════════════════════════════
```

</div>

<div style="page-break-after: always;"></div>

---

## 📋 DAFTAR ISI

<table>
<tr><td width="50"><b>BAB</b></td><td><b>JUDUL</b></td><td width="50"><b>HAL</b></td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>I</b></td><td><b>ARSITEKTUR SISTEM</b></td><td>1</td></tr>
<tr><td></td><td>1.1 Diagram Arsitektur Microservices</td><td>1</td></tr>
<tr><td></td><td>1.2 Komponen Sistem</td><td>2</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>II</b></td><td><b>PRASYARAT DAN PERSIAPAN</b></td><td>3</td></tr>
<tr><td></td><td>2.1 Software yang Diperlukan</td><td>3</td></tr>
<tr><td></td><td>2.2 Verifikasi Instalasi</td><td>4</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>III</b></td><td><b>STRUKTUR PROYEK</b></td><td>5</td></tr>
<tr><td></td><td>3.1 Organisasi Direktori</td><td>5</td></tr>
<tr><td></td><td>3.2 Daftar Microservices</td><td>6</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>IV</b></td><td><b>KONFIGURASI MICROSERVICES</b></td><td>7</td></tr>
<tr><td></td><td>4.1 Service Anggota</td><td>7</td></tr>
<tr><td></td><td>4.2 Service Buku</td><td>12</td></tr>
<tr><td></td><td>4.3 Service Peminjaman</td><td>13</td></tr>
<tr><td></td><td>4.4 Service Pengembalian</td><td>14</td></tr>
<tr><td></td><td>4.5 Command Service (CQRS)</td><td>15</td></tr>
<tr><td></td><td>4.6 Query Service (CQRS)</td><td>16</td></tr>
<tr><td></td><td>4.7 Eureka Server</td><td>17</td></tr>
<tr><td></td><td>4.8 API Gateway</td><td>18</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>V</b></td><td><b>DEPLOYMENT KUBERNETES</b></td><td>19</td></tr>
<tr><td></td><td>5.1 Persiapan Cluster</td><td>19</td></tr>
<tr><td></td><td>5.2 Deployment Infrastructure</td><td>20</td></tr>
<tr><td></td><td>5.3 Deployment Services</td><td>21</td></tr>
<tr><td></td><td>5.4 Verifikasi Deployment</td><td>22</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>VI</b></td><td><b>CI/CD DENGAN JENKINS</b></td><td>23</td></tr>
<tr><td></td><td>6.1 Setup Jenkins</td><td>23</td></tr>
<tr><td></td><td>6.2 Konfigurasi Pipeline</td><td>24</td></tr>
<tr><td></td><td>6.3 Alur CI/CD</td><td>25</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>VII</b></td><td><b>TESTING DAN MONITORING</b></td><td>26</td></tr>
<tr><td></td><td>7.1 Health Check</td><td>26</td></tr>
<tr><td></td><td>7.2 API Testing</td><td>27</td></tr>
<tr><td></td><td>7.3 Monitoring dengan Prometheus</td><td>28</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>VIII</b></td><td><b>TROUBLESHOOTING</b></td><td>29</td></tr>
<tr><td></td><td>8.1 Common Problems</td><td>29</td></tr>
<tr><td></td><td>8.2 Solutions</td><td>30</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td><b>IX</b></td><td><b>KESIMPULAN DAN SARAN</b></td><td>31</td></tr>
<tr><td colspan="3"><hr></td></tr>
<tr><td colspan="3"><b>LAMPIRAN</b></td></tr>
<tr><td></td><td>A. Checklist Deployment</td><td>32</td></tr>
<tr><td></td><td>B. Quick Reference</td><td>33</td></tr>
<tr><td></td><td>C. Command Reference</td><td>34</td></tr>
</table>

<div style="page-break-after: always;"></div>

---


```
═══════════════════════════════════════════════════════════════════════════════
                              BAB I
                         ARSITEKTUR SISTEM
═══════════════════════════════════════════════════════════════════════════════
```

## 1.1 Diagram Arsitektur Microservices

Sistem Perpustakaan ini dibangun menggunakan **Microservices Architecture** dengan 8 services independen yang saling berkomunikasi melalui REST API dan Message Broker (RabbitMQ).

<div align="center">

### **Arsitektur Lengkap Sistem**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                                                                         │
│                     CLIENT / USER INTERFACE                             │
│                                                                         │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
                                 │ HTTP Request
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                          API GATEWAY                                    │
│                           Port: 8080                                    │
│                    Load Balancer & Routing                              │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
                                 │ Service Discovery
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                       EUREKA SERVER                                     │
│                         Port: 8761                                      │
│                   Service Registry & Discovery                          │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
                 ┌───────────────┼───────────────┐
                 │               │               │
                 ▼               ▼               ▼
    ┌──────────────────┐  ┌──────────────┐  ┌──────────────────┐
    │    ANGGOTA       │  │     BUKU     │  │   PEMINJAMAN     │
    │   Port: 8081     │  │  Port: 8082  │  │   Port: 8084     │
    │                  │  │              │  │                  │
    │  - CRUD Member   │  │  - CRUD Book │  │  - Create Loan   │
    │  - Status: AKTIF │  │  - Status:   │  │  - Status:       │
    │  - H2 Database   │  │    TERSEDIA  │  │    DIPINJAM      │
    └──────────────────┘  └──────────────┘  └────────┬─────────┘
                                                      │
                                        ┌─────────────┴──────────┐
                                        │                        │
                                        ▼                        ▼
                            ┌──────────────────┐      ┌────────────────┐
                            │  PENGEMBALIAN    │      │   RABBITMQ     │
                            │   Port: 8085     │      │  Message Broker│
                            │                  │      │                │
                            │  - Return Book   │◄────►│  Event Queue   │
                            │  - Calculate Fee │      │                │
                            │  - Status: SELESAI│      └───────┬────────┘
                            └──────────────────┘              │
                                                              │
                                       ┌──────────────────────┼────────────┐
                                       │                      │            │
                                       ▼                      ▼            ▼
                            ┌─────────────────┐   ┌──────────────────┐
                            │ COMMAND SERVICE │   │  QUERY SERVICE   │
                            │   Port: 8088    │   │   Port: 8087     │
                            │                 │   │                  │
                            │  CQRS - WRITE   │   │   CQRS - READ    │
                            │  Event Publisher│   │  Event Subscriber│
                            └─────────────────┘   └──────────────────┘
                                       
┌─────────────────────────────────────────────────────────────────────────┐
│                        INFRASTRUCTURE LAYER                             │
│                                                                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐                │
│  │   JENKINS    │  │  PROMETHEUS  │  │  KUBERNETES  │                │
│  │  Port: 30080 │  │   Metrics    │  │ Orchestration│                │
│  │    CI/CD     │  │  Monitoring  │  │   + Scaling  │                │
│  └──────────────┘  └──────────────┘  └──────────────┘                │
└─────────────────────────────────────────────────────────────────────────┘
```

</div>

## 1.2 Komponen Sistem

### **A. Microservices Layer**

| No | Service | Port | Fungsi | Database | Status Field |
|----|---------|------|--------|----------|--------------|
| 1 | **Anggota** | 8081 | Manajemen data anggota perpustakaan | H2 (anggotadb) | AKTIF / TIDAK_AKTIF |
| 2 | **Buku** | 8082 | Manajemen data buku | H2 (bukudb) | TERSEDIA / DIPINJAM |
| 3 | **Peminjaman** | 8084 | Proses peminjaman buku | H2 (peminjaman) | DIPINJAM / DIKEMBALIKAN |
| 4 | **Pengembalian** | 8085 | Proses pengembalian & denda | H2 (pengembalian) | SELESAI / PENDING |
| 5 | **Command Service** | 8088 | CQRS Write Operations | H2 (commanddb) | - |
| 6 | **Query Service** | 8087 | CQRS Read Operations | H2 (querydb) | - |
| 7 | **Eureka Server** | 8761 | Service Discovery & Registry | - | - |
| 8 | **API Gateway** | 8080 | Load Balancer & Routing | - | - |

### **B. Infrastructure Components**

```
╔════════════════════════════════════════════════════════════════╗
║                    INFRASTRUCTURE STACK                        ║
╠════════════════════════════════════════════════════════════════╣
║                                                                ║
║  1. KUBERNETES                                                 ║
║     • Container Orchestration                                  ║
║     • Auto-scaling & Self-healing                              ║
║     • Rolling Updates                                          ║
║                                                                ║
║  2. DOCKER                                                     ║
║     • Containerization                                         ║
║     • Base Image: eclipse-temurin:17-jre-alpine               ║
║     • Health Check Integration                                 ║
║                                                                ║
║  3. RABBITMQ                                                   ║
║     • Message Broker                                           ║
║     • Event-Driven Communication                               ║
║     • CQRS Pattern Support                                     ║
║                                                                ║
║  4. JENKINS                                                    ║
║     • Continuous Integration                                   ║
║     • Continuous Deployment                                    ║
║     • Automated Pipeline (5 Stages)                            ║
║                                                                ║
║  5. PROMETHEUS                                                 ║
║     • Metrics Collection                                       ║
║     • Performance Monitoring                                   ║
║     • Actuator Integration                                     ║
║                                                                ║
║  6. H2 DATABASE                                                ║
║     • In-Memory Database                                       ║
║     • Development & Testing                                    ║
║     • Per-Service Database                                     ║
║                                                                ║
╚════════════════════════════════════════════════════════════════╝
```

### **C. Technology Stack**

<table border="1" cellpadding="10">
<tr>
<th width="200">Category</th>
<th>Technology</th>
<th>Version</th>
</tr>
<tr>
<td><b>Backend Framework</b></td>
<td>Spring Boot</td>
<td>3.5.5</td>
</tr>
<tr>
<td><b>Language</b></td>
<td>Java</td>
<td>17</td>
</tr>
<tr>
<td><b>Build Tool</b></td>
<td>Maven</td>
<td>3.8+</td>
</tr>
<tr>
<td><b>Service Discovery</b></td>
<td>Spring Cloud Netflix Eureka</td>
<td>4.x</td>
</tr>
<tr>
<td><b>API Gateway</b></td>
<td>Spring Cloud Gateway</td>
<td>4.x</td>
</tr>
<tr>
<td><b>Database</b></td>
<td>H2 In-Memory</td>
<td>2.x</td>
</tr>
<tr>
<td><b>Message Broker</b></td>
<td>RabbitMQ</td>
<td>3.x</td>
</tr>
<tr>
<td><b>Container Runtime</b></td>
<td>Docker</td>
<td>Latest</td>
</tr>
<tr>
<td><b>Orchestration</b></td>
<td>Kubernetes</td>
<td>1.28+</td>
</tr>
<tr>
<td><b>CI/CD</b></td>
<td>Jenkins</td>
<td>Latest LTS</td>
</tr>
<tr>
<td><b>Monitoring</b></td>
<td>Prometheus + Actuator</td>
<td>Latest</td>
</tr>
<tr>
<td><b>Code Generation</b></td>
<td>Lombok</td>
<td>1.18+</td>
</tr>
</table>

<div style="page-break-after: always;"></div>

---


```
═══════════════════════════════════════════════════════════════════════════════
                              BAB II
                      PRASYARAT DAN PERSIAPAN
═══════════════════════════════════════════════════════════════════════════════
```

## 2.1 Software yang Diperlukan

Sebelum memulai implementasi, pastikan semua software berikut telah terinstall dengan benar:

<table border="1" cellpadding="10" width="100%">
<tr bgcolor="#4472C4">
<th style="color:white"><b>No</b></th>
<th style="color:white"><b>Software</b></th>
<th style="color:white"><b>Version</b></th>
<th style="color:white"><b>Link Download</b></th>
<th style="color:white"><b>Keterangan</b></th>
</tr>
<tr>
<td align="center"><b>1</b></td>
<td><b>Docker Desktop</b></td>
<td>Latest</td>
<td>https://www.docker.com/products/docker-desktop</td>
<td>Wajib untuk containerization</td>
</tr>
<tr>
<td align="center"><b>2</b></td>
<td><b>Kubernetes</b></td>
<td>1.28+</td>
<td>Enabled in Docker Desktop</td>
<td>Orchestration platform</td>
</tr>
<tr>
<td align="center"><b>3</b></td>
<td><b>Java JDK</b></td>
<td>17+</td>
<td>https://adoptium.net/</td>
<td>Runtime environment</td>
</tr>
<tr>
<td align="center"><b>4</b></td>
<td><b>Maven</b></td>
<td>3.8+</td>
<td>Built-in (mvnw)</td>
<td>Optional, sudah ada wrapper</td>
</tr>
<tr>
<td align="center"><b>5</b></td>
<td><b>kubectl</b></td>
<td>Latest</td>
<td>https://kubernetes.io/docs/tasks/tools/</td>
<td>Kubernetes CLI</td>
</tr>
<tr>
<td align="center"><b>6</b></td>
<td><b>Git</b></td>
<td>Latest</td>
<td>https://git-scm.com/</td>
<td>Version control</td>
</tr>
<tr>
<td align="center"><b>7</b></td>
<td><b>PowerShell</b></td>
<td>5.1+</td>
<td>Built-in Windows</td>
<td>Deployment scripts</td>
</tr>
</table>

## 2.2 Verifikasi Instalasi

Setelah instalasi, jalankan command berikut untuk memverifikasi:

### **2.2.1 Verifikasi Docker**

```powershell
# Check Docker version
docker --version

# Output expected:
# Docker version 24.0.x, build xxxxxxx

# Check Docker is running
docker ps

# Output expected:
# CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
```

### **2.2.2 Verifikasi Kubernetes**

```powershell
# Check kubectl version
kubectl version --client

# Output expected:
# Client Version: v1.28.x

# Check cluster info
kubectl cluster-info

# Output expected:
# Kubernetes control plane is running at https://kubernetes.docker.internal:6443
```

### **2.2.3 Verifikasi Java**

```powershell
# Check Java version
java -version

# Output expected:
# openjdk version "17.0.x" 2024-xx-xx
# OpenJDK Runtime Environment Temurin-17+xx
```

### **2.2.4 Verifikasi Maven Wrapper**

```powershell
# Navigate to service directory
cd anggota

# Check Maven Wrapper
.\mvnw.cmd --version

# Output expected:
# Apache Maven 3.8.x
# Maven home: C:\Users\...\.m2\wrapper\dists\...
# Java version: 17.0.x
```

### **Checklist Verifikasi**

```
╔════════════════════════════════════════════════════════════════╗
║           CHECKLIST VERIFIKASI ENVIRONMENT                     ║
╠════════════════════════════════════════════════════════════════╣
║                                                                ║
║  ☐  Docker Desktop installed dan running                      ║
║  ☐  Kubernetes enabled di Docker Desktop                      ║
║  ☐  Java JDK 17 installed                                     ║
║  ☐  kubectl configured properly                               ║
║  ☐  Maven Wrapper tersedia di setiap service                  ║
║  ☐  Git installed dan configured                              ║
║  ☐  PowerShell 5.1+ available                                 ║
║  ☐  Minimal 8GB RAM available                                 ║
║  ☐  Minimal 20GB disk space free                              ║
║  ☐  Port 8080-8088, 8761, 30080 available                     ║
║                                                                ║
╚════════════════════════════════════════════════════════════════╝
```

<div style="page-break-after: always;"></div>

---


```
═══════════════════════════════════════════════════════════════════════════════
                              BAB III
                          STRUKTUR PROYEK
═══════════════════════════════════════════════════════════════════════════════
```

## 3.1 Organisasi Direktori

Proyek ini menggunakan struktur **mono-repository** dengan 8 microservices independen:

```
D:\5. SEMESTER 5\ARSITEKTUR\perpustakaan\
│
├── 📄 build-all.ps1                    # Script build semua services
├── 📄 deploy-anggota.ps1               # Script deployment otomatis
├── 📄 docker-compose.yml               # Docker Compose untuk testing
├── 📄 DOCKER-README.md                 # Dokumentasi Docker
├── 📄 DOKUMENTASI-LENGKAP.md           # Laporan lengkap (file ini)
├── 📄 perpustakaan.code-workspace      # VS Code workspace
├── 📄 quick-start.ps1                  # Quick start script
│
├── 📁 anggota/                         ← SERVICE 1: Manajemen Anggota
│   ├── 📁 src/
│   │   ├── 📁 main/java/com/key/anggota/
│   │   │   ├── 📄 AnggotaApplication.java
│   │   │   ├── 📁 model/
│   │   │   │   └── 📄 Anggota.java              # Entity + Status Field
│   │   │   ├── 📁 controller/
│   │   │   │   └── 📄 AnggotaController.java    # GET, POST, PUT, DELETE
│   │   │   ├── 📁 service/
│   │   │   │   └── 📄 AnggotaService.java       # Business Logic
│   │   │   └── 📁 repository/
│   │   │       └── 📄 AnggotaRepository.java    # JPA Repository
│   │   └── 📁 main/resources/
│   │       └── 📄 application.properties        # Config + Prometheus
│   ├── 📁 target/
│   │   └── 📄 anggota-0.0.1-SNAPSHOT.jar       # Built artifact
│   ├── 📄 dockerfile                           # Alpine + Health Check
│   ├── 📄 Jenkinsfile                          # CI/CD Pipeline (5 stages)
│   ├── 📄 pom.xml                              # Maven dependencies
│   └── 📄 mvnw.cmd                             # Maven Wrapper
│
├── 📁 buku/                            ← SERVICE 2: Manajemen Buku
│   ├── 📁 src/main/java/com/key/buku/
│   │   ├── 📄 Buku.java                        # Status: TERSEDIA/DIPINJAM
│   │   ├── 📄 BukuController.java
│   │   ├── 📄 BukuService.java
│   │   └── 📄 BukuRepository.java
│   ├── 📄 dockerfile
│   ├── 📄 Jenkinsfile
│   └── 📄 pom.xml
│
├── 📁 peminjaman/                      ← SERVICE 3: Proses Peminjaman
│   ├── 📁 src/main/java/com/key/peminjaman/
│   │   ├── 📄 Peminjaman.java                  # Status: DIPINJAM/DIKEMBALIKAN
│   │   ├── 📄 PeminjamanController.java
│   │   ├── 📄 PeminjamanService.java           # RestTemplate integration
│   │   └── 📄 PeminjamanRepository.java
│   ├── 📄 dockerfile
│   ├── 📄 Jenkinsfile
│   └── 📄 pom.xml
│
├── 📁 pengembalian/                    ← SERVICE 4: Proses Pengembalian
│   ├── 📁 src/main/java/com/key/pengembalian/
│   │   ├── 📄 Pengembalian.java                # Status: SELESAI/PENDING
│   │   ├── 📄 PengembalianController.java
│   │   ├── 📄 PengembalianService.java         # Denda calculation + RabbitMQ
│   │   └── 📄 PengembalianRepository.java
│   ├── 📄 dockerfile
│   ├── 📄 Jenkinsfile
│   └── 📄 pom.xml
│
├── 📁 command-service/                 ← SERVICE 5: CQRS Write Side
│   ├── 📁 src/main/java/com/key/commandservice/
│   │   ├── 📁 command/                         # Command handlers
│   │   ├── 📁 event/                           # Event definitions
│   │   ├── 📁 model/
│   │   └── 📁 service/
│   │       └── 📄 EventPublisher.java          # RabbitMQ publisher
│   ├── 📄 dockerfile
│   ├── 📄 Jenkinsfile
│   └── 📄 pom.xml
│
├── 📁 query-service/                   ← SERVICE 6: CQRS Read Side
│   ├── 📁 src/main/java/com/key/queryservice/
│   │   ├── 📁 model/                           # View models
│   │   ├── 📁 controller/                      # Query endpoints
│   │   └── 📁 messaging/
│   │       └── 📄 QueryServiceMessageListener.java  # RabbitMQ subscriber
│   ├── 📄 dockerfile
│   ├── 📄 Jenkinsfile
│   └── 📄 pom.xml
│
├── 📁 eureka/                          ← SERVICE 7: Service Discovery
│   ├── 📁 src/main/java/com/key/eureka/
│   │   └── 📄 EurekaServerApplication.java
│   ├── 📁 src/main/resources/
│   │   └── 📄 application.properties           # Eureka config
│   ├── 📄 dockerfile
│   ├── 📄 Jenkinsfile
│   └── 📄 pom.xml
│
├── 📁 api-get-away/                    ← SERVICE 8: API Gateway
│   ├── 📁 src/main/java/com/key/apigateway/
│   │   └── 📄 ApiGatewayApplication.java
│   ├── 📁 src/main/resources/
│   │   └── 📄 application.yml                  # Gateway routes
│   ├── 📄 dockerfile
│   ├── 📄 Jenkinsfile
│   └── 📄 pom.xml
│
├── 📁 kubernetes/                      # Kubernetes Manifests
│   ├── 📁 01-namespace/
│   │   └── 📄 namespace.yaml                   # 4 namespaces
│   ├── 📁 02-secrets/
│   │   ├── 📄 mongodb-secret.yaml
│   │   └── 📄 rabbitmq-secret.yaml
│   ├── 📁 03-infrastructure/
│   │   ├── 📄 mongodb-deployment.yaml
│   │   ├── 📄 mongodb-pvc.yaml
│   │   ├── 📄 mongodb-service.yaml
│   │   ├── 📄 rabbitmq-deployment.yaml
│   │   └── 📄 rabbitmq-service.yaml
│   ├── 📁 04-services/
│   │   ├── 📄 anggota-deployment.yaml          # Deployment + Service + Ingress
│   │   ├── 📄 buku-deployment.yaml
│   │   ├── 📄 peminjaman-deployment.yaml
│   │   ├── 📄 pengembalian-deployment.yaml
│   │   ├── 📄 eureka-deployment.yaml
│   │   └── 📄 api-gateway-deployment.yaml
│   ├── 📁 05-logging/                          # Logging stack (future)
│   ├── 📁 06-monitoring/                       # Monitoring stack (future)
│   └── 📁 07-jenkins/
│       ├── 📄 jenkins-deployment.yaml          # Jenkins master
│       ├── 📄 jenkins-pvc.yaml                 # Persistent storage
│       └── 📄 jenkins-service.yaml             # NodePort 30080
│
├── 📁 rabbit_pengembalian/             # RabbitMQ consumer for Pengembalian
└── 📁 rabbitmq-peminjaman-service/     # RabbitMQ consumer for Peminjaman
```

## 3.2 Daftar Microservices

<table border="1" cellpadding="10" width="100%">
<tr bgcolor="#4472C4">
<th style="color:white"><b>No</b></th>
<th style="color:white"><b>Service Name</b></th>
<th style="color:white"><b>Port</b></th>
<th style="color:white"><b>Endpoints</b></th>
<th style="color:white"><b>Features</b></th>
</tr>
<tr>
<td align="center"><b>1</b></td>
<td><b>Anggota Service</b></td>
<td align="center">8081</td>
<td>/api/anggota</td>
<td>✅ CRUD, ✅ Status Field, ✅ PUT Endpoint</td>
</tr>
<tr>
<td align="center"><b>2</b></td>
<td><b>Buku Service</b></td>
<td align="center">8082</td>
<td>/api/buku</td>
<td>✅ CRUD, ✅ Status Field, ✅ PUT Endpoint</td>
</tr>
<tr>
<td align="center"><b>3</b></td>
<td><b>Peminjaman Service</b></td>
<td align="center">8084</td>
<td>/api/peminjaman</td>
<td>✅ CRUD, ✅ Status Field, ✅ RestTemplate</td>
</tr>
<tr>
<td align="center"><b>4</b></td>
<td><b>Pengembalian Service</b></td>
<td align="center">8085</td>
<td>/api/pengembalian</td>
<td>✅ CRUD, ✅ Denda Logic, ✅ RabbitMQ</td>
</tr>
<tr>
<td align="center"><b>5</b></td>
<td><b>Command Service</b></td>
<td align="center">8088</td>
<td>/api/command/*</td>
<td>✅ CQRS Write, ✅ Event Publisher</td>
</tr>
<tr>
<td align="center"><b>6</b></td>
<td><b>Query Service</b></td>
<td align="center">8087</td>
<td>/api/query/*</td>
<td>✅ CQRS Read, ✅ Event Subscriber</td>
</tr>
<tr>
<td align="center"><b>7</b></td>
<td><b>Eureka Server</b></td>
<td align="center">8761</td>
<td>/eureka</td>
<td>✅ Service Discovery, ✅ Dashboard</td>
</tr>
<tr>
<td align="center"><b>8</b></td>
<td><b>API Gateway</b></td>
<td align="center">8080</td>
<td>/*</td>
<td>✅ Load Balancer, ✅ Routing</td>
</tr>
</table>

### **Ringkasan File per Service**

```
╔════════════════════════════════════════════════════════════════╗
║              FILE CHECKLIST PER SERVICE                        ║
╠════════════════════════════════════════════════════════════════╣
║                                                                ║
║  Setiap Service memiliki struktur file yang sama:              ║
║                                                                ║
║  ✅ pom.xml                  - Maven dependencies              ║
║  ✅ mvnw.cmd                 - Maven Wrapper                   ║
║  ✅ dockerfile               - Alpine + Health Check           ║
║  ✅ Jenkinsfile              - CI/CD Pipeline (5 stages)       ║
║  ✅ application.properties   - Config + Prometheus             ║
║  ✅ Model class              - Entity dengan @Data             ║
║  ✅ Controller class         - REST API endpoints              ║
║  ✅ Service class            - Business logic                  ║
║  ✅ Repository interface     - JPA repository                  ║
║                                                                ║
╚════════════════════════════════════════════════════════════════╝
```

<div style="page-break-after: always;"></div>

---

## 🎯 KONFIGURASI PER SERVICE

### **1. SERVICE ANGGOTA (Port 8081)**

#### **A. Model (Anggota.java)**
```java
@Data
@Entity
public class Anggota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    private String alamat;
    private String email;
    private String telepon;
    private String status; // "AKTIF" atau "TIDAK_AKTIF"
}
```

#### **B. REST API Endpoints**
| Method | Endpoint | Fungsi |
|--------|----------|--------|
| GET | `/api/anggota` | Get all anggota |
| GET | `/api/anggota/{id}` | Get anggota by ID |
| POST | `/api/anggota` | Create anggota |
| PUT | `/api/anggota/{id}` | Update anggota |
| DELETE | `/api/anggota/{id}` | Delete anggota |

#### **C. Application Properties**
```properties
server.port=8081
spring.application.name=anggota
spring.datasource.url=jdbc:h2:mem:anggotadb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Actuator untuk health check & monitoring
management.endpoint.health.show-details=always
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.probes.enabled=true
management.metrics.export.prometheus.enabled=true
```

#### **D. Dockerfile**
```dockerfile
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY target/anggota-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8081/actuator/health || exit 1

ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
```

#### **E. Jenkinsfile (CI/CD Pipeline)**
```groovy
pipeline {
    agent any
    
    environment {
        SERVICE_NAME = 'anggotaservice'
        DOCKER_DIR = 'anggota'
        K8S_DEPLOYMENT = 'anggota-deployment'
        K8S_NS = 'perpustakaan-ns'
        CONTAINER_NAME = 'anggota-container'
        IMAGE_TAG = "localhost:5000/${SERVICE_NAME}:1.0"
    }
    
    tools {
        maven 'maven-3'
    }
    
    stages {
        stage('Initialize') {
            steps {
                sh 'docker --version && kubectl version --client'
            }
        }
        
        stage('Build Maven') {
            steps {
                dir("${DOCKER_DIR}") {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                dir("${DOCKER_DIR}") {
                    sh "docker build -t ${IMAGE_TAG} ."
                }
            }
        }
        
        stage('Push Image') {
            steps {
                sh "docker push ${IMAGE_TAG}"
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                sh """
                kubectl set image deployment/${K8S_DEPLOYMENT} \
                ${CONTAINER_NAME}=${IMAGE_TAG} -n ${K8S_NS}
                kubectl rollout restart deployment/${K8S_DEPLOYMENT} -n ${K8S_NS}
                """
            }
        }
    }
    
    post {
        always {
            sh 'docker image prune -f'
        }
    }
}
```

---

### **2. SERVICE BUKU (Port 8082)**
**Konfigurasi sama dengan Anggota**, dengan perbedaan:
- Port: `8082`
- Database: `bukudb`
- Field status: `"TERSEDIA"` atau `"DIPINJAM"`
- Endpoints: `/api/buku/*`

---

### **3. SERVICE PEMINJAMAN (Port 8084)**
**Konfigurasi sama dengan Anggota**, dengan perbedaan:
- Port: `8084`
- Database: `peminjaman`
- Field tambahan: `anggotaId`, `bukuId`, `tanggalPinjam`, `tanggalKembali`
- Field status: `"DIPINJAM"` atau `"DIKEMBALIKAN"`
- Endpoints: `/api/peminjaman/*`
- **Extra**: Integrasi dengan RestTemplate untuk get Anggota & Buku

---

### **4. SERVICE PENGEMBALIAN (Port 8085)**
**Konfigurasi sama dengan Peminjaman**, dengan perbedaan:
- Port: `8085`
- Database: `pengembalian`
- Field tambahan: `peminjamanId`, `tanggalDikembalikan`, `denda`, `terlambat`
- Field status: `"SELESAI"` atau `"PENDING"`
- Endpoints: `/api/pengembalian/*`
- **Extra**: Integrasi dengan RabbitMQ

---

### **5. COMMAND SERVICE - CQRS (Port 8088)**
**CQRS Write Side**
- Port: `8088`
- Database: `commanddb` (H2)
- Pattern: Command Pattern
- Publish events ke RabbitMQ
- Endpoints:
  - POST `/api/command/anggota`
  - POST `/api/command/buku`
  - POST `/api/command/peminjaman`

---

### **6. QUERY SERVICE - CQRS (Port 8087)**
**CQRS Read Side**
- Port: `8087`
- Database: `querydb` (H2)
- Pattern: Query Pattern
- Subscribe events dari RabbitMQ
- Endpoints:
  - GET `/api/query/anggota`
  - GET `/api/query/buku`
  - GET `/api/query/peminjaman`

---

### **7. EUREKA SERVER (Port 8761)**
**Service Discovery**
- Port: `8761`
- Dashboard: `http://localhost:8761`
- Semua service register ke Eureka
- No business logic, hanya service registry

---

### **8. API GATEWAY (Port 8080)**
**Load Balancer & Routing**
- Port: `8080`
- Route semua request ke service yang tepat
- Pattern: Gateway Pattern
- Integrasi dengan Eureka untuk service discovery

---

## ☸️ DEPLOYMENT KE KUBERNETES

### **Tahap 1: Setup Namespace**
```bash
kubectl apply -f kubernetes/01-namespace/namespace.yaml
```

Ini membuat 4 namespace:
- `perpustakaan-ns` - untuk semua microservices
- `jenkins-ns` - untuk Jenkins CI/CD
- `logging-ns` - untuk logging stack
- `monitoring-ns` - untuk monitoring stack

### **Tahap 2: Deploy Secrets**
```bash
kubectl apply -f kubernetes/02-secrets/
```

Secrets untuk:
- MongoDB credentials
- RabbitMQ credentials

### **Tahap 3: Deploy Infrastructure**
```bash
kubectl apply -f kubernetes/03-infrastructure/
```

Deploy:
- MongoDB (database)
- RabbitMQ (message broker)
- Persistent Volume Claims

### **Tahap 4: Deploy Eureka (Service Discovery)**
```bash
kubectl apply -f kubernetes/04-services/eureka-deployment.yaml
```

Wait for Eureka ready:
```bash
kubectl wait --for=condition=ready pod -l app=eureka -n perpustakaan-ns --timeout=180s
```

### **Tahap 5: Deploy Microservices**
```bash
kubectl apply -f kubernetes/04-services/anggota-deployment.yaml
kubectl apply -f kubernetes/04-services/buku-deployment.yaml
kubectl apply -f kubernetes/04-services/peminjaman-deployment.yaml
kubectl apply -f kubernetes/04-services/pengembalian-deployment.yaml
```

### **Tahap 6: Deploy Jenkins**
```bash
kubectl apply -f kubernetes/07-jenkins/jenkins-pvc.yaml
kubectl apply -f kubernetes/07-jenkins/jenkins-deployment.yaml
```

### **Tahap 7: Verifikasi Deployment**
```bash
# Check all pods
kubectl get pods -n perpustakaan-ns

# Check all services
kubectl get svc -n perpustakaan-ns

# Check Jenkins
kubectl get pods -n jenkins-ns
```

---

## 🔧 CI/CD DENGAN JENKINS

### **1. Akses Jenkins**
```bash
# Port forward untuk akses lokal
kubectl port-forward -n jenkins-ns svc/jenkins-service 30080:8080
```

Buka browser: `http://localhost:30080`

### **2. Get Initial Admin Password**
```bash
kubectl exec -n jenkins-ns -it deployment/jenkins -- cat /var/jenkins_home/secrets/initialAdminPassword
```

### **3. Konfigurasi Jenkins**

#### **A. Install Plugins:**
- Docker Pipeline
- Kubernetes
- Git
- Maven Integration

#### **B. Configure Maven:**
1. Manage Jenkins → Global Tool Configuration
2. Add Maven → Name: `maven-3`
3. Install automatically from Apache

#### **C. Create Pipeline untuk Anggota:**
1. New Item → Pipeline
2. Pipeline script from SCM
3. SCM: Git
4. Repository URL: (your git repo)
5. Script Path: `anggota/Jenkinsfile`

#### **D. Ulangi untuk semua service:**
- Buku Pipeline
- Peminjaman Pipeline
- Pengembalian Pipeline
- Command Service Pipeline
- Query Service Pipeline
- Eureka Pipeline
- API Gateway Pipeline

### **4. Jenkins Pipeline Flow**

```
┌─────────────┐
│  Git Push   │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Jenkins   │ Webhook Trigger
│   Detect    │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Maven Build │ mvn clean package -DskipTests
└──────┬──────┘
       │
       ▼
┌─────────────┐
│Docker Build │ docker build -t image:tag
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Push Image  │ docker push to registry
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Deploy    │ kubectl set image + rollout
│     K8s     │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Cleanup   │ docker image prune
└─────────────┘
```

---

## 📊 TESTING DAN MONITORING

### **1. Health Check Endpoints**
Semua service memiliki health check di:
```
http://localhost:{PORT}/actuator/health
```

Example:
- Anggota: http://localhost:8081/actuator/health
- Buku: http://localhost:8082/actuator/health
- Peminjaman: http://localhost:8084/actuator/health

### **2. Prometheus Metrics**
```
http://localhost:{PORT}/actuator/prometheus
```

### **3. H2 Console (Development)**
```
http://localhost:{PORT}/h2-console
```

Credentials:
- JDBC URL: Lihat di application.properties masing-masing service
- Username: `sa`
- Password: `password` (atau sesuai config)

### **4. Eureka Dashboard**
```bash
kubectl port-forward -n perpustakaan-ns svc/eureka-service 8761:8761
```
Buka: http://localhost:8761

### **5. Test API dengan cURL**

**Create Anggota:**
```bash
curl -X POST http://localhost:8081/api/anggota \
  -H "Content-Type: application/json" \
  -d '{
    "nama": "John Doe",
    "alamat": "Jl. Merdeka No. 123",
    "email": "john@example.com",
    "telepon": "08123456789",
    "status": "AKTIF"
  }'
```

**Get All Anggota:**
```bash
curl http://localhost:8081/api/anggota
```

**Update Anggota:**
```bash
curl -X PUT http://localhost:8081/api/anggota/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nama": "John Doe Updated",
    "alamat": "Jl. Merdeka No. 456",
    "email": "john.updated@example.com",
    "telepon": "08987654321",
    "status": "TIDAK_AKTIF"
  }'
```

**Delete Anggota:**
```bash
curl -X DELETE http://localhost:8081/api/anggota/1
```

---

## 🛠️ TROUBLESHOOTING

### **Problem 1: Pod Tidak Ready**
```bash
# Check pod status
kubectl get pods -n perpustakaan-ns

# Check pod logs
kubectl logs -n perpustakaan-ns <pod-name>

# Describe pod untuk detail error
kubectl describe pod -n perpustakaan-ns <pod-name>
```

**Solution:**
- Pastikan image sudah di-push ke registry
- Check health check endpoint
- Verifikasi resource limits tidak terlalu rendah

### **Problem 2: Service Tidak Bisa Connect**
```bash
# Check service
kubectl get svc -n perpustakaan-ns

# Test connectivity dari dalam cluster
kubectl run -it --rm debug --image=busybox --restart=Never -- sh
# Inside pod:
wget -O- http://anggota-service:8081/actuator/health
```

**Solution:**
- Pastikan service selector match dengan pod labels
- Verifikasi port configuration
- Check Eureka registration

### **Problem 3: Eureka Not Showing Services**
**Solution:**
- Pastikan `eureka.client.register-with-eureka=true`
- Verifikasi `eureka.client.service-url.defaultZone` benar
- Check network connectivity antar pods
- Wait 30-60 detik untuk registration

### **Problem 4: Jenkins Build Failed**
```bash
# Check Jenkins logs
kubectl logs -n jenkins-ns deployment/jenkins

# Check build console output di Jenkins UI
```

**Solution:**
- Pastikan Maven configured correctly
- Verifikasi Docker daemon accessible
- Check kubectl credentials
- Verifikasi Jenkinsfile syntax

### **Problem 5: Memory/Disk Full**
```bash
# Check disk usage
df -h

# Clean Docker
docker system prune -a

# Clean Kubernetes
kubectl delete pod --field-selector=status.phase==Failed -n perpustakaan-ns
```

**Solution:**
- Bersihkan unused Docker images
- Bersihkan unused volumes
- Increase disk space
- Adjust resource limits di deployment

---

## 📈 MONITORING RESOURCES

### **Check Resource Usage:**
```bash
# CPU & Memory per pod
kubectl top pods -n perpustakaan-ns

# Node resources
kubectl top nodes

# Detailed pod description
kubectl describe pod <pod-name> -n perpustakaan-ns
```

### **Scaling:**
```bash
# Scale deployment
kubectl scale deployment anggota-deployment --replicas=3 -n perpustakaan-ns

# Auto-scaling (HPA)
kubectl autoscale deployment anggota-deployment --min=2 --max=5 --cpu-percent=80 -n perpustakaan-ns
```

---

## ✅ CHECKLIST DEPLOYMENT

### **Pre-Deployment:**
- [ ] Docker Desktop running
- [ ] Kubernetes enabled dan running
- [ ] kubectl configured
- [ ] Maven Wrapper available (`mvnw.cmd` di setiap service)
- [ ] Local Docker registry ready (localhost:5000)

### **Build & Push Images:**
- [ ] Anggota service built & pushed
- [ ] Buku service built & pushed
- [ ] Peminjaman service built & pushed
- [ ] Pengembalian service built & pushed
- [ ] Command service built & pushed
- [ ] Query service built & pushed
- [ ] Eureka service built & pushed
- [ ] API Gateway built & pushed

### **Kubernetes Deployment:**
- [ ] Namespace created
- [ ] Secrets deployed
- [ ] MongoDB deployed & ready
- [ ] RabbitMQ deployed & ready
- [ ] Eureka deployed & ready
- [ ] All microservices deployed
- [ ] Jenkins deployed & configured

### **Verification:**
- [ ] All pods running
- [ ] All services accessible
- [ ] Eureka shows all registered services
- [ ] Health checks passing
- [ ] Prometheus metrics available
- [ ] Jenkins pipelines configured

---

## 🎯 SUMMARY

### **Total Services: 8**
1. ✅ Anggota (8081)
2. ✅ Buku (8082)
3. ✅ Peminjaman (8084)
4. ✅ Pengembalian (8085)
5. ✅ Command Service (8088)
6. ✅ Query Service (8087)
7. ✅ Eureka Server (8761)
8. ✅ API Gateway (8080)

### **Infrastructure Components:**
- ✅ MongoDB
- ✅ RabbitMQ
- ✅ Jenkins CI/CD
- ✅ Kubernetes Cluster

### **Features Implemented:**
- ✅ CRUD Operations dengan PUT endpoint
- ✅ Microservices Architecture
- ✅ Service Discovery (Eureka)
- ✅ API Gateway
- ✅ CQRS Pattern (Command & Query)
- ✅ Event-Driven Architecture (RabbitMQ)
- ✅ Containerization (Docker)
- ✅ Orchestration (Kubernetes)
- ✅ CI/CD Pipeline (Jenkins)
- ✅ Health Checks
- ✅ Monitoring (Prometheus)
- ✅ Logging

---

## 📞 QUICK REFERENCE

### **Ports:**
| Service | Port | URL |
|---------|------|-----|
| API Gateway | 8080 | http://localhost:8080 |
| Anggota | 8081 | http://localhost:8081/api/anggota |
| Buku | 8082 | http://localhost:8082/api/buku |
| Peminjaman | 8084 | http://localhost:8084/api/peminjaman |
| Pengembalian | 8085 | http://localhost:8085/api/pengembalian |
| Query Service | 8087 | http://localhost:8087/api/query |
| Command Service | 8088 | http://localhost:8088/api/command |
| Eureka | 8761 | http://localhost:8761 |
| Jenkins | 30080 | http://localhost:30080 |

### **Common Commands:**
```bash
# Start Kubernetes
kubectl cluster-info

# Deploy all
kubectl apply -f kubernetes/

# Check status
kubectl get all -n perpustakaan-ns

# Port forward service
kubectl port-forward -n perpustakaan-ns svc/anggota-service 8081:8081

# View logs
kubectl logs -f -n perpustakaan-ns deployment/anggota-deployment

# Restart deployment
kubectl rollout restart deployment/anggota-deployment -n perpustakaan-ns
```

---

**🎉 PROYEK SIAP PRODUCTION!**

Semua konfigurasi sudah sesuai dengan standar enterprise dan best practices untuk microservices architecture dengan Kubernetes dan Jenkins CI/CD.
