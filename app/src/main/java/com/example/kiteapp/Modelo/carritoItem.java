package com.example.kiteapp.Modelo;

public class carritoItem {
    private String cartItemId;
    private String productId;
    private int quantity;

    public carritoItem(String cartItemId, String productId, int quantity) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
