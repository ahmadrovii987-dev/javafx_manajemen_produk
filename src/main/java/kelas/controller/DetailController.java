package kelas.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kelas.Navigator;
import kelas.produk;

public class DetailController {
    @FXML private Label kodeLabel;
    @FXML private Label namaLabel;
    @FXML private Label kategoriLabel;
    @FXML private Label hargaLabel;
    @FXML private Label pesanLabel;
    @FXML private ImageView gambarView;
    
    private Navigator navigator;

    public void setData(Navigator navigator, produk produk) {
        this.navigator = navigator;
        kodeLabel.setText(produk.getKode());
        namaLabel.setText(produk.getNama());
        kategoriLabel.setText(produk.getKategori());
        
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        hargaLabel.setText(rupiah.format(produk.getHarga()));
        
        if (produk.getGambarUri() != null) {
            Image image = new Image(produk.getGambarUri(), 350, 250, true, true);
            gambarView.setImage(image);
        } else {
            gambarView.setImage(null);
        }
        
        pesanLabel.setText("Selamat, produk sudah ditambahkan!");
    }

    @FXML
    private void tambahLagi() {
        try { navigator.bukaForm(); } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void bukaDaftar() {
        try { navigator.bukaDaftar(); } catch (IOException e) { e.printStackTrace(); }
    }
}