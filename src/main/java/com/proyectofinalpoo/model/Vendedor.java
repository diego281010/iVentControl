package com.proyectofinalpoo.model;

public class Vendedor extends Persona{
    private String rol;
    public Vendedor(int id, String nombre, String apellido, String usuario, String correo, String password, String estado, String rol) {
        super(id, nombre, apellido, usuario, correo, password, estado);
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String descripcionPermisos() {
        return "Acceso operativo: gestión de productos y proveedores.";
    }
}
