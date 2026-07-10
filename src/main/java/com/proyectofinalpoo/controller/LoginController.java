package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> cbxRol;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegistrarCliente;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbxRol.setItems(FXCollections.observableArrayList(
                "Administrador",
                "Vendedor",
                "Cliente"
        ));

    }

    @FXML
    private void ingresar(ActionEvent event) {

        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();
        String rol = cbxRol.getValue();

        // VALIDACIONES

        if (usuario.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe ingresar el usuario.");
            txtUsuario.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe ingresar la contraseña.");
            txtPassword.requestFocus();
            return;
        }

        if (rol == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un rol.");
            return;
        }

        try {

            String nombre = usuarioDAO.validarLogin(usuario, password, rol);

            if (nombre != null) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Bienvenido" + nombre);
                abrirDashboard();
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Usuario, contraseña o rol incorrectos");
            }

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al conectar con la base de datos.");
            e.printStackTrace();
        }

    }

        }

    private void abrirDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/proyectofinalpoo/view/dashboard.fxml")
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            stage.setTitle("iVentControl - Dashboard");
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo abrir el dashboard.");
        }
    }


    @FXML
    private void registrarCliente(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/proyectofinalpoo/view/registro_cliente.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) btnRegistrarCliente.getScene().getWindow();
            stage.setTitle("Registro de cliente");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("No se pudo abrir la pantalla de registro.");
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