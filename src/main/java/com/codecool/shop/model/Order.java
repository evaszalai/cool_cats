package com.codecool.shop.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;

    private int highest = 0;

    private LocalDate date;

    private List<Product> productList;

    private Customer customer;

    public Order(List<Product> productList, Customer customer) {
        this.productList = productList;
        this.date = LocalDate.now();
        this.customer = customer;
        this.id = generateId();

    }

    private int generateId(){
        this.setHighest(getHighest() + 1);
        return getHighest();
    }

    private int getHighest(){
        return this.highest;
    }

    public void setHighest(int highest) {
        this.highest = highest;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
