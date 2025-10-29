package com.key.rabbitmq_peminjaman_service.vo;

public class ResponseTemplate {
  Peminjaman peminjaman;
  Buku buku;
  Anggota anggota;

  
  public ResponseTemplate(){
    
  }

  public ResponseTemplate( Peminjaman peminjaman, Buku buku, Anggota anggota) {
    this.peminjaman = peminjaman;
    this.buku = buku;
    this.anggota = anggota;
  }


  public Peminjaman getPeminjaman() {
    return peminjaman;
  }

  public void setPeminjaman(Peminjaman peminjaman) {
    this.peminjaman = peminjaman;
  }

  public Buku getBuku() {
    return buku;
  }

  public void setBuku(Buku buku) {
    this.buku = buku;
  }

  public Anggota getAnggota() {
    return anggota;
  }

  public void setAnggota(Anggota anggota) {
    this.anggota = anggota;
  }
  
  public String sendMailMessage() {
    StringBuilder message = new StringBuilder();

    message.append("Subject: Konfirmasi Peminjaman Buku Berhasil\n\n");

    message.append("Yth. ").append(anggota.getNama()).append(",\n\n");
    message.append("Terima kasih telah melakukan peminjaman buku di perpustakaan kami.\n");
    message.append("Berikut adalah rincian lengkap peminjaman Anda:\n\n");

    message.append("------------------------------------------\n");
    message.append("DETAIL PEMINJAMAN\n");
    message.append("------------------------------------------\n");
    message.append("ID Peminjaman  : ").append(peminjaman.getId()).append("\n");
    message.append("Nama Anggota   : ").append(anggota.getNama()).append("\n\n");
    message.append("NIM Anggota    : ").append(anggota.getNim()).append("\n\n");
    message.append("Jenis Kelamin  : ").append(anggota.getJenis_kelamin()).append("\n\n");

    message.append("Judul Buku     : ").append(buku.getJudul()).append("\n");
    message.append("Pengarang      : ").append(buku.getPengarang()).append("\n\n");

    message.append("Tanggal Pinjam : ").append(peminjaman.getTanggalPinjam()).append("\n");
    message.append("Tgl. Kembali   : ").append(peminjaman.getTanggalKembali()).append("\n");
    message.append("------------------------------------------\n\n");

    message.append("Mohon untuk mengembalikan buku tepat waktu sebelum tanggal jatuh tempo untuk menghindari denda.\n\n");
    message.append("Hormat kami,\n");
    message.append("Tim Perpustakaan Digital\n");

    return message.toString();
  }
}
