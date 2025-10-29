package com.key.commandservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnggotaCommand {
    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;
    
    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;
    
    @NotBlank(message = "No telepon tidak boleh kosong")
    private String noTelepon;
    
    @NotBlank(message = "Alamat tidak boleh kosong")
    private String alamat;
}