package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;


public class Order{

        public enum OrderType{
            DELIVERY, PICKUP
        }
        private int orderId;private int userId;
        private MutableLiveData<ArrayList<Item>> items;
        private float subtotal;
        private float total;
        private OrderType orderType;
        private Location orderLocation;


    //constructors
    public Order(ArrayList<Item> items) {
        this.items = new MutableLiveData<ArrayList<Item>>();
        this.items.setValue(new ArrayList<Item>());
        for (Item i: this.items.getValue() ) {
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


    public void createFakeOrder(){
            Item carneAsada = new Item(1,"Carne Asada", "Carne Asada with rice and pillo de gallo. Served with 2 flour tortillas", (float)12.99, 0);
            Item chilaquiles = new Item(2,"Chilaquiles", "Fried chip tortillas bathed in salsa verde topped with cream, cheese and delivious eggs. Served with 2 flour tortillas", (float) 10.99, 0);
            Item threeTacoPlate = new Item(3,"Three Tacos Plate", "3 tacos with meat of your choice, served with rice and beans ", (float)12.99, 0);
            Item burrito = new Item(4,"Burrito", "Flour tortilla with meat of choice, rice, beans, cheese, cream and salsa verde", (float)8.99, 0);
            Item polloAsado = new Item(5,"Pollo Asado", "Grilled chicken topped with special sauce. Comes with 1 enchilada, rice and beans. Served with 2 flour tortillas", (float)13.99, 0);
            Item nachos = new Item(6,"Nachos", "Nachos topped with chicken or beef, cream, pico de gallo and cheese", (float)11.99, 0);
            Item horchata = new Item(7,"Carne Asada", "Carne Asada with rice and pillo de gallo. Served with 2 flour tortillas", (float)4.99, 0);
            Item coke = new Item(8,"Coke", "12-Ounze drink", (float)3.99, 0);

            MutableLiveData<ArrayList<Item>> orderItems = new MutableLiveData<ArrayList<Item>>();
                    orderItems.setValue( new ArrayList(Arrays.asList(carneAsada, chilaquiles, threeTacoPlate, burrito, polloAsado, nachos, horchata, coke)));
            for(Item i : orderItems.getValue()){
                addItem(i);
            }
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