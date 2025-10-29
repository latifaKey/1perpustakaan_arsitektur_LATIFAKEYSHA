package com.key.commandservice.repository;

import com.key.commandservice.model.Anggota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnggotaRepository extends JpaRepository<Anggota, Long> {
    boolean existsByEmail(String email);
}