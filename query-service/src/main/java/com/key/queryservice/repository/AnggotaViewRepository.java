package com.key.queryservice.repository;

import com.key.queryservice.model.AnggotaView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnggotaViewRepository extends JpaRepository<AnggotaView, Long> {
    
    List<AnggotaView> findByNamaContaining(String nama);
    
    List<AnggotaView> findByEmail(String email);
    
    @Query("SELECT a FROM AnggotaView a WHERE a.nama LIKE %:keyword% OR a.email LIKE %:keyword%")
    List<AnggotaView> searchByKeyword(@Param("keyword") String keyword);
}