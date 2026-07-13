package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.db.Conexion;
import com.proyectofinalpoo.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void insertar(Producto p) throws SQLException {

        String sql = """
                INSERT INTO producto
                (nombre_producto, tipo, color, almacenamiento, precio_compra, precio_venta, stock, estado, id_proveedor)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setParametros(ps, p);
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Producto p) throws SQLException {

        String sql = """
                UPDATE producto SET
                    nombre_producto = ?, tipo = ?, color = ?, almacenamiento = ?,
                    precio_compra = ?, precio_venta = ?, stock = ?, estado = ?, id_proveedor = ?
                WHERE id_producto = ?
                """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setParametros(ps, p);
            ps.setInt(10, p.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Producto buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM producto WHERE id_producto = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Producto> listar() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM producto ORDER BY id_producto";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        }

        return productos;
    }

    @Override
    public boolean existeProducto(String nombreProducto, int idExcluir) throws SQLException {

        String sql = "SELECT 1 FROM producto WHERE LOWER(nombre_producto) = LOWER(?) AND id_producto <> ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreProducto);
            ps.setInt(2, idExcluir);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id_producto"),
                rs.getString("nombre_producto"),
                rs.getString("tipo"),
                rs.getString("color"),
                rs.getString("almacenamiento"),
                rs.getDouble("precio_compra"),
                rs.getDouble("precio_venta"),
                rs.getInt("stock"),
                rs.getString("estado"),
                rs.getInt("id_proveedor")
        );
    }

    private void setParametros(PreparedStatement ps, Producto p) throws SQLException {
        ps.setString(1, p.getNombre_producto());
        ps.setString(2, p.getTipo());
        ps.setString(3, p.getColor());
        ps.setString(4, p.getAlmacenamiento());
        ps.setDouble(5, p.getPrecio_compra());
        ps.setDouble(6, p.getPrecio_venta());
        ps.setInt(7, p.getStock());
        ps.setString(8, p.getEstado());
        ps.setInt(9, p.getId_proveedor());
    }
}