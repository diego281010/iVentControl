package com.proyectofinalpoo.util;

import com.proyectofinalpoo.model.Persona;

public class Sesion {

    private static Sesion instancia;

    private Persona usuarioActual;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public void iniciarSesion(Persona persona) {
        this.usuarioActual = persona;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Persona getUsuarioActual() {
        return usuarioActual;
    }

    public boolean esAdministrador() {
        return usuarioActual != null && "Administrador".equals(getRolActual());
    }

    public boolean esVendedor() {
        return usuarioActual != null && "Vendedor".equals(getRolActual());
    }

    public boolean esCliente() {
        return usuarioActual != null && "Cliente".equals(getRolActual());
    }

    private String getRolActual() {
        if (usuarioActual instanceof com.proyectofinalpoo.model.Administrador a) {
            return a.getRol();
        }
        if (usuarioActual instanceof com.proyectofinalpoo.model.Vendedor v) {
            return v.getRol();
        }
        if (usuarioActual instanceof com.proyectofinalpoo.model.Cliente c) {
            return c.getRol();
        }
        return null;
    }
}