package kelas.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelas.produk;

public class DataProduk {
    private final ObservableList<produk> daftar = FXCollections.observableArrayList();
    private int urutan = 1;

    public ObservableList<produk> getDaftar() {
        return FXCollections.unmodifiableObservableList(daftar);
    }

    public produk tambah(String nama, String kategori, long harga, String gambarUri) {
        produk p = new produk(String.format("PRD%03d", urutan), nama, kategori, harga, gambarUri);
        daftar.add(p);
        urutan++;
        return p;
    }

    public void hapus(produk p) {
        daftar.remove(p);
    }
}