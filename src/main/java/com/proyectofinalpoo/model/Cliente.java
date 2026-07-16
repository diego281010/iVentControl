package com.proyectofinalpoo.model;

public class Cliente extends Persona{

    public Cliente(int id, String nombre, String apellido, String usuario, String correo, String password, String estado, String rol) {
        super(id, nombre, apellido, usuario, correo, password, estado, rol);
    }

    @Override
    public String descripcionPermisos() {
        return "Acceso de consulta: ver catálogo de productos.";
    }
}
