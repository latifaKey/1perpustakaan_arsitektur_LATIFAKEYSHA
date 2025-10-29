package com.key.commandservice.service;

import com.key.commandservice.event.AnggotaCreatedEvent;
import com.key.commandservice.event.BukuCreatedEvent;
import com.key.commandservice.event.PeminjamanCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void publishAnggotaCreated(AnggotaCreatedEvent event) {
        rabbitTemplate.convertAndSend("anggota.exchange", "anggota.created", event);
    }
    
    public void publishAnggotaUpdated(AnggotaCreatedEvent event) {
        rabbitTemplate.convertAndSend("anggota.exchange", "anggota.updated", event);
    }
    
    public void publishAnggotaDeleted(AnggotaCreatedEvent event) {
        rabbitTemplate.convertAndSend("anggota.exchange", "anggota.deleted", event);
    }
    
    public void publishBukuCreated(BukuCreatedEvent event) {
        rabbitTemplate.convertAndSend("buku.exchange", "buku.created", event);
    }
    
    public void publishBukuUpdated(BukuCreatedEvent event) {
        rabbitTemplate.convertAndSend("buku.exchange", "buku.updated", event);
    }
    
    public void publishPeminjamanCreated(PeminjamanCreatedEvent event) {
        rabbitTemplate.convertAndSend("peminjaman.exchange", "peminjaman.created", event);
    }
    
    public void publishPeminjamanReturned(PeminjamanCreatedEvent event) {
        rabbitTemplate.convertAndSend("peminjaman.exchange", "peminjaman.returned", event);
    }
}