package com.key.pengembalian.repository;

import com.key.pengembalian.model.Pengembalian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PengembalianRepository extends JpaRepository<Pengembalian, Long> {
    // Method khusus dapat ditambahkan di sini jika diperlukan
}