package com.proyectofinalpoo.model;

public class Vendedor extends Persona{
    public Vendedor(int id, String nombre, String apellido, String usuario, String correo, String password, String estado, String rol) {
        super(id, nombre, apellido, usuario, correo, password, estado, rol);
    }

    @Override
    public String descripcionPermisos() {
        return "Acceso operativo: gestión de productos y proveedores.";
    }
}
