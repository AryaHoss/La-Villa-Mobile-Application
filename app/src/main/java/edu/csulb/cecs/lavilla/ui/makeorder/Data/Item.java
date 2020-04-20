package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import java.util.HashMap;
import java.util.Map;

public class Item {
    private int itemId;
    private String name;
    private String description;
    private float price;
    private int quantity;


    private String imgUrl;

    public HashMap<String, Boolean> getValidLocations() {
        return validLocation;
    }

    private HashMap<String, Boolean> validLocation;

    public Item(int itemId, String name, String description, float price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(){}

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

    public HashMap<String, Boolean> isValidLocation() {
        return validLocation;
    }

    public void incrementQty() {
        this.quantity++;
    }

    public void decreaseQty() {
        this.quantity--;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getItemDescription (){
        String str = "name: " + this.name +". price" + this.price + " description:" + this.description +" \n";
        if(validLocation == null) {
            System.out.println("valid locaitons is null");
        }
        else{
            for(Map.Entry<String, Boolean> entry : validLocation.entrySet()){
                System.out.println(entry.getKey() + "---" + entry.getValue());
            }
        }
        return str;

    }

}
