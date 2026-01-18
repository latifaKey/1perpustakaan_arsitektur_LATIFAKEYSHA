# Build All Services Script - Perpustakaan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Building All Perpustakaan Microservices" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$services = @(
    "eureka",
    "api-get-away",
    "anggota",
    "buku",
    "peminjaman",
    "pengembalian",
    "command-service",
    "query-service",
    "rabbitmq-peminjaman-service",
    "rabbit_pengembalian"
)

$failedServices = @()
$successServices = @()

foreach ($service in $services) {
    Write-Host "Building $service..." -ForegroundColor Yellow
    
    if (Test-Path $service) {
        Push-Location $service
        
        # Clean and build
        & ../../mvnw.cmd clean package -DskipTests
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✓ $service built successfully" -ForegroundColor Green
            $successServices += $service
        } else {
            Write-Host "✗ $service build failed" -ForegroundColor Red
            $failedServices += $service
        }
        
        Pop-Location
        Write-Host ""
    } else {
        Write-Host "✗ Directory $service not found" -ForegroundColor Red
        $failedServices += $service
        Write-Host ""
    }
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Build Summary" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Successful: $($successServices.Count)" -ForegroundColor Green
foreach ($service in $successServices) {
    Write-Host "  ✓ $service" -ForegroundColor Green
}

if ($failedServices.Count -gt 0) {
    Write-Host ""
    Write-Host "Failed: $($failedServices.Count)" -ForegroundColor Red
    foreach ($service in $failedServices) {
        Write-Host "  ✗ $service" -ForegroundColor Red
    }
}

Write-Host ""
if ($failedServices.Count -eq 0) {
    Write-Host "All services built successfully! ✓" -ForegroundColor Green
    Write-Host "You can now run: docker-compose up -d" -ForegroundColor Cyan
} else {
    Write-Host "Some services failed to build. Please check the errors above." -ForegroundColor Red
}
