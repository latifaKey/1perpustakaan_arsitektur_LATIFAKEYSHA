package com.key.commandservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnggotaCreatedEvent {
    private Long id;
    private String nama;
    private String email;
    private String noTelepon;
    private String alamat;
    private LocalDateTime timestamp;
}