package com.key.queryservice.repository;

import com.key.queryservice.model.PeminjamanView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PeminjamanViewRepository extends JpaRepository<PeminjamanView, Long> {
    
    List<PeminjamanView> findByAnggotaId(Long anggotaId);
    
    List<PeminjamanView> findByBukuId(Long bukuId);
    
    List<PeminjamanView> findByStatus(String status);
    
    List<PeminjamanView> findByTanggalPinjamBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT p FROM PeminjamanView p WHERE p.status = 'AKTIF'")
    List<PeminjamanView> findActivePeminjaman();
    
    @Query("SELECT p FROM PeminjamanView p WHERE p.tanggalKembali < :currentDate AND p.status = 'AKTIF'")
    List<PeminjamanView> findOverduePeminjaman(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT p FROM PeminjamanView p WHERE p.namaAnggota LIKE %:keyword% OR p.judulBuku LIKE %:keyword%")
    List<PeminjamanView> searchByKeyword(@Param("keyword") String keyword);
}