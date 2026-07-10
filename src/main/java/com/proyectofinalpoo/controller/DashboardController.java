package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML
    private StackPane contenedorContenido;

    @FXML
    private void mostrarInicio() {
        contenedorContenido.getChildren().setAll(
                new Label("Bienvenido al dashboard")
        );
    }

    @FXML
    private void mostrarProductos() {
        cargarVista("productos.fxml");
    }

    @FXML
    private void mostrarProveedores() {
        cargarVista("proveedores.fxml");
    }

    private void cargarVista(String nombreFXML) {
        try {
            Node vista = FXMLLoader.load(
                    Main.class.getResource("/com/proyectofinalpoo/view/" + nombreFXML)
            );

            contenedorContenido.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion() {
        // luego aquí puedes volver al login
    }

}