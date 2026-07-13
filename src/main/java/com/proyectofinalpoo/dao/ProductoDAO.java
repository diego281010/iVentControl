package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.model.Producto;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    void insertar(Producto p) throws SQLException;
    void actualizar(Producto p) throws SQLException;
    void eliminar(int id) throws SQLException;
    Producto buscarPorId(int id) throws SQLException;
    List<Producto> listar() throws SQLException;
    boolean existeProducto(String nombreProducto, int idExcluir) throws SQLException;
}