package com.key.commandservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBukuCommand {
    @NotBlank(message = "Judul tidak boleh kosong")
    private String judul;
    
    @NotBlank(message = "Pengarang tidak boleh kosong")
    private String pengarang;
    
    @NotBlank(message = "Penerbit tidak boleh kosong")
    private String penerbit;
    
    @NotNull(message = "Tahun terbit tidak boleh kosong")
    @Min(value = 1900, message = "Tahun terbit tidak valid")
    private Integer tahunTerbit;
    
    @NotBlank(message = "ISBN tidak boleh kosong")
    private String isbn;
    
    @NotNull(message = "Stok tidak boleh kosong")
    @Min(value = 0, message = "Stok tidak boleh negatif")
    private Integer stok;
}