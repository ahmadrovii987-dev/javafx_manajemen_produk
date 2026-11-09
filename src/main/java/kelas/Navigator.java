package kelas;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kelas.controller.DaftarController;
import kelas.controller.DetailController;
import kelas.controller.FormController;
import kelas.data.DataProduk;

public class Navigator {
    private final Stage stage;
    private final DataProduk data;
    private Scene scene;

    public Navigator(Stage stage, DataProduk data) {
        this.stage = stage;
        this.data = data;
    }

    private FXMLLoader loader(String nama) {
        return new FXMLLoader(getClass().getResource("/kelas/" + nama + ".fxml"));
    }

    private void tampil(Parent root) {
        if (scene == null) {
            scene = new Scene(root, 950, 750);
            scene.getStylesheets().add(getClass().getResource("/kelas/styles/app.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Manajemen Produk Toko - by Rovii");
            stage.setMinWidth(800);
            stage.setMinHeight(600);
        } else {
            scene.setRoot(root);
        }
        stage.show();
    }

    public void bukaForm() throws IOException {
        FXMLLoader l = loader("form");
        Parent root = l.load();
        FormController c = l.getController();
        c.setKonteks(this, data);
        tampil(root);
    }

    public void bukaDaftar() throws IOException {
        FXMLLoader l = loader("daftar");
        Parent root = l.load();
        DaftarController c = l.getController();
        c.setKonteks(this, data);
        tampil(root);
    }

    public void bukaDetail(produk produk) throws IOException {
        FXMLLoader l = loader("detail");
        Parent root = l.load();
        DetailController c = l.getController();
        c.setData(this, produk);
        tampil(root);
    }
}