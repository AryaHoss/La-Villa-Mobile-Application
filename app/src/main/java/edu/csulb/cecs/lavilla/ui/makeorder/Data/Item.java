package edu.csulb.cecs.lavilla.ui.makeorder.Data;

public class Item {
    private int itemId;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private boolean locationId;

    public Item(int itemId, String name, String description, float price, int quantity, boolean locationId) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.locationId = locationId;
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

    public boolean isLocationId() {
        return locationId;
    }
}
