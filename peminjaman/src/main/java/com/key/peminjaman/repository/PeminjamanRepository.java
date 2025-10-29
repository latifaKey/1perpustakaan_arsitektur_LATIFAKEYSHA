package com.key.peminjaman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.key.peminjaman.model.Peminjaman;

@Repository
public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long> {
    // Method khusus dapat ditambahkan di sini jika diperlukan
}