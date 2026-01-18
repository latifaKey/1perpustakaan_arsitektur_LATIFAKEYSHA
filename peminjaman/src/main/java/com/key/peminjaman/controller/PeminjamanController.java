package com.key.peminjaman.controller;

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

import com.key.peminjaman.model.Peminjaman;
import com.key.peminjaman.service.PeminjamanService;
import com.key.peminjaman.vo.ResponseTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/peminjaman")
public class PeminjamanController {
    
    @Autowired
    private PeminjamanService peminjamanService;
    
    // GET all peminjaman
    @GetMapping
    public List<Peminjaman> getAllPeminjaman() {
        return peminjamanService.getAllPeminjaman();
    }
    
    // GET peminjaman by ID
    @GetMapping("/{id}")
    public ResponseEntity<Peminjaman> getPeminjamanById(@PathVariable Long id) {
        Peminjaman peminjaman = peminjamanService.getPeminjamanById(id);
        return peminjaman != null ? ResponseEntity.ok(peminjaman) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<List<ResponseTemplate>> getPeminjamanWithAnggotaId(@PathVariable Long id) {
        List<ResponseTemplate> responseTemplates = peminjamanService.getPeminjamanWithAnggotaId(id);
        return responseTemplates != null ? ResponseEntity.ok(responseTemplates) : ResponseEntity.notFound().build();
    }

    
    // POST create new peminjaman
    @PostMapping
    public Peminjaman createPeminjaman(@RequestBody Peminjaman peminjaman) {
        return peminjamanService.createPeminjaman(peminjaman);
    }
    
    // PUT update peminjaman
    @PutMapping("/{id}")
    public ResponseEntity<Peminjaman> updatePeminjaman(@PathVariable Long id, @RequestBody Peminjaman peminjaman) {
        Peminjaman updatedPeminjaman = peminjamanService.updatePeminjaman(id, peminjaman);
        return updatedPeminjaman != null ? ResponseEntity.ok(updatedPeminjaman) : ResponseEntity.notFound().build();
    }
    
    // DELETE peminjaman
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeminjaman(@PathVariable Long id) {
        peminjamanService.deletePeminjaman(id);
        return ResponseEntity.ok().build();
    }
}