package com.key.queryservice.controller;

import com.key.queryservice.model.PeminjamanView;
import com.key.queryservice.service.PeminjamanQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/query/peminjaman")
public class PeminjamanQueryController {
    
    @Autowired
    private PeminjamanQueryService peminjamanQueryService;
    
    @GetMapping
    public ResponseEntity<List<PeminjamanView>> getAllPeminjaman() {
        List<PeminjamanView> peminjamanList = peminjamanQueryService.getAllPeminjaman();
        return ResponseEntity.ok(peminjamanList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PeminjamanView> getPeminjamanById(@PathVariable Long id) {
        Optional<PeminjamanView> peminjaman = peminjamanQueryService.getPeminjamanById(id);
        return peminjaman.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/anggota/{anggotaId}")
    public ResponseEntity<List<PeminjamanView>> getPeminjamanByAnggotaId(@PathVariable Long anggotaId) {
        List<PeminjamanView> peminjamanList = peminjamanQueryService.getPeminjamanByAnggotaId(anggotaId);
        return ResponseEntity.ok(peminjamanList);
    }
    
    @GetMapping("/buku/{bukuId}")
    public ResponseEntity<List<PeminjamanView>> getPeminjamanByBukuId(@PathVariable Long bukuId) {
        List<PeminjamanView> peminjamanList = peminjamanQueryService.getPeminjamanByBukuId(bukuId);
        return ResponseEntity.ok(peminjamanList);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PeminjamanView>> getPeminjamanByStatus(@PathVariable String status) {
        List<PeminjamanView> peminjamanList = peminjamanQueryService.getPeminjamanByStatus(status);
        return ResponseEntity.ok(peminjamanList);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<PeminjamanView>> getActivePeminjaman() {
        List<PeminjamanView> peminjamanList = peminjamanQueryService.getActivePeminjaman();
        return ResponseEntity.ok(peminjamanList);
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<List<PeminjamanView>> getOverduePeminjaman() {
        List<PeminjamanView> peminjamanList = peminjamanQueryService.getOverduePeminjaman();
        return ResponseEntity.ok(peminjamanList);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<PeminjamanView>> getPeminjamanByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PeminjamanView> peminjamanList = peminjamanQueryService.getPeminjamanByDateRange(startDate, endDate);
        return ResponseEntity.ok(peminjamanList);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<PeminjamanView>> searchPeminjamanByKeyword(@RequestParam String keyword) {
        List<PeminjamanView> peminjamanList = peminjamanQueryService.searchPeminjamanByKeyword(keyword);
        return ResponseEntity.ok(peminjamanList);
    }
}