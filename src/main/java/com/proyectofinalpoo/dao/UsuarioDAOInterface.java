package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.model.Persona;
import java.sql.SQLException;

public interface UsuarioDAOInterface {
    void insertar(Persona p);
    Persona validarLogin(String usuario, String password, String rol) throws SQLException;
}