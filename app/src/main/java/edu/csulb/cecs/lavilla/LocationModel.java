package edu.csulb.cecs.lavilla;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class LocationModel {
    private String id;
    private String street;
    private String city;
    private String zip;
    private String hour;
    private String phone;

    public LocationModel(){

    }

    public LocationModel(String id, String street, String city, String zip, String hour, String phone){
        this.id = id;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.hour = hour;
        this.phone = phone;
    }

    public String getId(){
        return id;
    }

    public void setId(String name){
        this.id = id;
    }

    public String getStreet(){
        return street;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getZip() { return zip; }

    public void setZip(String zip) { this.zip = zip; }

    public String getHour(){
        return hour;
    }

    public void setHour(String hour){
        this.hour = hour;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }
}
