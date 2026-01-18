package com.key.pengembalian.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.key.pengembalian.model.Pengembalian;
import com.key.pengembalian.repository.PengembalianRepository;
import com.key.pengembalian.vo.Anggota;
import com.key.pengembalian.vo.Buku;
import com.key.pengembalian.vo.Peminjaman;
import com.key.pengembalian.vo.ResponseTemplate;

@Service
public class PengembalianService {

    private final RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(PengembalianService.class);

    @Value("${app.rabbitmq-pengembalian.exchange}")
    private String exchange;

    @Value("${app.rabbitmq-pengembalian.routing-key}")
    private String routingKey;

    @Autowired
    private PengembalianRepository pengembalianRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public PengembalianService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Pengembalian> getAllPengembalians() {
        return pengembalianRepository.findAll();
    }

    public Pengembalian getPengembalianById(Long id) {
        return pengembalianRepository.findById(id).orElse(null);
    }

    public Pengembalian createPengembalian(Pengembalian pengembalian) {
        Peminjaman peminjaman = this.getPeminjaman(pengembalian.getPeminjamanId());

        if (peminjaman == null) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,
                    "Data peminjaman tidak ditemukan untuk id: " + pengembalian.getPeminjamanId());
        }

        // Parse dates (support dd-MM-yyyy and yyyy-MM-dd)
        LocalDate tanggalKembali;
        LocalDate tanggalDikembalikan;
        try {
            tanggalKembali = parseToLocalDate(peminjaman.getTanggalKembali());
            tanggalDikembalikan = parseToLocalDate(pengembalian.getTanggalDikembalikan());
        } catch (DateTimeParseException ex) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Format tanggal tidak valid, gunakan dd-MM-yyyy atau yyyy-MM-dd");
        }

        long terlambat = ChronoUnit.DAYS.between(tanggalKembali, tanggalDikembalikan);
        if (terlambat < 0) {
            terlambat = 0;
        }
        double denda = terlambat * 1000;

        pengembalian.setTerlambat(terlambat + " Hari");
        pengembalian.setDenda(denda);

        // Send to RabbitMQ (optional - continue if RabbitMQ is not available)
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, pengembalian);
            log.info("Message sent to exchange [{}] with routing key [{}], Payload: {}", exchange, routingKey, pengembalian);
        } catch (Exception e) {
            log.warn("RabbitMQ tidak tersedia, melanjutkan tanpa mengirim pesan: {}", e.getMessage());
        }

        return pengembalianRepository.save(pengembalian);
    }

    private LocalDate parseToLocalDate(String value) {
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter f2 = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd
        try {
            return LocalDate.parse(value, f1);
        } catch (DateTimeParseException ex) {
            return LocalDate.parse(value, f2);
        }
    }


    public void deletePengembalian(Long id) {
        pengembalianRepository.deleteById(id);
    }

    public Pengembalian updatePengembalian(Long id, Pengembalian pengembalianDetails) {
        return pengembalianRepository.findById(id).map(pengembalian -> {
            pengembalian.setPeminjamanId(pengembalianDetails.getPeminjamanId());
            pengembalian.setTanggalDikembalikan(pengembalianDetails.getTanggalDikembalikan());
            pengembalian.setDenda(pengembalianDetails.getDenda());
            pengembalian.setTerlambat(pengembalianDetails.getTerlambat());
            pengembalian.setStatus(pengembalianDetails.getStatus());
            return pengembalianRepository.save(pengembalian);
        }).orElse(null);
    }

    public List<ResponseTemplate> getPengembalianWithPeminjamanById(Long id) {
        List<ResponseTemplate> responseTemplates = new ArrayList<>();
        Pengembalian pengembalian = getPengembalianById(id);

        if (pengembalian == null) {
            return null;
        }

        ServiceInstance serviceInstance = discoveryClient.getInstances("api-gateway-pustaka").get(0);

        Peminjaman peminjaman = restTemplate.getForObject(serviceInstance.getUri() + "/api/peminjaman/" + pengembalian.getPeminjamanId(), Peminjaman.class);
        Anggota anggota = restTemplate.getForObject(serviceInstance.getUri() + "/api/anggota/" + peminjaman.getAnggotaId(), Anggota.class);
        Buku buku = restTemplate.getForObject(serviceInstance.getUri() + "/api/buku/" + peminjaman.getBukuId(), Buku.class);

        ResponseTemplate vo = new ResponseTemplate();
        vo.setPengembalian(pengembalian);
        vo.setPeminjaman(peminjaman);
        vo.setAnggota(anggota);
        vo.setBuku(buku);
        
        responseTemplates.add(vo);
        return responseTemplates;
    }

    public Peminjaman getPeminjaman(Long id) {
        // Try via API Gateway first
        try {
            java.util.List<ServiceInstance> instances = discoveryClient.getInstances("api-gateway-pustaka");
            if (instances != null && !instances.isEmpty()) {
                ServiceInstance gateway = instances.get(0);
                String gatewayUrl = gateway.getUri() + "/api/peminjaman/" + id;
                log.info("Trying to get peminjaman via gateway: {}", gatewayUrl);
                Peminjaman result = restTemplate.getForObject(gatewayUrl, Peminjaman.class);
                if (result != null) {
                    return result;
                }
            }
        } catch (Exception e) {
            log.warn("Request via API Gateway failed for peminjaman id {}: {}", id, e.getMessage());
        }

        // Fallback: call peminjaman service directly
        try {
            String directUrl = "http://localhost:8084/api/peminjaman/" + id;
            log.info("Fallback: calling peminjaman directly at {}", directUrl);
            return restTemplate.getForObject(directUrl, Peminjaman.class);
        } catch (Exception e) {
            log.error("Direct request to peminjaman service failed for id {}: {}", id, e.getMessage());
            return null;
        }
    }
}
