package com.example.getstarted.Models;

public class OrderModel {
    int order_number;
    String date;

    public OrderModel(int order_number, String date) {
        this.order_number = order_number;
        this.date = date;
    }

    public OrderModel() {
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
