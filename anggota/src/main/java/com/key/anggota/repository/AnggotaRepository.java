package com.key.anggota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.key.anggota.model.Anggota;

@Repository
public interface AnggotaRepository extends JpaRepository<Anggota, Long> {
    // Method khusus dapat ditambahkan di sini jika diperlukan
}
