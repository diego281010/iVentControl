package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private BorderPane bpPrincipal;
    @FXML
    private Node vistaInicial;
    @FXML
    private void initialize() {
        vistaInicial = bpPrincipal.getCenter();
    }

    @FXML
    private void mostrarInicio() {
        bpPrincipal.setCenter(vistaInicial);
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

            bpPrincipal.setCenter(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarUsuarios() {
        // muestra de usuarios y añadir usuarios administradores, solo admin puede hacerlo
        cargarVista("usuarios.fxml");
    }

    @FXML
    private void cerrarSesion() {
        // luego aquí puedes volver al login
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/proyectofinalpoo/view/login.fxml"));

            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setTitle("iVentControl - Login");
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo cerrar sesión.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {

        Alert alert = new Alert(tipo);
        alert.setTitle("iVentControl");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

}