package com.key.buku.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.key.buku.model.Buku;
import com.key.buku.repository.BukuRepository;

@Service
public class BukuService {
    @Autowired
    private BukuRepository bukuRepository;

    public List<Buku> getAllBuku() {
        return bukuRepository.findAll();
    }

    public Buku getBukuById(Long id) {
        Optional<Buku> buku = bukuRepository.findById(id);
        return buku.orElse(null);
    }

    public Buku createBuku(Buku buku) {
        return bukuRepository.save(buku);
    }

    public Buku updateBuku(Long id, Buku bukuDetails) {
        Optional<Buku> bukuOptional = bukuRepository.findById(id);
        if (bukuOptional.isPresent()) {
            Buku buku = bukuOptional.get();
            buku.setJudul(bukuDetails.getJudul());
            buku.setPengarang(bukuDetails.getPengarang());
            buku.setPenerbit(bukuDetails.getPenerbit());
            buku.setStatus(bukuDetails.getStatus());
            return bukuRepository.save(buku);
        }
        return null;
    }

    public void deleteBuku(Long id) {
        bukuRepository.deleteById(id);
    }
}