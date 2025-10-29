package com.key.queryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class PeminjamanView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long anggotaId;
    private Long bukuId;
    private String namaAnggota;
    private String judulBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String status;
}