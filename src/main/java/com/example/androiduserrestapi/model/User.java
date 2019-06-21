package com.example.androiduserrestapi.model;

public class User {

    int id;
    String name;
    String username;
    String email;
    Address address;

    public User(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Address getAddress() {
        return address;
    }
}
