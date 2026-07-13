package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.model.Proveedor;

import java.sql.SQLException;
import java.util.List;

public interface ProveedorDAO {
    void insertar(Proveedor p) throws SQLException;
    void actualizar(Proveedor p) throws SQLException;
    void eliminar(int id) throws SQLException;
    Proveedor buscarPorId(int id) throws SQLException;
    List<Proveedor> listar() throws SQLException;
    boolean existeProveedor(String empresa, int idExcluir) throws SQLException;
}