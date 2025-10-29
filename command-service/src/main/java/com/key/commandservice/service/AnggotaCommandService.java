package com.key.commandservice.service;

import com.key.commandservice.command.CreateAnggotaCommand;
import com.key.commandservice.event.AnggotaCreatedEvent;
import com.key.commandservice.model.Anggota;
import com.key.commandservice.repository.AnggotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class AnggotaCommandService {
    
    @Autowired
    private AnggotaRepository anggotaRepository;
    
    @Autowired
    private EventPublisher eventPublisher;
    
    public Long createAnggota(CreateAnggotaCommand command) {
        // Validate email uniqueness
        if (anggotaRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email sudah terdaftar: " + command.getEmail());
        }
        
        // Create entity
        Anggota anggota = new Anggota();
        anggota.setNama(command.getNama());
        anggota.setEmail(command.getEmail());
        anggota.setNoTelepon(command.getNoTelepon());
        anggota.setAlamat(command.getAlamat());
        anggota.setTanggalDaftar(LocalDate.now());
        
        // Save to database
        Anggota savedAnggota = anggotaRepository.save(anggota);
        
        // Publish event
        AnggotaCreatedEvent event = new AnggotaCreatedEvent(
            savedAnggota.getId(),
            savedAnggota.getNama(),
            savedAnggota.getEmail(),
            savedAnggota.getNoTelepon(),
            savedAnggota.getAlamat(),
            LocalDateTime.now()
        );
        
        eventPublisher.publishAnggotaCreated(event);
        
        return savedAnggota.getId();
    }
    
    public void updateAnggota(Long id, CreateAnggotaCommand command) {
        Anggota anggota = anggotaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Anggota tidak ditemukan dengan ID: " + id));
        
        // Check email uniqueness if email is being changed
        if (!anggota.getEmail().equals(command.getEmail()) && 
            anggotaRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email sudah terdaftar: " + command.getEmail());
        }
        
        anggota.setNama(command.getNama());
        anggota.setEmail(command.getEmail());
        anggota.setNoTelepon(command.getNoTelepon());
        anggota.setAlamat(command.getAlamat());
        
        anggotaRepository.save(anggota);
        
        // Publish update event
        AnggotaCreatedEvent event = new AnggotaCreatedEvent(
            anggota.getId(),
            anggota.getNama(),
            anggota.getEmail(),
            anggota.getNoTelepon(),
            anggota.getAlamat(),
            LocalDateTime.now()
        );
        
        eventPublisher.publishAnggotaUpdated(event);
    }
    
    public void deleteAnggota(Long id) {
        if (!anggotaRepository.existsById(id)) {
            throw new IllegalArgumentException("Anggota tidak ditemukan dengan ID: " + id);
        }
        
        anggotaRepository.deleteById(id);
        
        // Publish delete event
        AnggotaCreatedEvent event = new AnggotaCreatedEvent();
        event.setId(id);
        event.setTimestamp(LocalDateTime.now());
        
        eventPublisher.publishAnggotaDeleted(event);
    }
}