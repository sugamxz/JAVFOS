package com.techuniversity.foodordering;

import java.io.Serializable;

public class Order implements Serializable {
    private String orderId;
    private String customer;
    private String date;
    private String status;

    // Constructor, getters, and setters

    public Order(String orderId, String customer, String date, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.date = date;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
