package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.db.Conexion;
import com.proyectofinalpoo.model.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAOImpl implements ProveedorDAO {

    @Override
    public void insertar(Proveedor p) throws SQLException {

        String sql = """
                INSERT INTO proveedor (empresa, telefono, correo, direccion, pais)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setParametros(ps, p);
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Proveedor p) throws SQLException {

        String sql = """
                UPDATE proveedor SET
                    empresa = ?, telefono = ?, correo = ?, direccion = ?, pais = ?
                WHERE id_proveedor = ?
                """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setParametros(ps, p);
            ps.setInt(6, p.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Proveedor buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM proveedor WHERE id_proveedor = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProveedor(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Proveedor> listar() throws SQLException {

        List<Proveedor> proveedores = new ArrayList<>();

        String sql = "SELECT * FROM proveedor ORDER BY id_proveedor";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                proveedores.add(mapearProveedor(rs));
            }
        }

        return proveedores;
    }

    @Override
    public boolean existeProveedor(String empresa, int idExcluir) throws SQLException {

        String sql = "SELECT 1 FROM proveedor WHERE LOWER(empresa) = LOWER(?) AND id_proveedor <> ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empresa);
            ps.setInt(2, idExcluir);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        return new Proveedor(
                rs.getInt("id_proveedor"),
                rs.getString("empresa"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("direccion"),
                rs.getString("pais")
        );
    }

    private void setParametros(PreparedStatement ps, Proveedor p) throws SQLException {
        ps.setString(1, p.getEmpresa());
        ps.setString(2, p.getTelefono());
        ps.setString(3, p.getCorreo());
        ps.setString(4, p.getDireccion());
        ps.setString(5, p.getPais());
    }
}