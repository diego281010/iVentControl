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
                    return mapearPersona(rs);
                }
            }
        }

        return null;
    }

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

    /**
     * Decide, en tiempo de ejecución, cuál subclase de Persona construir
     * según el valor de la columna rol. Aquí es donde se usa el polimorfismo:
     * el resto de la app recibe un Persona y no necesita saber cuál es.
     */
    private Persona mapearPersona(ResultSet rs) throws SQLException {

        int id = rs.getInt("id_usuario");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String usuario = rs.getString("usuario");
        String correo = rs.getString("correo");
        String password = rs.getString("contrasena");
        String estado = rs.getString("estado");
        String rol = rs.getString("rol");

        return switch (rol) {
            case "Administrador" -> new Administrador(id, nombre, apellido, usuario, correo, password, estado, rol);
            case "Vendedor" -> new Vendedor(id, nombre, apellido, usuario, correo, password, estado, rol);
            case "Cliente" -> new Cliente(id, nombre, apellido, usuario, correo, password, estado, rol);
            default -> null;
        };
    }
}