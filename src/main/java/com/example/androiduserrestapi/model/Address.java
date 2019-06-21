package com.example.androiduserrestapi.model;

public class Address {

    String street;
    String suite;
    String city;
    String zipcode;
    Geo geo;

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Geo getGeo() {
        return geo;
    }
}
