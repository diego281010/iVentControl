package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.db.Conexion;
import com.proyectofinalpoo.model.Administrador;
import com.proyectofinalpoo.model.Cliente;
import com.proyectofinalpoo.model.Persona;
import com.proyectofinalpoo.model.Vendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements UsuarioDAOInterface {

    @Override
    public void insertar(Persona p) {
        System.out.println("WIP");
    }

    @Override
    public Persona validarLogin(String usuario, String password, String rol) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND contrasena = ? AND rol = ? AND estado = 'Activo'";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);
            ps.setString(3, rol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearPersonaDesdeResultSet(rs);
                }
            }
        }

        return null;
    }

    @Override
    public boolean existeUsuarioOCorreo(String usuario, String correo) throws SQLException {

        String sql = "SELECT 1 FROM usuario WHERE usuario = ? OR correo = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, correo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public boolean registrarCliente(Persona persona) throws SQLException {

        String sql = """
                INSERT INTO usuario (nombre, apellido, usuario, correo, contrasena, rol, estado)
                VALUES (?, ?, ?, ?, ?, 'Cliente', 'Activo')
                """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setString(3, persona.getUsuario());
            ps.setString(4, persona.getCorreo());
            ps.setString(5, persona.getPassword());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean cambiarUsuarioAdmin(int idUsuario) throws SQLException {
        String sql = "UPDATE usuario SET rol = 'Administrador' WHERE id_usuario = ? AND estado = 'Activo';";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean cambiarUsuarioCliente(int idUsuario) throws SQLException {
        String sql = "UPDATE usuario SET rol = 'Cliente' WHERE id_usuario = ? AND estado = 'Activo';";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean cambiarUsuarioVendedor(int idUsuario) throws SQLException {
        String sql = "UPDATE usuario SET rol = 'Vendedor' WHERE id_usuario = ? AND estado = 'Activo';";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarUsuario(int idUsuario) throws SQLException{
        String sql = "UPDATE usuario SET estado = 'Inactivo' WHERE id_usuario = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean reactivarUsuario(int idUsuario) throws SQLException {
        String sql = "UPDATE usuario SET estado = 'Activo' WHERE id_usuario = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Persona> listarUsuarios() throws SQLException {
        String sql = "SELECT id_usuario, nombre, apellido, usuario, correo, contrasena, rol, estado FROM usuario ORDER BY id_usuario";

        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            return mapearListaUsuarios(rs);


        }
    }

    @Override
    public List<Persona> buscarUsuarios(String texto) throws SQLException {
        String sql = """
                SELECT id_usuario, nombre, apellido, usuario, correo, contrasena, rol, estado
                FROM usuario
                WHERE LOWER(nombre) LIKE LOWER(?)
                   OR LOWER(apellido) LIKE LOWER(?)
                   OR LOWER(usuario) LIKE LOWER(?)
                   OR LOWER(correo) LIKE LOWER(?)
                   OR LOWER(rol) LIKE LOWER(?)
                   OR LOWER(estado) LIKE LOWER(?)
                ORDER BY id_usuario
                """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String filtro = "%" + texto + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            ps.setString(4, filtro);
            ps.setString(5, filtro);
            ps.setString(6, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                return mapearListaUsuarios(rs);
            }
        }
    }

    private List<Persona> mapearListaUsuarios(ResultSet rs) throws SQLException {
        List<Persona> usuarios = new ArrayList<>();

        while (rs.next()) {
            usuarios.add(crearPersonaDesdeResultSet(rs));
        }

        return usuarios;
    }

    private Persona crearPersonaDesdeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_usuario");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String usuario = rs.getString("usuario");
        String correo = rs.getString("correo");
        String password = rs.getString("contrasena");
        String rol = rs.getString("rol");
        String estado = rs.getString("estado");

        if ("Administrador".equalsIgnoreCase(rol)) {
            return new Administrador(id, nombre, apellido, usuario, correo, password, estado, rol);
        }

        if ("Vendedor".equalsIgnoreCase(rol)) {
            return new Vendedor(id, nombre, apellido, usuario, correo, password, estado, rol);
        }

        return new Cliente(id, nombre, apellido, usuario, correo, password, estado, rol);
    }
}