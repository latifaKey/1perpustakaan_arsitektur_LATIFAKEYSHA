# Docker Setup untuk Perpustakaan Microservices

## Struktur Microservices Perpustakaan

Project perpustakaan ini terdiri dari beberapa microservices:

1. **Eureka Server** (Port 8761) - Service Discovery
2. **API Gateway** (Port 8080) - API Gateway
3. **Anggota Service** (Port 8081) - Service untuk mengelola anggota perpustakaan
4. **Buku Service** (Port 8082) - Service untuk mengelola buku
5. **Peminjaman Service** (Port 8083) - Service untuk mengelola peminjaman buku
6. **Pengembalian Service** (Port 8084) - Service untuk mengelola pengembalian buku
7. **Command Service** (Port 8085) - CQRS Command Service
8. **Query Service** (Port 8086) - CQRS Query Service
9. **RabbitMQ Peminjaman** (Port 8087) - Message queue untuk peminjaman
10. **RabbitMQ Pengembalian** (Port 8088) - Message queue untuk pengembalian

## Prerequisites

- Docker Desktop terinstall
- Maven 3.6+ atau Maven Wrapper (mvnw) di folder parent
- Java 17+

## Cara Build dan Menjalankan

### Opsi 1: Quick Start (Paling Mudah)

```powershell
cd perpustakaan
.\quick-start.ps1
```

Script ini akan otomatis:
1. Build semua JAR files
2. Build Docker images
3. Start semua containers

### Opsi 2: Step-by-Step Manual

#### 1. Build semua JAR files

```powershell
cd perpustakaan
.\build-all.ps1
```

Atau build per service:

```powershell
cd eureka
..\..\mvnw.cmd clean package -DskipTests
cd ..

cd api-get-away
..\..\mvnw.cmd clean package -DskipTests
cd ..

cd anggota
..\..\mvnw.cmd clean package -DskipTests
cd ..

# ... dan seterusnya untuk semua service
```

#### 2. Build Docker images dan jalankan

```powershell
# Build semua images
docker compose build

# Jalankan semua services
docker compose up -d

# Lihat logs
docker compose logs -f
```

### Opsi 3: Menggunakan Docker Script

```powershell
# Build images
.\docker-run.ps1 -Build

# Start services
.\docker-run.ps1 -Up

# View logs
.\docker-run.ps1 -Logs

# View logs untuk service tertentu
.\docker-run.ps1 -Logs -Service perpustakaan-anggota

# Stop services
.\docker-run.ps1 -Down

# Clean up (hapus containers, images, volumes)
.\docker-run.ps1 -Clean
```

## Stop dan Remove Containers

```powershell
# Stop semua containers
docker compose down

# Stop dan hapus volumes
docker compose down -v

# Stop dan hapus volumes + images
docker compose down -v --rmi all
```

## Endpoint Testing

Setelah semua services running:

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Anggota Service**: http://localhost:8081
- **Buku Service**: http://localhost:8082
- **Peminjaman Service**: http://localhost:8083
- **Pengembalian Service**: http://localhost:8084
- **Command Service**: http://localhost:8085
- **Query Service**: http://localhost:8086
- **RabbitMQ Peminjaman**: http://localhost:8087
- **RabbitMQ Pengembalian**: http://localhost:8088

## Troubleshooting

### Service tidak bisa connect ke Eureka

Pastikan environment variable `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` sudah benar di docker-compose.yml

### Port sudah digunakan

Jika port sudah digunakan, ubah port mapping di docker-compose.yml:
```yaml
ports:
  - "8081:8081"  # ubah port pertama, misalnya "9081:8081"
```

### Rebuild service tertentu

```powershell
docker compose build --no-cache perpustakaan-anggota
docker compose up -d perpustakaan-anggota
```

### Lihat logs error

```powershell
# Logs semua services
docker compose logs

# Logs service tertentu dengan follow
docker compose logs -f perpustakaan-buku
```

### Check status containers

```powershell
docker compose ps
# atau
docker ps --filter "name=perpustakaan"
```

## Network

Semua services menggunakan network `perpustakaan-network` untuk komunikasi antar container.

## Health Check

Eureka Server memiliki health check yang akan memastikan service sudah siap sebelum services lain start.

## Urutan Startup

Docker Compose sudah dikonfigurasi untuk menjalankan services dalam urutan yang benar:

1. **Eureka Server** start terlebih dahulu
2. Setelah Eureka Server healthy, baru services lainnya start
3. **API Gateway** dan semua microservices akan register ke Eureka

## Tips

1. Pastikan semua JAR files sudah di-build sebelum menjalankan Docker
2. Tunggu Eureka Server selesai start (cek di http://localhost:8761)
3. Service lain akan otomatis register ke Eureka
4. Gunakan `docker compose logs -f` untuk monitoring real-time
5. Jika ada perubahan code, rebuild JAR dan Docker image

## Clean Up

Untuk membersihkan semua container, images, dan volumes:

```powershell
# Menggunakan script
.\docker-run.ps1 -Clean

# Atau manual
docker compose down -v --rmi all

# Complete cleanup semua Docker resources
docker system prune -a --volumes
```

## Monitoring

Untuk melihat resource usage:

```powershell
# CPU dan Memory usage
docker stats

# Hanya untuk perpustakaan containers
docker stats $(docker ps --filter "name=perpustakaan" -q)
```

## Useful Commands

```powershell
# List semua perpustakaan containers
docker ps --filter "name=perpustakaan"

# Stop semua perpustakaan containers
docker stop $(docker ps --filter "name=perpustakaan" -q)

# Remove semua perpustakaan containers
docker rm $(docker ps -a --filter "name=perpustakaan" -q)

# View logs dari multiple services
docker compose logs -f eureka-server api-gateway anggota-service

# Restart specific service
docker compose restart anggota-service

# Scale service (jika diperlukan)
docker compose up -d --scale anggota-service=2
```

## Development Workflow

1. Buat perubahan code di service
2. Build JAR: `cd <service> && ..\..\mvnw.cmd clean package -DskipTests`
3. Rebuild image: `docker compose build <service-name>`
4. Restart service: `docker compose up -d <service-name>`
5. Check logs: `docker compose logs -f <service-name>`

## Production Notes

Untuk production deployment:

1. Tambahkan health checks pada semua services
2. Konfigurasi resource limits (CPU, Memory)
3. Setup logging driver untuk centralized logging
4. Gunakan secrets untuk credentials
5. Implementasi monitoring dan alerting
6. Setup auto-restart policies
7. Pertimbangkan orchestration dengan Kubernetes

## Support

Jika ada masalah, cek:
1. Docker Desktop status
2. Port conflicts (`netstat -ano | findstr :<port>`)
3. Logs (`docker compose logs`)
4. Container status (`docker compose ps`)
5. Network connectivity (`docker network inspect perpustakaan-network`)
