package com.example.kiteapp.Modelo;

public class historialOrdenes {
    private String orderId;
    private String date;
    private double totalAmount;

    public historialOrdenes(String orderId, String date, double totalAmount) {
        this.orderId = orderId;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
