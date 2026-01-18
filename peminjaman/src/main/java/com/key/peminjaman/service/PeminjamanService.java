package com.key.peminjaman.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.key.peminjaman.model.Peminjaman;
import com.key.peminjaman.repository.PeminjamanRepository;
import com.key.peminjaman.vo.ResponseTemplate;
import com.key.peminjaman.vo.Anggota;
import com.key.peminjaman.vo.Buku;

@Service
public class PeminjamanService {
    
    @Autowired
    private PeminjamanRepository peminjamanRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    public List<Peminjaman> getAllPeminjaman() {
        return peminjamanRepository.findAll();
    }
    
    public Peminjaman getPeminjamanById(Long id) {
        Optional<Peminjaman> peminjaman = peminjamanRepository.findById(id);
        return peminjaman.orElse(null);
    }

    public List<ResponseTemplate> getPeminjamanWithAnggotaId(Long id) {
        List<ResponseTemplate> responseTemplates = new ArrayList<>();
        Peminjaman peminjaman = getPeminjamanById(id);
        
        if (peminjaman == null){
            return null;
        }

        ServiceInstance serviceInstance = discoveryClient.getInstances("api-gateway-pustaka").get(0);

        Anggota anggota = restTemplate.getForObject(serviceInstance.getUri() + "/api/anggota/" + peminjaman.getAnggotaId(), Anggota.class);

        Buku buku = restTemplate.getForObject(serviceInstance.getUri() + "/api/buku/" + peminjaman.getBukuId(), Buku.class);

        ResponseTemplate responseTemplate = new ResponseTemplate();
        responseTemplate.setPeminjaman(peminjaman);
        responseTemplate.setAnggota(anggota);
        responseTemplate.setBuku(buku);

        responseTemplates.add(responseTemplate);

        return responseTemplates;
    }
    
    public Peminjaman createPeminjaman(Peminjaman peminjaman) {
        return peminjamanRepository.save(peminjaman);
    }
    
    public Peminjaman updatePeminjaman(Long id, Peminjaman peminjamanDetails) {
        Optional<Peminjaman> peminjamanOptional = peminjamanRepository.findById(id);
        if (peminjamanOptional.isPresent()) {
            Peminjaman peminjaman = peminjamanOptional.get();
            peminjaman.setAnggotaId(peminjamanDetails.getAnggotaId());
            peminjaman.setBukuId(peminjamanDetails.getBukuId());
            peminjaman.setTanggalPinjam(peminjamanDetails.getTanggalPinjam());
            peminjaman.setTanggalKembali(peminjamanDetails.getTanggalKembali());
            peminjaman.setStatus(peminjamanDetails.getStatus());
            return peminjamanRepository.save(peminjaman);
        }
        return null;
    }
    
    public void deletePeminjaman(Long id) {
        peminjamanRepository.deleteById(id);
    }
}