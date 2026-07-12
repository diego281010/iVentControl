package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.db.Conexion;
import com.proyectofinalpoo.model.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO implements UsuarioDAOInterface{

    @Override
    public void insertar(Persona p) {
        System.out.println("WIP");
    }

    @Override
    public String validarLogin(String usuario, String password, String rol) throws SQLException {
        String sql = "SELECT nombre FROM usuario WHERE usuario = ? AND contrasena = ? AND rol = ? AND estado = 'Activo'";

        try (
                Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, usuario);
            ps.setString(2, password);
            ps.setString(3, rol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
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

}