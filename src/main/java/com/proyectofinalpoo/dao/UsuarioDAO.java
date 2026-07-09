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
        String sql = "SELECT nombre FROM usuario WHERE usuario = ? AND contrasena = ? AND rol = ? AND estado = 'Activo";

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

}
