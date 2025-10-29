package com.key.commandservice.controller;

import com.key.commandservice.command.CreateAnggotaCommand;
import com.key.commandservice.service.AnggotaCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/command/anggota")
@Validated
public class AnggotaCommandController {
    
    @Autowired
    private AnggotaCommandService anggotaCommandService;
    
    @PostMapping
    public ResponseEntity<Long> createAnggota(@RequestBody @Validated CreateAnggotaCommand command) {
        try {
            Long anggotaId = anggotaCommandService.createAnggota(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(anggotaId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnggota(@PathVariable Long id, @RequestBody @Validated CreateAnggotaCommand command) {
        try {
            anggotaCommandService.updateAnggota(id, command);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnggota(@PathVariable Long id) {
        try {
            anggotaCommandService.deleteAnggota(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}