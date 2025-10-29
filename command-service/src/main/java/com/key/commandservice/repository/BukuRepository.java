package com.key.commandservice.repository;

import com.key.commandservice.model.Buku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BukuRepository extends JpaRepository<Buku, Long> {
    boolean existsByIsbn(String isbn);
}