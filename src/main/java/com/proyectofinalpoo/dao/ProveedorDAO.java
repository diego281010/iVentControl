package com.proyectofinalpoo.dao;

import com.proyectofinalpoo.model.Vendedor;

import java.util.List;

public interface ProveedorDAO {
    void insertar(Vendedor v);
    void actualizar(Vendedor v);
    void eliminar(Vendedor v);
    void filtrarPorId(int id);
    List<Vendedor> listar();
}
