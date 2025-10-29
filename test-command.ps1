# Test Script untuk Command Service dan Query Service

Write-Host "=== Testing Command Service ===" -ForegroundColor Green

# Test 1: Create Anggota
Write-Host "1. Testing POST /api/command/anggota" -ForegroundColor Yellow

$headers = @{
    'Content-Type' = 'application/json'
}

$anggotaData = @{
    nama = "John Doe"
    email = "john.doe@email.com" 
    alamat = "Jakarta"
    noTelepon = "081234567890"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8088/api/command/anggota" -Method POST -Body $anggotaData -Headers $headers
    Write-Host "✅ Success! Anggota ID: $response" -ForegroundColor Green
    $anggotaId = $response
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Response: $($_.ErrorDetails.Message)" -ForegroundColor Red
}

Write-Host "`n=== Testing Query Service ===" -ForegroundColor Green

# Test 2: Get All Anggota
Write-Host "2. Testing GET /api/query/anggota" -ForegroundColor Yellow

try {
    $allAnggota = Invoke-RestMethod -Uri "http://localhost:8087/api/query/anggota" -Method GET
    Write-Host "✅ Success! Found $($allAnggota.Count) anggota" -ForegroundColor Green
    $allAnggota | Format-Table
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Response: $($_.ErrorDetails.Message)" -ForegroundColor Red
}

# Test 3: Get Anggota by ID (if we have one)
if ($anggotaId) {
    Write-Host "3. Testing GET /api/query/anggota/$anggotaId" -ForegroundColor Yellow
    
    try {
        $anggota = Invoke-RestMethod -Uri "http://localhost:8087/api/query/anggota/$anggotaId" -Method GET
        Write-Host "✅ Success! Found anggota:" -ForegroundColor Green
        $anggota | Format-List
    } catch {
        Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "Response: $($_.ErrorDetails.Message)" -ForegroundColor Red
    }
}

Write-Host "`n=== Test Complete ===" -ForegroundColor Green