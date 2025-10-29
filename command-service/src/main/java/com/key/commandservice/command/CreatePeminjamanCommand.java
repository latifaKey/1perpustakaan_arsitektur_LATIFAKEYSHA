package com.key.commandservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePeminjamanCommand {
    @NotNull(message = "Anggota ID tidak boleh kosong")
    private Long anggotaId;
    
    @NotNull(message = "Buku ID tidak boleh kosong")
    private Long bukuId;
    
    @NotNull(message = "Tanggal pinjam tidak boleh kosong")
    private LocalDate tanggalPinjam;
    
    @NotNull(message = "Tanggal kembali tidak boleh kosong")
    private LocalDate tanggalKembali;
}