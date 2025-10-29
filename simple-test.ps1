Write-Host "=== TESTING COMMAND & QUERY SERVICE ===" -ForegroundColor Cyan

# Test Command Service
Write-Host "`n1. Testing Command Service (CREATE)" -ForegroundColor Yellow
$commandUrl = "http://localhost:8088/api/command/anggota"
$body = '{"nama":"John Doe","email":"john@email.com","alamat":"Jakarta","noTelepon":"081234567890"}'

try {
    $anggotaId = Invoke-RestMethod -Uri $commandUrl -Method POST -Body $body -ContentType "application/json"
    Write-Host "✅ SUCCESS! Created Anggota with ID: $anggotaId" -ForegroundColor Green
} catch {
    Write-Host "❌ FAILED: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.ErrorDetails.Message) {
        Write-Host "Details: $($_.ErrorDetails.Message)" -ForegroundColor Red
    }
}

# Test Query Service  
Write-Host "`n2. Testing Query Service (READ)" -ForegroundColor Yellow
$queryUrl = "http://localhost:8087/api/query/anggota"

try {
    $allAnggota = Invoke-RestMethod -Uri $queryUrl -Method GET
    Write-Host "✅ SUCCESS! Found $($allAnggota.Count) anggota records" -ForegroundColor Green
    
    if ($allAnggota.Count -gt 0) {
        Write-Host "Sample data:" -ForegroundColor Cyan
        $allAnggota | Select-Object -First 3 | Format-Table -AutoSize
    }
} catch {
    Write-Host "❌ FAILED: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== TESTING COMPLETE ===" -ForegroundColor Cyan