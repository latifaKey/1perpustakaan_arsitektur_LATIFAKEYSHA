package com.key.queryservice.repository;

import com.key.queryservice.model.BukuView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BukuViewRepository extends JpaRepository<BukuView, Long> {
    
    List<BukuView> findByJudulContaining(String judul);
    
    List<BukuView> findByPengarangContaining(String pengarang);
    
    List<BukuView> findByPenerbitContaining(String penerbit);
    
    List<BukuView> findByStokGreaterThan(Integer stok);
    
    @Query("SELECT b FROM BukuView b WHERE b.judul LIKE %:keyword% OR b.pengarang LIKE %:keyword% OR b.penerbit LIKE %:keyword%")
    List<BukuView> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT b FROM BukuView b WHERE b.stok > 0")
    List<BukuView> findAvailableBooks();
}