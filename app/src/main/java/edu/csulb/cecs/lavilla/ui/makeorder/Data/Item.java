package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import java.util.ArrayList;
import java.util.Arrays;

public class Item {
    private int itemId;
    private String name;
    private String description;
    private float price;
    private int quantity;

    public Item(int itemId, String name, String description, float price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }
    public int getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void incrementQty() {
        quantity++;
    }

    public void decreaseQty(){
        if(quantity>0)
            quantity--;
    }

    //temp function to create a list of items to display

}
