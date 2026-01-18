# Quick Start Script - Perpustakaan Microservices
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Quick Start - Perpustakaan Deployment" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Build all services
Write-Host "Step 1: Building all services..." -ForegroundColor Yellow
& .\build-all.ps1

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "Build failed! Please fix the errors before continuing." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Step 2: Building Docker images..." -ForegroundColor Yellow
docker compose build

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "Docker build failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Step 3: Starting all services..." -ForegroundColor Yellow
docker compose up -d

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Deployment Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Services are running on:" -ForegroundColor Cyan
Write-Host "  • Eureka Server:           http://localhost:8761" -ForegroundColor White
Write-Host "  • API Gateway:             http://localhost:8080" -ForegroundColor White
Write-Host "  • Anggota Service:         http://localhost:8081" -ForegroundColor White
Write-Host "  • Buku Service:            http://localhost:8082" -ForegroundColor White
Write-Host "  • Peminjaman Service:      http://localhost:8083" -ForegroundColor White
Write-Host "  • Pengembalian Service:    http://localhost:8084" -ForegroundColor White
Write-Host "  • Command Service:         http://localhost:8085" -ForegroundColor White
Write-Host "  • Query Service:           http://localhost:8086" -ForegroundColor White
Write-Host "  • RabbitMQ Peminjaman:     http://localhost:8087" -ForegroundColor White
Write-Host "  • RabbitMQ Pengembalian:   http://localhost:8088" -ForegroundColor White
Write-Host ""
Write-Host "Useful commands:" -ForegroundColor Yellow
Write-Host "  docker compose ps              # Check status"
Write-Host "  docker compose logs -f         # View logs"
Write-Host "  docker compose down            # Stop all services"
Write-Host ""
