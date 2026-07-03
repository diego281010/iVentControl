package com.proyectofinalpoo.model;

public class Producto {
    private int id;
    private String nombre_producto;
    private String tipo;
    private String color;
    private String almacenamiento;
    private double precio_compra;
    private double precio_venta;
    private int stock;
    private String estado;
    private int id_proveedor;

    public Producto() { }

    public Producto(int id, String nombre_producto, String tipo, String color, String almacenamiento, double precio_compra, double precio_venta, int stock, String estado, int id_proveedor) {
        this.id = id;
        this.nombre_producto = nombre_producto;
        this.tipo = tipo;
        this.color = color;
        this.almacenamiento = almacenamiento;
        this.precio_compra = precio_compra;
        this.precio_venta = precio_venta;
        this.stock = stock;
        this.estado = estado;
        this.id_proveedor = id_proveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public double getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(double precio_compra) {
        this.precio_compra = precio_compra;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
}
