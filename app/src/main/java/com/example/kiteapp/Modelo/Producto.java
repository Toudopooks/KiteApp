package com.example.kiteapp.Modelo;

public class Producto {
    private String productId;
    private String productName;
    private int quantity;

    public Producto(String productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
