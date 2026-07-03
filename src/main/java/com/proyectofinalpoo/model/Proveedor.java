package com.proyectofinalpoo.model;

public class Proveedor {
    private int id;
    private String empresa;
    private String telefono;
    private String correo;
    private String direccion;
    private String pais;

    public Proveedor() { }

    public Proveedor(int id, String empresa, String telefono, String correo, String direccion, String pais) {
        this.id = id;
        this.empresa = empresa;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
