package com.key.commandservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BukuCreatedEvent {
    private Long id;
    private String judul;
    private String pengarang;
    private String penerbit;
    private Integer tahunTerbit;
    private String isbn;
    private Integer stok;
    private LocalDateTime timestamp;
}