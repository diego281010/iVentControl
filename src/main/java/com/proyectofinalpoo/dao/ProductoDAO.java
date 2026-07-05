package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.model.Producto;

import java.util.List;

public interface ProductoDAO {
    void insertar(Producto p);
    void actualizar(Producto p);
    void eliminar(int id);
    Producto buscarPorId(int id);
    List<Producto> listar();
}
