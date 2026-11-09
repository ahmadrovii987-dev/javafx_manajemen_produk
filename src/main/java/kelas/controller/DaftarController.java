package kelas.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.produk;

public class DaftarController {
    @FXML private TableView<produk> tabel;
    @FXML private TableColumn<produk, String> kodeCol;
    @FXML private TableColumn<produk, ImageView> gambarCol; 
    @FXML private TableColumn<produk, String> namaCol;
    @FXML private TableColumn<produk, String> kategoriCol;
    @FXML private TableColumn<produk, String> hargaCol;
    @FXML private TextField cariField;
    @FXML private Label pesanLabel;

    private Navigator navigator;
    private DataProduk data;
    private final ObservableList<produk> dataTampilan = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        kodeCol.setCellValueFactory(new PropertyValueFactory<>("kode"));
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        kategoriCol.setCellValueFactory(new PropertyValueFactory<>("kategori"));
    
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        hargaCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(rupiah.format(cellData.getValue().getHarga()))
        );

        gambarCol.setCellValueFactory(cellData -> {
            String uri = cellData.getValue().getGambarUri();
            ImageView imgView = new ImageView();
            imgView.setFitHeight(40);
            imgView.setFitWidth(60);
            imgView.setPreserveRatio(true);
            
            if (uri != null) {
                Image img = new Image(uri, 60, 40, true, true);
                if (!img.isError()) imgView.setImage(img);
            }
            return new SimpleObjectProperty<>(imgView);
        });
        
        tabel.setItems(dataTampilan);
        tabel.setPlaceholder(new Label("Belum ada data produk tersimpan."));
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        cari();
    }

    @FXML
    private void cari() {
        if (data == null) return;
        dataTampilan.clear();
        String kata = cariField.getText().trim().toLowerCase(Locale.ROOT);
        
        for (produk p : data.getDaftar()) {
            if (p.getNama().toLowerCase(Locale.ROOT).contains(kata)) {
                dataTampilan.add(p);
            }
        }
    }

    @FXML
    private void tambah() {
        try { navigator.bukaForm(); } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void detail() {
        produk p = tabel.getSelectionModel().getSelectedItem();
        if (p == null) {
            pesanLabel.setText("Silakan klik salah satu baris produk pada tabel terlebih dahulu.");
            return;
        }
        try { navigator.bukaDetail(p); } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void hapus() {
        produk p = tabel.getSelectionModel().getSelectedItem();
        if (p == null) {
            pesanLabel.setText("Silakan klik produk yang ingin dihapus pada tabel.");
            return;
        }
        
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Apakah kamu yakin ingin menghapus " + p.getNama() + "?",
                ButtonType.YES, ButtonType.NO);
        dialog.setTitle("Konfirmasi Hapus");
        dialog.setHeaderText(null);
        dialog.initOwner(tabel.getScene().getWindow());
        
        ButtonType jawaban = dialog.showAndWait().orElse(ButtonType.NO);
        if (jawaban == ButtonType.YES) {
            data.hapus(p);
            cari();
            pesanLabel.setStyle("-fx-text-fill: #10b981;"); 
            pesanLabel.setText("Produk " + p.getNama() + " berhasil dihapus.");
        }
    }
}