package com.key.anggota.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.key.anggota.model.Anggota;
import com.key.anggota.service.AnggotaService;

import java.util.List;

@RestController
@RequestMapping("/api/anggota")
public class AnggotaController {
    
    @Autowired
    private AnggotaService anggotaService;
    
    // GET all anggota
    @GetMapping
    public List<Anggota> getAllAnggota() {
        return anggotaService.getAllAnggota();
    }
    
    // GET anggota by ID
    @GetMapping("/{id}")
    public ResponseEntity<Anggota> getAnggotaById(@PathVariable Long id) {
        Anggota anggota = anggotaService.getAnggotaById(id);
        return anggota != null ? ResponseEntity.ok(anggota) : ResponseEntity.notFound().build();
    }
    
    // POST create new anggota
    @PostMapping
    public Anggota createAnggota(@RequestBody Anggota anggota) {
        return anggotaService.createAnggota(anggota);
    }
    
    // PUT update anggota
    @PutMapping("/{id}")
    public ResponseEntity<Anggota> updateAnggota(@PathVariable Long id, @RequestBody Anggota anggota) {
        Anggota updatedAnggota = anggotaService.updateAnggota(id, anggota);
        return updatedAnggota != null ? ResponseEntity.ok(updatedAnggota) : ResponseEntity.notFound().build();
    }
    
    // DELETE anggota
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnggota(@PathVariable Long id) {
        anggotaService.deleteAnggota(id);
        return ResponseEntity.ok().build();
    }
}