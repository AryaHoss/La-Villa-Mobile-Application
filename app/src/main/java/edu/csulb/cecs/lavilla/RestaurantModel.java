package edu.csulb.cecs.lavilla;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class RestaurantModel {
    private String name;
    private String address;
    private String hour;
    private String phone;

    public RestaurantModel(){

    }

    public RestaurantModel(String name, String address, String hour, String phone){
        this.name = name;
        this.address = address;
        this.hour = hour;
        this.phone = phone;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

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
