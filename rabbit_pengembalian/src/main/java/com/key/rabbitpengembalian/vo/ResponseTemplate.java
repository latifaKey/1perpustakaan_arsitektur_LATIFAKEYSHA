package com.key.rabbitpengembalian.vo;

public class ResponseTemplate {
  Pengembalian pengembalian;
  Peminjaman peminjaman;
  Buku buku;
  Anggota anggota;

  public ResponseTemplate(){

  }

  public ResponseTemplate(Pengembalian pengembalian, Peminjaman peminjaman, Buku buku, Anggota anggota){
    this.pengembalian = pengembalian;
    this.peminjaman = peminjaman;
    this.buku = buku;
    this.anggota = anggota;
  }

  public Pengembalian getPengembalian() {
    return pengembalian;
  }

  public void setPengembalian(Pengembalian pengembalian){
    this.pengembalian = pengembalian;
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

    message.append("Subject: Konfirmasi Pengembalian Buku Berhasil\n\n");

    message.append("Yth. ").append(anggota.getNama()).append(",\n\n");
    message.append("Terima kasih telah mengembalikan buku ke perpustakaan kami.\n");
    message.append("Berikut adalah rincian lengkap pengembalian Anda:\n\n");

    message.append("------------------------------------------\n");
    message.append("DETAIL PENGEMBALIAN\n");
    message.append("------------------------------------------\n");
    message.append("ID Pengembalian : ").append(pengembalian.getId()).append("\n");
    message.append("ID Peminjaman   : ").append(pengembalian.getPeminjamanId()).append("\n");
    message.append("Nama Anggota    : ").append(anggota.getNama()).append("\n");
    message.append("NIM Anggota     : ").append(anggota.getNim()).append("\n");
    message.append("Jenis Kelamin   : ").append(anggota.getJenis_kelamin()).append("\n\n");

    message.append("Judul Buku      : ").append(buku.getJudul()).append("\n");
    message.append("Pengarang       : ").append(buku.getPengarang()).append("\n\n");

    message.append("Tanggal Dikembalikan : ").append(pengembalian.getTanggalDikembalikan()).append("\n");
    message.append("Terlambat            : ").append(pengembalian.getTerlambat()).append(" hari\n");
    message.append("Denda                : Rp ").append(pengembalian.getDenda()).append("\n");
    message.append("------------------------------------------\n\n");

    message.append("Terima kasih telah mengembalikan buku dengan baik.\n");
    message.append("Jika ada keterlambatan, mohon untuk segera menyelesaikan pembayaran denda di loket perpustakaan.\n\n");

    message.append("Hormat kami,\n");
    message.append("Tim Perpustakaan Digital\n");

    return message.toString();  
  }
}
