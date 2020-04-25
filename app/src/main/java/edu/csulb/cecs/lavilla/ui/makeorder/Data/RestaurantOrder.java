package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class RestaurantOrder implements Serializable {

    private String userId;
    private Map<String, Integer> items;
    private int total;
    private String status;
    private String locationId;
    private String orderMethod;
    private Map<String, String> shippingAddress;
    private Date purchaseDate;
    private String orderId;

    public RestaurantOrder() {
    }

    public RestaurantOrder(String userId,  Map<String, Integer> items, String locationId, String orderMethod, int total) {
        this.userId = userId;
        this.items = items;
        this.locationId = locationId;
        this.orderMethod = orderMethod;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date date) {
        this.purchaseDate = date;
    }

    public String getUserId() {
        return userId;
    }

    public String getOrderMethod(){
        return this.orderMethod;
    }

    public String getLocationId() {
        return locationId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShippingAddress(Map<String, String> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Map<String, String> getShippingAddress() {
        return shippingAddress;
    }
}
