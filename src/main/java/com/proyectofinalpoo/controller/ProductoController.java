package com.proyectofinalpoo.controller;

import com.proyectofinalpoo.dao.ProductoDAO;
import com.proyectofinalpoo.dao.ProductoDAOImpl;
import com.proyectofinalpoo.model.Producto;
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

public class ProductoController implements Initializable {

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, Integer> colId;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, String> colTipo;
    @FXML
    private TableColumn<Producto, String> colColor;
    @FXML
    private TableColumn<Producto, String> colAlmacenamiento;
    @FXML
    private TableColumn<Producto, Double> colPrecioCompra;
    @FXML
    private TableColumn<Producto, Double> colPrecioVenta;
    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private TableColumn<Producto, String> colEstado;
    @FXML
    private TableColumn<Producto, Integer> colProveedor;

    @FXML
    private TextField txtNombreProducto;
    @FXML
    private TextField txtTipoProducto;
    @FXML
    private TextField txtColorProducto;
    @FXML
    private TextField txtAlmacenamientoProducto;
    @FXML
    private TextField txtPrecioCompraProducto;
    @FXML
    private TextField txtPrecioVentaProducto;
    @FXML
    private TextField txtStockProducto;
    @FXML
    private ComboBox<String> cbxEstadoProducto;
    @FXML
    private TextField txtIdProveedorProducto;

    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    private int idProductoSeleccionado = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_producto"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colAlmacenamiento.setCellValueFactory(new PropertyValueFactory<>("almacenamiento"));
        colPrecioCompra.setCellValueFactory(new PropertyValueFactory<>("precio_compra"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("id_proveedor"));

        cbxEstadoProducto.setItems(FXCollections.observableArrayList(
                "Disponible",
                "Agotado",
                "Descontinuado"
        ));

        tablaProductos.getSelectionModel().selectedItemProperty().addListener(
                (obs, seleccionAnterior, seleccionNueva) -> {
                    if (seleccionNueva != null) {
                        cargarProductoEnFormulario(seleccionNueva);
                    }
                }
        );

        cargarTabla();
    }

    private void cargarTabla() {
        try {
            ObservableList<Producto> productos = FXCollections.observableArrayList(productoDAO.listar());
            tablaProductos.setItems(productos);
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo cargar la lista de productos.");
        }
    }

    private void cargarProductoEnFormulario(Producto p) {
        idProductoSeleccionado = p.getId();
        txtNombreProducto.setText(p.getNombre_producto());
        txtTipoProducto.setText(p.getTipo());
        txtColorProducto.setText(p.getColor());
        txtAlmacenamientoProducto.setText(p.getAlmacenamiento());
        txtPrecioCompraProducto.setText(String.valueOf(p.getPrecio_compra()));
        txtPrecioVentaProducto.setText(String.valueOf(p.getPrecio_venta()));
        txtStockProducto.setText(String.valueOf(p.getStock()));
        cbxEstadoProducto.setValue(p.getEstado());
        txtIdProveedorProducto.setText(String.valueOf(p.getId_proveedor()));
    }

    @FXML
    private void guardarProducto(ActionEvent event) {

        Producto p = leerFormulario();
        if (p == null) {
            return;
        }

        try {
            if (productoDAO.existeProducto(p.getNombre_producto(), 0)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Ya existe un producto con ese nombre.");
                return;
            }

            productoDAO.insertar(p);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto guardado correctamente.");
            limpiarFormulario(event);
            cargarTabla();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar el producto.");
        }
    }

    @FXML
    private void actualizarProducto(ActionEvent event) {

        if (idProductoSeleccionado == 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un producto de la tabla para actualizar.");
            return;
        }

        Producto p = leerFormulario();
        if (p == null) {
            return;
        }
        p.setId(idProductoSeleccionado);

        try {
            if (productoDAO.existeProducto(p.getNombre_producto(), idProductoSeleccionado)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Ya existe otro producto con ese nombre.");
                return;
            }

            productoDAO.actualizar(p);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto actualizado correctamente.");
            limpiarFormulario(event);
            cargarTabla();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al actualizar el producto.");
        }
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {

        if (idProductoSeleccionado == 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un producto de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar el producto seleccionado? Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                productoDAO.eliminar(idProductoSeleccionado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Producto eliminado correctamente.");
                limpiarFormulario(event);
                cargarTabla();
            } catch (SQLException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar el producto.");
            }
        }
    }

    @FXML
    private void limpiarFormulario(ActionEvent event) {
        idProductoSeleccionado = 0;
        txtNombreProducto.clear();
        txtTipoProducto.clear();
        txtColorProducto.clear();
        txtAlmacenamientoProducto.clear();
        txtPrecioCompraProducto.clear();
        txtPrecioVentaProducto.clear();
        txtStockProducto.clear();
        cbxEstadoProducto.setValue(null);
        txtIdProveedorProducto.clear();
        tablaProductos.getSelectionModel().clearSelection();
    }

    private Producto leerFormulario() {

        String nombre = txtNombreProducto.getText().trim();
        String tipo = txtTipoProducto.getText().trim();
        String color = txtColorProducto.getText().trim();
        String almacenamiento = txtAlmacenamientoProducto.getText().trim();
        String precioCompraTexto = txtPrecioCompraProducto.getText().trim();
        String precioVentaTexto = txtPrecioVentaProducto.getText().trim();
        String stockTexto = txtStockProducto.getText().trim();
        String estado = cbxEstadoProducto.getValue();
        String idProveedorTexto = txtIdProveedorProducto.getText().trim();

        if (nombre.isEmpty() || tipo.isEmpty() || precioCompraTexto.isEmpty()
                || precioVentaTexto.isEmpty() || stockTexto.isEmpty()
                || estado == null || idProveedorTexto.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe completar todos los campos obligatorios (nombre, tipo, precios, stock, estado, proveedor).");
            return null;
        }

        double precioCompra;
        double precioVenta;
        int stock;
        int idProveedor;

        try {
            precioCompra = Double.parseDouble(precioCompraTexto);
            precioVenta = Double.parseDouble(precioVentaTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Los precios deben ser valores numéricos (ej. 999.99).");
            return null;
        }

        if (precioCompra < 0 || precioVenta < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Los precios no pueden ser negativos.");
            return null;
        }

        try {
            stock = Integer.parseInt(stockTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "El stock debe ser un número entero (ej. 10).");
            return null;
        }

        if (stock < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "El stock no puede ser negativo.");
            return null;
        }

        try {
            idProveedor = Integer.parseInt(idProveedorTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "El id de proveedor debe ser un número entero.");
            return null;
        }

        if (idProveedor <= 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Ingrese un id de proveedor válido.");
            return null;
        }

        Producto p = new Producto();
        p.setNombre_producto(nombre);
        p.setTipo(tipo);
        p.setColor(color);
        p.setAlmacenamiento(almacenamiento);
        p.setPrecio_compra(precioCompra);
        p.setPrecio_venta(precioVenta);
        p.setStock(stock);
        p.setEstado(estado);
        p.setId_proveedor(idProveedor);

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