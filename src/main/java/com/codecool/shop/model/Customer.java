package com.codecool.shop.model;

public class Customer {
    private int id;
    private String country;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String postCode;
    private String phoneNumber;
    private String email;
    private static int highest = 0;
    private String password;

    public Customer(String country, String firstName, String lastName, String address, String city, String postCode, String phoneNumber, String email){
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.id = generateId();
    }

    public Customer(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    private static int generateId(){
        Customer.setHighest(getHighest() + 1);
        return Customer.getHighest();
    }

    private static int getHighest() {
        return Customer.highest;
    }

    public static void setHighest(int highest) {
        Customer.highest = highest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
}
