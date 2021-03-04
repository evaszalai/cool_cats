package com.codecool.shop.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;
    private float totalPrice;
    private LocalDate date;
    private List<Product> productList;
    private Customer customer;

    public Order(List<Product> productList, float totalPrice) {
        this.productList = productList;
        this.date = LocalDate.now();
        this.totalPrice = totalPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
