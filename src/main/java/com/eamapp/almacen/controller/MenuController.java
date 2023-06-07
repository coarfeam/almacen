package com.eamapp.almacen.controller;

import com.eamapp.almacen.AlmacenApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private MenuItem menuClientes;

    @FXML
    private MenuItem menuProductos;

    @FXML
    private MenuItem menuSalir;

    @FXML
    private Menu menuVentas;

    @FXML
    void menuClientesClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AlmacenApplication.class.getResource("cliente-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage tea = new Stage();
        tea.setTitle("Menu");
        tea.setScene(scene);
        tea.show();

    }

    @FXML
    void menuSalirClick(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void menuVentasClick(ActionEvent event) {

    }

    @FXML
    void productosMenuClick(ActionEvent event) {

    }
}
