package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.dao.UsuarioDAO;
import com.proyectofinalpoo.model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Optional;

public class UsuariosController {
    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtUsuarioSeleccionado;

    @FXML
    private TableView<Persona> tbUsuarios;

    @FXML
    private TableColumn<Persona, Integer> colId;

    @FXML
    private TableColumn<Persona, String> colNombre;

    @FXML
    private TableColumn<Persona, String> colApellido;

    @FXML
    private TableColumn<Persona, String> colUsuario;

    @FXML
    private TableColumn<Persona, String> colCorreo;

    @FXML
    private TableColumn<Persona, String> colRol;

    @FXML
    private TableColumn<Persona, String> colEstado;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final ObservableList<Persona> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarColumnas();
        configurarSeleccionTabla();
        cargarUsuarios();

        txtBuscar.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            buscarUsuarios(valorNuevo);
        });
    }

    private void configurarColumnas() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    private void configurarSeleccionTabla() {
        tbUsuarios.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, seleccionado) -> {
            if (seleccionado != null) {
                txtUsuarioSeleccionado.setText(seleccionado.getUsuario());
            }
        }));
    }

    private void cargarUsuarios() {
        try {
            listaUsuarios.setAll(usuarioDAO.listarUsuarios());
            tbUsuarios.setItems(listaUsuarios);
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudieron cargar los usuarios" + e.getMessage());
        }
    }

    private void buscarUsuarios(String texto) {
        try {
            if (texto == null || texto.trim().isEmpty()) {
                listaUsuarios.setAll(usuarioDAO.listarUsuarios());
            } else {
                listaUsuarios.setAll(usuarioDAO.buscarUsuarios(texto.trim()));
            }
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo buscar usuarios: " + e.getMessage());
        }
    }

    @FXML
    private void cambiarUsuarioAdmin() {
        Persona seleccionado = obtenerUsuarioSeleccionado();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe seleccionar un usuario de la tabla");
            return;
        }

        if("Administrador".equalsIgnoreCase(seleccionado.getRol())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "El usuario seleccionado ya es administrador.");
            return;
        }

        try {
            boolean actualizado = usuarioDAO.cambiarUsuarioAdmin(seleccionado.getId());

            if (actualizado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "El usuario ahora es administrador.");
                refrescarUsuarios();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo actualizar el rol del usuario.");
            }
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cambiar rol: " + e.getMessage());
        }
    }

    @FXML
    private void cambiarUsuarioCliente() {
        Persona seleccionado = obtenerUsuarioSeleccionado();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe seleccionar un usuario de la tabla");
            return;
        }

        if("Cliente".equalsIgnoreCase(seleccionado.getRol())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "El usuario seleccionado ya es cliente.");
            return;
        }

        try {
            boolean actualizado = usuarioDAO.cambiarUsuarioCliente(seleccionado.getId());

            if (actualizado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "El usuario ahora es cliente.");
                refrescarUsuarios();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo actualizar el rol del usuario.");
            }
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cambiar rol: " + e.getMessage());
        }
    }

    @FXML
    private void cambiarUsuarioVendedor() {
        Persona seleccionado = obtenerUsuarioSeleccionado();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe seleccionar un usuario de la tabla");
            return;
        }

        if("Vendedor".equalsIgnoreCase(seleccionado.getRol())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "El usuario seleccionado ya es vendedor.");
            return;
        }

        try {
            boolean actualizado = usuarioDAO.cambiarUsuarioVendedor(seleccionado.getId());

            if (actualizado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "El usuario ahora es vendedor.");
                refrescarUsuarios();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo actualizar el rol del usuario.");
            }
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cambiar rol: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarUsuario() {
        Persona seleccionado = obtenerUsuarioSeleccionado();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe seleccionar un usuario de la tabla.");
            return;
        }

        Optional<ButtonType> respuesta = mostrarConfirmacion(
                "Eliminar usuario",
                "Seguro que desea desactivar al usuario" + seleccionado.getUsuario() + "?"
        );

        if (respuesta.isEmpty() || respuesta.get() != ButtonType.OK) {
            return;
        }

        try {
            boolean eliminado = usuarioDAO.eliminarUsuario(seleccionado.getId());

            if (eliminado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Usuario desactivado correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo desactivar el usuario.");
            }
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar usuario: " + e.getMessage());
        }
    }

    @FXML
    private void reactivarUsuario() {
        Persona seleccionado = obtenerUsuarioSeleccionado();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe seleccionar un usuario de la tabla.");
            return;
        }

        Optional<ButtonType> respuesta = mostrarConfirmacion(
                "Reactivar usuario",
                "Seguro que desea reactivar al usuario" + seleccionado.getUsuario() + "?"
        );

        if (respuesta.isEmpty() || respuesta.get() != ButtonType.OK) {
            return;
        }

        try {
            boolean reactivado = usuarioDAO.reactivarUsuario(seleccionado.getId());

            if (reactivado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Usuario reactivado correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo reactivar el usuario.");
            }
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al reactivar usuario: " + e.getMessage());
        }
    }

    @FXML
    private void refrescarUsuarios() {
        txtBuscar.clear();
        txtUsuarioSeleccionado.clear();
        cargarUsuarios();
    }

    private Persona obtenerUsuarioSeleccionado() {
        return tbUsuarios.getSelectionModel().getSelectedItem();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {

        Alert alert = new Alert(tipo);
        alert.setTitle("iVentControl");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    private Optional<ButtonType> mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("iVentControl");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        return alert.showAndWait();
    }
}
