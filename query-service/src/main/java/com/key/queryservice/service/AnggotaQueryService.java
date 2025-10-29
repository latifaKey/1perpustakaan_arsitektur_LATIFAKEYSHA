package com.key.queryservice.service;

import com.key.queryservice.model.AnggotaView;
import com.key.queryservice.repository.AnggotaViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnggotaQueryService {
    
    @Autowired
    private AnggotaViewRepository anggotaViewRepository;
    
    public List<AnggotaView> getAllAnggota() {
        return anggotaViewRepository.findAll();
    }
    
    public Optional<AnggotaView> getAnggotaById(Long id) {
        return anggotaViewRepository.findById(id);
    }
    
    public List<AnggotaView> searchAnggotaByName(String name) {
        return anggotaViewRepository.findByNamaContaining(name);
    }
    
    public List<AnggotaView> searchAnggotaByKeyword(String keyword) {
        return anggotaViewRepository.searchByKeyword(keyword);
    }
    
    public boolean isAnggotaActive(Long anggotaId) {
        Optional<AnggotaView> anggota = anggotaViewRepository.findById(anggotaId);
        return anggota.isPresent() && "AKTIF".equals(anggota.get().getStatus());
    }
}