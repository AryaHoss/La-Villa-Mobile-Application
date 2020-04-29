package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class Order implements Serializable {

        public enum OrderType {
            DELIVERY, PICKUP;
        }

        private int orderId;
        private int userId;
        private MutableLiveData<ArrayList<Item>> items;
        private float subtotal;
        private float total;
        private OrderType orderType;
        private Location orderLocation;

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    private Address address;


    //constructors
    public Order(ArrayList<Item> items) {
        this.items = new MutableLiveData<ArrayList<Item>>();
        this.items.setValue(new ArrayList<Item>());
        for (Item i: items ) {
           addItem(i);
        }
    }

    public Order(){
            items = new MutableLiveData<ArrayList<Item>>();
            items.setValue(new ArrayList<Item>());
            total = 0;
            subtotal = 0;
            orderType = OrderType.DELIVERY;
    }

         public void addItem(Item item){
            items.getValue().add((item));
            updateTotal(item.getPrice(), item.getQuantity());
         }

         public void updateTotal(float itemPice, float itemQuantity){

            subtotal += (itemPice * itemQuantity);
            total = (float) (subtotal + (subtotal*0.095));

         }

        public void setUserId(int id) {
            userId = id;
        }

        public float getSubtotal() {
            return subtotal;
        }

        public float getTotal() {
            total =0;
            for (Item i : getPickedItems()){
                total += i.getPrice() * i.getQuantity();
                }
            return total;
        }

        public void setOrderType(OrderType ot){
            this.orderType = ot;
        }
        public OrderType getOrderType() {return orderType;}


    public void  printOrder() {
        for (Item i : items.getValue()){
            System.out.println("name: "+ i.getName() +" price:     " + i.getPrice()  + "qty:     "+ i.getQuantity() );
        }
    }

    public MutableLiveData<ArrayList<Item>> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {

        MutableLiveData<ArrayList<Item>> newItems =  new MutableLiveData<>();
        newItems.setValue(items);
        this.items = newItems;
    }

    public Location getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(Location orderLocation) {
        this.orderLocation = orderLocation;
    }


        public ArrayList<Item> getPickedItems(){
            ArrayList<Item> ordered = new ArrayList<>();
            for (Item i: items.getValue()) {
                if(i.getQuantity()>0)
                    ordered.add(i);
            }
            return ordered;
        }
    }