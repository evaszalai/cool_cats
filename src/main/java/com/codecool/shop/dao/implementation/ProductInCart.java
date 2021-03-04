package com.codecool.shop.dao.implementation;

public class ProductInCart {
    private int id;
    private String action;

    public ProductInCart(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
