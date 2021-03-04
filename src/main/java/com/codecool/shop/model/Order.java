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

    public String toString() {
        StringBuilder orderInformations = new StringBuilder();
        orderInformations.append("Customer Details:\n");
        orderInformations.append("name: " + this.customer.getFirstName() + " " + this.customer.getLastName() + "\n");
        orderInformations.append("address: " + this.customer.getAddress() + "\n");
        orderInformations.append("city: " + this.customer.getCity() + "\n");
        orderInformations.append("address: " + this.customer.getAddress() + "\n");
        orderInformations.append("postCode: " + this.customer.getPostCode() + "\n");
        orderInformations.append("phoneNumber: " + this.customer.getPhoneNumber() + "\n");
        orderInformations.append("email: " + this.customer.getEmail() + "\n");
        orderInformations.append("Order Details:\n");
        orderInformations.append("order id: " + this.id + "\n");
        orderInformations.append("date: " + this.date + "\n");
        orderInformations.append("product(s):\n");
        for (Product product : productList) {
            orderInformations.append("product name: " + product.getName() + "\n");
            orderInformations.append("product category: " + product.getProductCategory() + "\n");
            orderInformations.append("product continent: " + product.getSupplier() + "\n");
            orderInformations.append("product unitprice: " + product.getDefaultPrice() + " " + product.getDefaultCurrency() + "\n");
            orderInformations.append("product quantity: " + product.getQuantity() + "\n");
            orderInformations.append("------------------");
        }
        orderInformations.append("Total price: " + this.totalPrice + "\n");

        return orderInformations.toString();
    }
}
