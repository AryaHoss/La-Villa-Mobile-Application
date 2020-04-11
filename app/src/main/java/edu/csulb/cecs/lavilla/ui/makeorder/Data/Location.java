package edu.csulb.cecs.lavilla.ui.makeorder.Data;

public class Location{
    private String locationId;
    private String street;
    private String city;
    private int zip;

    public Location(){

    }

    public Location(String locationId, String street, String city, int zip) {
        this.locationId = locationId;
        this.street = street;
        this.city = city;
        this.zip = zip;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getZip() {
        return zip;
    }
}