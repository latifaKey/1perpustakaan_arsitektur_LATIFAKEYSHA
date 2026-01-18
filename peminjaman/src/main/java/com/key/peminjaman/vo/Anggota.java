package com.key.peminjaman.vo;

public class Anggota {
  private Long id;
  private String nim;
  private String nama;
  private String alamat;
  private String email;
  private String jenis_kelamin;

  public Anggota(){
    
  }
  public Anggota(Long id, String nim, String nama, String alamat, String email, String jenis_kelamin) {
    this.id = id;
    this.nim = nim;
    this.nama = nama;
    this.alamat = alamat;
    this.email = email;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getNim() {
    return nim;
  }
  public void setNim(String nim) {
    this.nim = nim;
  }
  public String getNama() {
    return nama;
  }
  public void setNama(String nama) {
    this.nama = nama;
  }
  public String getAlamat() {
    return alamat;
  }
  public void setAlamat(String alamat) {
    this.alamat = alamat;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

}
