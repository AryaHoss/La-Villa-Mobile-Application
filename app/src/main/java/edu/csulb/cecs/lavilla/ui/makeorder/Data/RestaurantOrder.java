package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import java.util.Map;

public class RestaurantOrder {

    private String userEmail;
    private Map<String, Integer> items;
    private float total;
    private String status;

    public RestaurantOrder() {
    }

    public RestaurantOrder(String useremail,  Map<String, Integer> items, float total, String status) {
        this.userEmail = useremail;
        this.items = items;
        this.total = total;
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }



    public Map<String, Integer> getItems() {
        return items;
    }

    public float getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
