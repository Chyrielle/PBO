package model;

public class Transaksi {

    private int id;
    private int tiketId;
    private String namaTiket; // hasil JOIN, buat ditampilin di tabel riwayat transaksi
    private int jumlah;
    private double totalHarga;
    private String tanggal;

    public Transaksi() {
    }

    public Transaksi(int id, int tiketId, int jumlah,
                      double totalHarga, String tanggal) {

        this.id = id;
        this.tiketId = tiketId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiketId() {
        return tiketId;
    }

    public void setTiketId(int tiketId) {
        this.tiketId = tiketId;
    }

    public String getNamaTiket() {
        return namaTiket;
    }

    public void setNamaTiket(String namaTiket) {
        this.namaTiket = namaTiket;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
