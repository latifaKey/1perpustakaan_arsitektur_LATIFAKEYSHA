package com.key.rabbitpengembalian.vo;

public class Peminjaman {
  private Long id;
  private Long bukuId;
  private Long anggotaId;
  private String tanggalPinjam;
  private String tanggalKembali;

  public Peminjaman(){
    
  }

  public Peminjaman(Long id, Long bukuId, Long anggotaId, String tanggalPinjam, String tanggalKembali) {
    this.id = id;
    this.bukuId = bukuId;
    this.anggotaId = anggotaId;
    this.tanggalPinjam = tanggalPinjam;
    this.tanggalKembali = tanggalKembali;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getBukuId() {
    return bukuId;
  }
  public void setBukuId(Long bukuId) {
    this.bukuId = bukuId;
  }
  public Long getAnggotaId() {
    return anggotaId;
  }
  public void setAnggotaId(Long anggotaId) {
    this.anggotaId = anggotaId;
  }
  public String getTanggalPinjam() {
    return tanggalPinjam;
  }
  public void setTanggalPinjam(String tanggalPinjam) {
    this.tanggalPinjam = tanggalPinjam;
  }
  public String getTanggalKembali() {
    return tanggalKembali;
  }
  public void setTanggalKembali(String tanggalKembali) {
    this.tanggalKembali = tanggalKembali;
  }

}
