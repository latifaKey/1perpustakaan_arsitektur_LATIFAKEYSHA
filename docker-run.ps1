# Docker Management Script - Perpustakaan
param(
    [switch]$Build,
    [switch]$Up,
    [switch]$Down,
    [switch]$Logs,
    [switch]$Clean,
    [string]$Service = ""
)

function Show-Help {
    Write-Host "Docker Management Script - Perpustakaan" -ForegroundColor Cyan
    Write-Host "Usage:" -ForegroundColor Yellow
    Write-Host "  .\docker-run.ps1 -Build          # Build all Docker images"
    Write-Host "  .\docker-run.ps1 -Up             # Start all services"
    Write-Host "  .\docker-run.ps1 -Down           # Stop all services"
    Write-Host "  .\docker-run.ps1 -Logs           # Show logs"
    Write-Host "  .\docker-run.ps1 -Clean          # Clean all containers and images"
    Write-Host "  .\docker-run.ps1 -Logs -Service perpustakaan-anggota  # Show logs for specific service"
    Write-Host ""
}

if (-not ($Build -or $Up -or $Down -or $Logs -or $Clean)) {
    Show-Help
    exit
}

if ($Build) {
    Write-Host "Building all Docker images..." -ForegroundColor Cyan
    docker compose build
    Write-Host "Build complete!" -ForegroundColor Green
}

if ($Up) {
    Write-Host "Starting all services..." -ForegroundColor Cyan
    docker compose up -d
    Write-Host ""
    Write-Host "Services are starting up..." -ForegroundColor Green
    Write-Host "Check status with: docker compose ps" -ForegroundColor Yellow
    Write-Host "View logs with: .\docker-run.ps1 -Logs" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Eureka Dashboard: http://localhost:8761" -ForegroundColor Cyan
    Write-Host "API Gateway: http://localhost:8080" -ForegroundColor Cyan
}

if ($Down) {
    Write-Host "Stopping all services..." -ForegroundColor Cyan
    docker compose down
    Write-Host "All services stopped!" -ForegroundColor Green
}

if ($Logs) {
    if ($Service -ne "") {
        Write-Host "Showing logs for $Service..." -ForegroundColor Cyan
        docker compose logs -f $Service
    } else {
        Write-Host "Showing logs for all services..." -ForegroundColor Cyan
        docker compose logs -f
    }
}

if ($Clean) {
    Write-Host "Cleaning up Docker resources..." -ForegroundColor Yellow
    Write-Host "This will remove all containers, images, and volumes." -ForegroundColor Red
    $confirm = Read-Host "Are you sure? (yes/no)"
    
    if ($confirm -eq "yes") {
        docker compose down -v --rmi all
        Write-Host "Cleanup complete!" -ForegroundColor Green
    } else {
        Write-Host "Cleanup cancelled." -ForegroundColor Yellow
    }
}
