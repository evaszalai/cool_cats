package com.codecool.shop.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;

    private float totalPrice;

    private static int highest = 0;

    private LocalDate date;

    private List<Product> productList;

    private Customer customer;

    public Order(List<Product> productList, float totalPrice) {
        this.productList = productList;
        this.date = LocalDate.now();
        this.id = generateId();
        this.totalPrice = totalPrice;
    }

    private int generateId(){
        Order.setHighest(Order.getHighest() + 1);
        return Order.getHighest();
    }

    public float getTotalPrice() {
        return totalPrice;
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
