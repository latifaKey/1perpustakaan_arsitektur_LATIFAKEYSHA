package com.key.commandservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeminjamanCreatedEvent {
    private Long id;
    private Long anggotaId;
    private Long bukuId;
    private String namaAnggota;
    private String judulBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private LocalDateTime timestamp;
}