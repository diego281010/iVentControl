package com.proyectofinalpoo.model;

public class Administrador extends Persona{
    private String rol;

    public Administrador(int id, String nombre, String apellido, String usuario, String correo, String password, String estado, String rol) {
        super(id, nombre, apellido, usuario, correo, password, estado);
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
