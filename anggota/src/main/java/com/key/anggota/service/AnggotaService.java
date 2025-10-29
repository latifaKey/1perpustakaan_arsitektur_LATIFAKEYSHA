package com.key.anggota.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.key.anggota.model.Anggota;
import com.key.anggota.repository.AnggotaRepository;

@Service
public class AnggotaService {
    
    @Autowired
    private AnggotaRepository anggotaRepository;
    
    public List<Anggota> getAllAnggota() {
        return anggotaRepository.findAll();
    }
    
    public Anggota getAnggotaById(Long id) {
        Optional<Anggota> anggota = anggotaRepository.findById(id);
        return anggota.orElse(null);
    }
    
    public Anggota createAnggota(Anggota anggota) {
        return anggotaRepository.save(anggota);
    }
    
    public void deleteAnggota(Long id) {
        anggotaRepository.deleteById(id);
    }
}
