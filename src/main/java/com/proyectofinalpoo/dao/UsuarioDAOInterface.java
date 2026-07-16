package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.model.Persona;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAOInterface {
    void insertar(Persona p);
    Persona validarLogin(String usuario, String password, String rol) throws SQLException;
    boolean existeUsuarioOCorreo(String usuario, String correo) throws SQLException;
    boolean registrarCliente(Persona persona) throws SQLException;
    boolean cambiarUsuarioAdmin(int idUsuario) throws SQLException;
    boolean cambiarUsuarioCliente(int idUsuario) throws SQLException;
    boolean cambiarUsuarioVendedor(int idUsuario) throws SQLException;
    boolean eliminarUsuario(int idUsuario) throws SQLException;
    boolean reactivarUsuario(int idUsuario) throws SQLException;
    List<Persona> listarUsuarios() throws SQLException;
    List<Persona> buscarUsuarios(String texto) throws SQLException;

}
