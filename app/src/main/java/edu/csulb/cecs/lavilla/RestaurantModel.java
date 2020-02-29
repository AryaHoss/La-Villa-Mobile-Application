package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantModel {
    private String address;
    private String hours;
    private String phoneNumber;

    public RestaurantModel() {

    }

    public RestaurantModel(String address, String hours, String phoneNumber) {
        this.address = address;
        this.hours = hours;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "Location: "+this.getAddress() +"\nHours: " +this.getHours()+ "\n phone Number:" + this.getPhoneNumber();
    }
}
