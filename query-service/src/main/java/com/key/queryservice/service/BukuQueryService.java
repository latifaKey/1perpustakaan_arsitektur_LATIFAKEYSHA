package com.key.queryservice.service;

import com.key.queryservice.model.BukuView;
import com.key.queryservice.repository.BukuViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BukuQueryService {
    
    @Autowired
    private BukuViewRepository bukuViewRepository;
    
    public List<BukuView> getAllBuku() {
        return bukuViewRepository.findAll();
    }
    
    public Optional<BukuView> getBukuById(Long id) {
        return bukuViewRepository.findById(id);
    }
    
    public List<BukuView> searchBukuByTitle(String judul) {
        return bukuViewRepository.findByJudulContaining(judul);
    }
    
    public List<BukuView> searchBukuByAuthor(String pengarang) {
        return bukuViewRepository.findByPengarangContaining(pengarang);
    }
    
    public List<BukuView> searchBukuByKeyword(String keyword) {
        return bukuViewRepository.searchByKeyword(keyword);
    }
    
    public List<BukuView> getAvailableBooks() {
        return bukuViewRepository.findAvailableBooks();
    }
    
    public boolean isBukuAvailable(Long bukuId) {
        Optional<BukuView> buku = bukuViewRepository.findById(bukuId);
        return buku.isPresent() && buku.get().getStok() > 0;
    }
}