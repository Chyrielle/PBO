package model;

public class Tiket {

    private int id;
    private String namaTiket;
    private double harga;
    private int stokTiket;

    public Tiket() {
    }

    public Tiket(int id, String namaTiket, double harga, int stokTiket) {
        this.id = id;
        this.namaTiket = namaTiket;
        this.harga = harga;
        this.stokTiket = stokTiket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaTiket() {
        return namaTiket;
    }

    public void setNamaTiket(String namaTiket) {
        this.namaTiket = namaTiket;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStokTiket() {
        return stokTiket;
    }

    public void setStokTiket(int stokTiket) {
        this.stokTiket = stokTiket;
    }

    @Override
    public String toString() {
        // dipakai supaya JComboBox di halaman Transaksi nampilin nama tiket, bukan alamat objek
        return namaTiket + " (Stok: " + stokTiket + ")";
    }
}
