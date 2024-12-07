package com.example.kiteapp.Modelo;

public class carritoItem {
    private String nombre;
    private String precio;
    private String cantidad;
    private String url;

    public carritoItem(String nombre, String precio, String cantidad, String url) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.url = url;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }

    public String getCantidad() { return cantidad; }
    public void setCantidad(String cantidad) { this.cantidad = cantidad; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
