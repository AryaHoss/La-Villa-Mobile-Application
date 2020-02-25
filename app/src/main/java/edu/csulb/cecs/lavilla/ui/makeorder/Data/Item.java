package edu.csulb.cecs.lavilla.ui.makeorder.Data;

public class Item {
    private int itemId;
    private String description;
    private float price;
    private int quantity;

    public Item(int itemId, String description, float price, int quantity) {
        this.itemId = itemId;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
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
}
