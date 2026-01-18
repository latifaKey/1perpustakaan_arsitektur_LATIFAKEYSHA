package com.key.anggota.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Anggota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    private String alamat;
    private String email;
    private String telepon;
    private String status; // Status anggota: "AKTIF" atau "TIDAK_AKTIF"
}
