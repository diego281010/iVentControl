package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.app.Main;
import com.proyectofinalpoo.model.Persona;
import com.proyectofinalpoo.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnProveedores;
    @FXML
    private Button btnUsuarios;
    @FXML
    private BorderPane bpPrincipal;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Label lblRolUsuario;

    private Node vistaInicial;

    @FXML
    private void initialize() {
        vistaInicial = bpPrincipal.getCenter();
        aplicarPermisosPorRol();
    }

    private void aplicarPermisosPorRol() {

        Persona usuario = Sesion.getInstancia().getUsuarioActual();

        if (usuario == null) {
            return;
        }

        lblNombreUsuario.setText(usuario.getNombre() + " " + usuario.getApellido());
        lblRolUsuario.setText(usuario.descripcionPermisos());

        boolean esAdmin = Sesion.getInstancia().esAdministrador();
        btnUsuarios.setVisible(esAdmin);
        btnUsuarios.setManaged(esAdmin);

        boolean puedeVerProveedores = !Sesion.getInstancia().esCliente();
        btnProveedores.setVisible(puedeVerProveedores);
        btnProveedores.setManaged(puedeVerProveedores);
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

    @FXML
    private void mostrarUsuarios() {
        cargarVista("usuarios.fxml");
    }

    private void cargarVista(String nombreFXML) {
        try {
            Node vista = FXMLLoader.load(
                    Main.class.getResource("/com/proyectofinalpoo/view/" + nombreFXML)
            );
            bpPrincipal.setCenter(vista);
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Esa sección todavía no está lista.");
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            Sesion.getInstancia().cerrarSesion();

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
