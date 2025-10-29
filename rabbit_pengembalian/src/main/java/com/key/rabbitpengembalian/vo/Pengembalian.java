package com.key.rabbitpengembalian.vo;

public class Pengembalian {
  private Long id;
  private Long peminjamanId;
  private String tanggalDikembalikan;
  private String terlambat;
  private Double denda;
  
  public Pengembalian() {
    
  }

  public Pengembalian(Long id, Long peminjamanId, String tanggalDikembalikan, String terlambat, Double denda) {
    this.id = id;
    this.peminjamanId = peminjamanId;
    this.tanggalDikembalikan = tanggalDikembalikan;
    this.terlambat = terlambat;
    this.denda = denda;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPeminjamanId() {
    return peminjamanId;
  }

  public void setPeminjamanId(Long peminjamanId) {
    this.peminjamanId = peminjamanId;
  }

  public String getTanggalDikembalikan() {
    return tanggalDikembalikan;
  }

  public void setTanggalDikembalikan(String tanggalDikembalikan) {
    this.tanggalDikembalikan = tanggalDikembalikan;
  }

  public String getTerlambat() {
    return terlambat;
  }

  public void setTerlambat(String terlambat) {
    this.terlambat = terlambat;
  }

  public Double getDenda() {
    return denda;
  }

  public void setDenda(Double denda) {
    this.denda = denda;
  }
}
