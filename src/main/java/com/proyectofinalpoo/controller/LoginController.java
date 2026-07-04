package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.db.Conexion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            mostrarAlerta("Debe ingresar el usuario.");
            txtUsuario.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mostrarAlerta("Debe ingresar la contraseña.");
            txtPassword.requestFocus();
            return;
        }

        if (rol == null) {
            mostrarAlerta("Seleccione un rol.");
            return;
        }

        String sql = """
                SELECT *
                FROM usuario
                WHERE usuario = ?
                AND contrasena = ?
                AND rol = ?
                AND estado = 'Activo'
                """;

        try {

            Connection con = Conexion.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, password);
            ps.setString(3, rol);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setHeaderText(null);
                alert.setContentText("Bienvenido " + rs.getString("nombre"));
                alert.showAndWait();

            } else {

                mostrarAlerta("Usuario, contraseña o rol incorrectos.");

            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {

            mostrarAlerta("Error al conectar con la base de datos.");

        }

    }
    

    private void mostrarAlerta(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("iVentControl");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

}