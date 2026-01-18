package com.key.buku.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;

import java.util.List;

import com.key.buku.service.BukuService;
import com.key.buku.model.Buku;

@RestController
@RequestMapping("/api/buku")
public class BukuController {

  @Autowired
  private BukuService bukuService;

  @GetMapping
  public List<Buku> getAllBuku() {
    return bukuService.getAllBuku();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Buku> getBukuById(@PathVariable Long id) {
    Buku buku = bukuService.getBukuById(id);
    return buku != null ? ResponseEntity.ok(buku) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public Buku createBuku(@RequestBody Buku buku) {
    return bukuService.createBuku(buku);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Buku> updateBuku(@PathVariable Long id, @RequestBody Buku buku) {
    Buku updatedBuku = bukuService.updateBuku(id, buku);
    return updatedBuku != null ? ResponseEntity.ok(updatedBuku) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBuku(@PathVariable Long id) {
    bukuService.deleteBuku(id);
    return ResponseEntity.ok().build();
  }

}
