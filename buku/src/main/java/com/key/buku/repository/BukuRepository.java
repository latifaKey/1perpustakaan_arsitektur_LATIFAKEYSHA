package com.key.buku.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.key.buku.model.Buku;

@Repository
public interface BukuRepository extends JpaRepository<Buku, Long> {
    // Method khusus dapat ditambahkan di sini jika diperlukan
}