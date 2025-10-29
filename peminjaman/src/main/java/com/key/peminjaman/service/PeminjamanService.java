package com.key.peminjaman.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.key.peminjaman.model.Peminjaman;
import com.key.peminjaman.repository.PeminjamanRepository;

@Service
public class PeminjamanService {
    
    @Autowired
    private PeminjamanRepository peminjamanRepository;
    
    public List<Peminjaman> getAllPeminjaman() {
        return peminjamanRepository.findAll();
    }
    
    public Peminjaman getPeminjamanById(Long id) {
        Optional<Peminjaman> peminjaman = peminjamanRepository.findById(id);
        return peminjaman.orElse(null);
    }
    
    public Peminjaman createPeminjaman(Peminjaman peminjaman) {
        return peminjamanRepository.save(peminjaman);
    }
    
    public void deletePeminjaman(Long id) {
        peminjamanRepository.deleteById(id);
    }
}