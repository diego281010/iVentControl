package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.dao.UsuarioDAO;
import com.proyectofinalpoo.model.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistroClienteController implements Initializable {

    @FXML
    private TextField txtNombreCliente;

    @FXML
    private TextField txtApellidoCliente;

    @FXML
    private TextField txtUsuarioCliente;

    @FXML
    private TextField txtCorreoCliente;

    @FXML
    private PasswordField txtPasswordCliente;

    @FXML
    private PasswordField txtConfirmarPasswordCliente;

    @FXML
    private Button btnRegistrarCliente;

    @FXML
    private Button btnVolverLogin;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void registrar(ActionEvent event) {

        String nombre = txtNombreCliente.getText().trim();
        String apellido = txtApellidoCliente.getText().trim();
        String usuario = txtUsuarioCliente.getText().trim();
        String correo = txtCorreoCliente.getText().trim();
        String password = txtPasswordCliente.getText().trim();
        String confirmarPassword = txtConfirmarPasswordCliente.getText().trim();

        // VALIDACIONES

        if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty()
                || correo.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe completar todos los campos.");
            return;
        }

        if (!nombre.matches("^[\\p{L}\\s]+$") || !apellido.matches("^[\\p{L}\\s]+$")) {
            mostrarAlerta(Alert.AlertType.WARNING, "El nombre y el apellido no deben contener números ni símbolos.");
            return;
        }

        if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Ingrese un correo válido.");
            return;
        }

        if (!password.equals(confirmarPassword)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Las contraseñas no coinciden.");
            return;
        }

        if (password.length() < 6) {
            mostrarAlerta(Alert.AlertType.WARNING, "La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        try {

            if (usuarioDAO.existeUsuarioOCorreo(usuario, correo)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Ya existe un usuario con ese nombre de usuario o correo.");
                return;
            }

            Persona nuevoCliente = new Persona(0, nombre, apellido, usuario, correo, password, "Cliente", "Activo");

            boolean registrado = usuarioDAO.registrarCliente(nuevoCliente);

            if (registrado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso. Ya puede iniciar sesión como Cliente.");
                volverALogin(event);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo completar el registro. Intente nuevamente.");
            }

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al conectar con la base de datos.");
        }

    }

    @FXML
    private void volver(ActionEvent event) {
        volverALogin(event);
    }

    private void volverALogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/proyectofinalpoo/view/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Inicio de sesión");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo volver a la pantalla de inicio de sesión.");
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