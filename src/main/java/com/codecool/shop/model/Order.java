package com.codecool.shop.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;

    private static int highest = 0;

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
        Order.setHighest(Order.getHighest() + 1);
        return Order.getHighest();
    }

    private static int getHighest(){
        return Order.highest;
    }

    private static void setHighest(int num) {
        Order.highest = num;
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
