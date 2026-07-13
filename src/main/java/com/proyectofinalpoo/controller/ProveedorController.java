package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.dao.ProveedorDAO;
import com.proyectofinalpoo.dao.ProveedorDAOImpl;
import com.proyectofinalpoo.model.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProveedorController implements Initializable {

    @FXML
    private TableView<Proveedor> tablaProveedores;

    @FXML
    private TableColumn<Proveedor, Integer> colId;
    @FXML
    private TableColumn<Proveedor, String> colEmpresa;
    @FXML
    private TableColumn<Proveedor, String> colTelefono;
    @FXML
    private TableColumn<Proveedor, String> colCorreo;
    @FXML
    private TableColumn<Proveedor, String> colDireccion;
    @FXML
    private TableColumn<Proveedor, String> colPais;

    @FXML
    private TextField txtEmpresaProveedor;
    @FXML
    private TextField txtTelefonoProveedor;
    @FXML
    private TextField txtCorreoProveedor;
    @FXML
    private TextField txtDireccionProveedor;
    @FXML
    private TextField txtPaisProveedor;

    private final ProveedorDAO proveedorDAO = new ProveedorDAOImpl();

    private int idProveedorSeleccionado = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));

        tablaProveedores.getSelectionModel().selectedItemProperty().addListener(
                (obs, seleccionAnterior, seleccionNueva) -> {
                    if (seleccionNueva != null) {
                        cargarProveedorEnFormulario(seleccionNueva);
                    }
                }
        );

        cargarTabla();
    }

    private void cargarTabla() {
        try {
            ObservableList<Proveedor> proveedores = FXCollections.observableArrayList(proveedorDAO.listar());
            tablaProveedores.setItems(proveedores);
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo cargar la lista de proveedores.");
        }
    }

    private void cargarProveedorEnFormulario(Proveedor p) {
        idProveedorSeleccionado = p.getId();
        txtEmpresaProveedor.setText(p.getEmpresa());
        txtTelefonoProveedor.setText(p.getTelefono());
        txtCorreoProveedor.setText(p.getCorreo());
        txtDireccionProveedor.setText(p.getDireccion());
        txtPaisProveedor.setText(p.getPais());
    }

    @FXML
    private void guardarProveedor(ActionEvent event) {

        Proveedor p = leerFormulario();
        if (p == null) {
            return;
        }

        try {
            if (proveedorDAO.existeProveedor(p.getEmpresa(), 0)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Ya existe un proveedor con esa empresa.");
                return;
            }

            proveedorDAO.insertar(p);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Proveedor guardado correctamente.");
            limpiarFormulario(event);
            cargarTabla();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar el proveedor.");
        }
    }

    @FXML
    private void actualizarProveedor(ActionEvent event) {

        if (idProveedorSeleccionado == 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un proveedor de la tabla para actualizar.");
            return;
        }

        Proveedor p = leerFormulario();
        if (p == null) {
            return;
        }
        p.setId(idProveedorSeleccionado);

        try {
            if (proveedorDAO.existeProveedor(p.getEmpresa(), idProveedorSeleccionado)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Ya existe otro proveedor con esa empresa.");
                return;
            }

            proveedorDAO.actualizar(p);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Proveedor actualizado correctamente.");
            limpiarFormulario(event);
            cargarTabla();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al actualizar el proveedor.");
        }
    }

    @FXML
    private void eliminarProveedor(ActionEvent event) {

        if (idProveedorSeleccionado == 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un proveedor de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar el proveedor seleccionado? Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                proveedorDAO.eliminar(idProveedorSeleccionado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Proveedor eliminado correctamente.");
                limpiarFormulario(event);
                cargarTabla();
            } catch (SQLException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar el proveedor. Verifique que no tenga productos asociados.");
            }
        }
    }

    @FXML
    private void limpiarFormulario(ActionEvent event) {
        idProveedorSeleccionado = 0;
        txtEmpresaProveedor.clear();
        txtTelefonoProveedor.clear();
        txtCorreoProveedor.clear();
        txtDireccionProveedor.clear();
        txtPaisProveedor.clear();
        tablaProveedores.getSelectionModel().clearSelection();
    }

    private Proveedor leerFormulario() {

        String empresa = txtEmpresaProveedor.getText().trim();
        String telefono = txtTelefonoProveedor.getText().trim();
        String correo = txtCorreoProveedor.getText().trim();
        String direccion = txtDireccionProveedor.getText().trim();
        String pais = txtPaisProveedor.getText().trim();

        if (empresa.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "El nombre de la empresa es obligatorio.");
            return null;
        }

        if (!correo.isEmpty() && !correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Ingrese un correo válido, o déjelo vacío.");
            return null;
        }

        Proveedor p = new Proveedor();
        p.setEmpresa(empresa);
        p.setTelefono(telefono);
        p.setCorreo(correo);
        p.setDireccion(direccion);
        p.setPais(pais);

        return p;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle("iVentControl");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}