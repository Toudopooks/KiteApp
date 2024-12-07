package com.example.kiteapp.Modelo;

public class historialOrdenes {
    private String productos,preciototal;

    public historialOrdenes(String productos, String preciototal) {
        this.productos = productos;
        this.preciototal = preciototal;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(String preciototal) {
        this.preciototal = preciototal;
    }
}
