package com.key.queryservice.service;

import com.key.queryservice.model.PeminjamanView;
import com.key.queryservice.repository.PeminjamanViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeminjamanQueryService {
    
    @Autowired
    private PeminjamanViewRepository peminjamanViewRepository;
    
    public List<PeminjamanView> getAllPeminjaman() {
        return peminjamanViewRepository.findAll();
    }
    
    public Optional<PeminjamanView> getPeminjamanById(Long id) {
        return peminjamanViewRepository.findById(id);
    }
    
    public List<PeminjamanView> getPeminjamanByAnggotaId(Long anggotaId) {
        return peminjamanViewRepository.findByAnggotaId(anggotaId);
    }
    
    public List<PeminjamanView> getPeminjamanByBukuId(Long bukuId) {
        return peminjamanViewRepository.findByBukuId(bukuId);
    }
    
    public List<PeminjamanView> getPeminjamanByStatus(String status) {
        return peminjamanViewRepository.findByStatus(status);
    }
    
    public List<PeminjamanView> getActivePeminjaman() {
        return peminjamanViewRepository.findActivePeminjaman();
    }
    
    public List<PeminjamanView> getOverduePeminjaman() {
        return peminjamanViewRepository.findOverduePeminjaman(LocalDate.now());
    }
    
    public List<PeminjamanView> getPeminjamanByDateRange(LocalDate startDate, LocalDate endDate) {
        return peminjamanViewRepository.findByTanggalPinjamBetween(startDate, endDate);
    }
    
    public List<PeminjamanView> searchPeminjamanByKeyword(String keyword) {
        return peminjamanViewRepository.searchByKeyword(keyword);
    }
}