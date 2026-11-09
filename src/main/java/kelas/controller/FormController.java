package kelas.controller;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.produk;

public class FormController {
    @FXML private TextField namaField;
    @FXML private ComboBox<String> kategoriBox;
    @FXML private TextField hargaField;
    @FXML private Label pesanLabel;
    @FXML private ImageView gambarView;
    
    private String gambarUri;
    private Navigator navigator;
    private DataProduk data;

    @FXML
    private void initialize() {
        kategoriBox.getItems().addAll("Makanan", "Minuman", "Pakaian", "Elektronik", "Alat Tulis", "Lainnya");
        kategoriBox.setValue("Makanan");
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
    }

    @FXML
    private void simpan() {
        try {
            long harga = Long.parseLong(hargaField.getText().trim());
            produk p = data.tambah(namaField.getText(), kategoriBox.getValue(), harga, gambarUri);
            navigator.bukaDetail(p);
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus berupa bilangan bulat tanpa titik/koma.");
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
        } catch (IOException e) {
            pesanLabel.setText("Gagal membuka halaman detail.");
            e.printStackTrace();
        }
    }

    @FXML
    private void pilihGambar() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Pilih Gambar Produk");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Gambar", "*.png", "*.jpg", "*.jpeg"));
        
        File file = chooser.showOpenDialog(namaField.getScene().getWindow());
        if (file == null) return;
        
        gambarUri = file.toURI().toString();
        Image image = new Image(gambarUri, 300, 200, true, true);
        gambarView.setImage(image);
        pesanLabel.setStyle("-fx-text-fill: #10b981;");
        pesanLabel.setText("Gambar berhasil dilampirkan.");
    }

    @FXML
    private void hapusGambar() {
        gambarUri = null;
        gambarView.setImage(null);
        pesanLabel.setStyle("-fx-text-fill: #ef4444;");
        pesanLabel.setText("Lampiran gambar dihapus.");
    }

    @FXML
    private void bukaDaftar() {
        try { navigator.bukaDaftar(); } catch (IOException e) { e.printStackTrace(); }
    }
}