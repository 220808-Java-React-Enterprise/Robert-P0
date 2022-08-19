package com.revature.exotic_jerky.models;

import java.util.UUID;

public class Customer {
    private String id, fName, lName, email, password, address, city, state, zip, phone, role;

    public Customer(){
        this.id = UUID.randomUUID().toString();
        this.role = "DEFAULT";
    }

    public Customer(String email, String password){
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.role = "DEFAULT";
    }
    public Customer(String id, String email, String password, String fName, String lName, String address, String city, String state, String zip, String phone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.role = "DEFAULT";
    }

    public Customer(String id, String email, String password, String fName, String lName, String address, String city, String state, String zip, String phone, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.role = role;
    }

    public Customer(Customer customer) {
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.fName = customer.getfName();
        this.lName = customer.getlName();
        this.address = customer.getAddress();
        this.city = customer.getCity();
        this.state = customer.getState();
        this.zip = customer.getZip();
        this.phone = customer.getPhone();
        this. role = customer.getRole();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}