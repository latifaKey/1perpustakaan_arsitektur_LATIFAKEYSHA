package com.key.commandservice.repository;

import com.key.commandservice.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long> {
    List<Peminjaman> findByAnggotaIdAndStatus(Long anggotaId, String status);
    List<Peminjaman> findByBukuIdAndStatus(Long bukuId, String status);
}