package com.key.queryservice.messaging;

import com.key.queryservice.model.AnggotaView;
import com.key.queryservice.model.BukuView;
import com.key.queryservice.model.PeminjamanView;
import com.key.queryservice.repository.AnggotaViewRepository;
import com.key.queryservice.repository.BukuViewRepository;
import com.key.queryservice.repository.PeminjamanViewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class QueryServiceMessageListener {
    
    @Autowired
    private AnggotaViewRepository anggotaViewRepository;
    
    @Autowired
    private BukuViewRepository bukuViewRepository;
    
    @Autowired
    private PeminjamanViewRepository peminjamanViewRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @RabbitListener(queues = "anggota.created.queue")
    public void handleAnggotaCreated(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            AnggotaView anggotaView = new AnggotaView();
            anggotaView.setId(Long.valueOf(data.get("id").toString()));
            anggotaView.setNama(data.get("nama").toString());
            anggotaView.setEmail(data.get("email").toString());
            anggotaView.setNoTelepon(data.get("noTelepon").toString());
            anggotaView.setAlamat(data.get("alamat").toString());
            anggotaView.setStatus("AKTIF");
            anggotaViewRepository.save(anggotaView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(queues = "anggota.updated.queue")
    public void handleAnggotaUpdated(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            Long id = Long.valueOf(data.get("id").toString());
            anggotaViewRepository.findById(id).ifPresent(anggotaView -> {
                anggotaView.setNama(data.get("nama").toString());
                anggotaView.setEmail(data.get("email").toString());
                anggotaView.setNoTelepon(data.get("noTelepon").toString());
                anggotaView.setAlamat(data.get("alamat").toString());
                anggotaViewRepository.save(anggotaView);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(queues = "anggota.deleted.queue")
    public void handleAnggotaDeleted(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            Long id = Long.valueOf(data.get("id").toString());
            anggotaViewRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(queues = "buku.created.queue")
    public void handleBukuCreated(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            BukuView bukuView = new BukuView();
            bukuView.setId(Long.valueOf(data.get("id").toString()));
            bukuView.setJudul(data.get("judul").toString());
            bukuView.setPengarang(data.get("pengarang").toString());
            bukuView.setPenerbit(data.get("penerbit").toString());
            bukuView.setTahunTerbit(Integer.valueOf(data.get("tahunTerbit").toString()));
            bukuView.setIsbn(data.get("isbn").toString());
            bukuView.setStok(Integer.valueOf(data.get("stok").toString()));
            bukuViewRepository.save(bukuView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(queues = "buku.updated.queue")
    public void handleBukuUpdated(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            Long id = Long.valueOf(data.get("id").toString());
            bukuViewRepository.findById(id).ifPresent(bukuView -> {
                bukuView.setJudul(data.get("judul").toString());
                bukuView.setPengarang(data.get("pengarang").toString());
                bukuView.setPenerbit(data.get("penerbit").toString());
                bukuView.setTahunTerbit(Integer.valueOf(data.get("tahunTerbit").toString()));
                bukuView.setIsbn(data.get("isbn").toString());
                bukuView.setStok(Integer.valueOf(data.get("stok").toString()));
                bukuViewRepository.save(bukuView);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(queues = "peminjaman.created.queue")
    public void handlePeminjamanCreated(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            PeminjamanView peminjamanView = new PeminjamanView();
            peminjamanView.setId(Long.valueOf(data.get("id").toString()));
            peminjamanView.setAnggotaId(Long.valueOf(data.get("anggotaId").toString()));
            peminjamanView.setBukuId(Long.valueOf(data.get("bukuId").toString()));
            peminjamanView.setNamaAnggota(data.get("namaAnggota").toString());
            peminjamanView.setJudulBuku(data.get("judulBuku").toString());
            peminjamanView.setTanggalPinjam(java.time.LocalDate.parse(data.get("tanggalPinjam").toString()));
            peminjamanView.setTanggalKembali(java.time.LocalDate.parse(data.get("tanggalKembali").toString()));
            peminjamanView.setStatus("AKTIF");
            peminjamanViewRepository.save(peminjamanView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(queues = "peminjaman.returned.queue")
    public void handlePeminjamanReturned(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            Long id = Long.valueOf(data.get("id").toString());
            peminjamanViewRepository.findById(id).ifPresent(peminjamanView -> {
                peminjamanView.setStatus("DIKEMBALIKAN");
                peminjamanViewRepository.save(peminjamanView);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}