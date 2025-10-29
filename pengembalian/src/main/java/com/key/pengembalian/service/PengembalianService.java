package com.key.pengembalian.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Pengembalian createPengembalian(Pengembalian pengembalian) throws ParseException {
        Peminjaman peminjaman = this.getPeminjaman(pengembalian.getPeminjamanId());

        if (peminjaman == null) {
            throw new RuntimeException("Data peminjaman tidak ditemukan untuk id: " + pengembalian.getPeminjamanId());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date tanggalKembali = sdf.parse(peminjaman.getTanggalKembali());
        Date tanggalDikembalikan = sdf.parse(pengembalian.getTanggalDikembalikan());

        long kembali = tanggalDikembalikan.getTime() - tanggalKembali.getTime();
        long jumlahHari = kembali < 0 ? 0 : Math.abs(kembali);
        long terlambat = TimeUnit.DAYS.convert(jumlahHari, TimeUnit.MILLISECONDS);
        double denda = terlambat * 1000;

        pengembalian.setTerlambat(terlambat + " Hari");
        pengembalian.setDenda(denda);

        rabbitTemplate.convertAndSend(exchange, routingKey, pengembalian);
        log.info("Message sent to exchange [{}] with routing key [{}], Payload: {}", exchange, routingKey, pengembalian);

        return pengembalianRepository.save(pengembalian);
    }


    public void deletePengembalian(Long id) {
        pengembalianRepository.deleteById(id);
    }

    public List<ResponseTemplate> getPengembalianWithPeminjamanById(Long id) {
        List<ResponseTemplate> responseTemplates = new ArrayList<>();
        Pengembalian pengembalian = getPengembalianById(id);

        if (pengembalian == null) {
            return null;
        }

        ServiceInstance serviceInstance = discoveryClient.getInstances("API-GATEWAY-PUSTAKA").get(0);

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
        try {
            ServiceInstance serviceInstance = discoveryClient.getInstances("API-GATEWAY-PUSTAKA").get(0);
            Peminjaman peminjaman = restTemplate.getForObject(serviceInstance.getUri() + "/api/peminjaman/" + id, Peminjaman.class);
            return peminjaman;        
        } catch (Exception e) {
            return null;
        }
    }
}
