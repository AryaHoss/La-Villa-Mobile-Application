package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;


public class Order{

        public enum OrderType{
            DELIVERY, PICKUP
        }

        private int orderId;
        private int userId;
        private ArrayList<Item> items;
        private float subtotal;
        private float total;
        private OrderType orderType;
        private Location orderLocation;

    public Location getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(Location orderLocation) {
        this.orderLocation = orderLocation;
    }




    public Order(ArrayList<Item> items) {
        this.items = new ArrayList<>();
        for (Item i: items ) {
           addItem(i);
        }
    }

    public Order(){
            items = new ArrayList<>();
            total = 0;
            subtotal = 0;
            orderType = OrderType.DELIVERY;
            }

         public void addItem(Item item){
            items.add((item));
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
            return total;
        }

        public void setOrderType(OrderType ot){
            this.orderType = ot;
        }


    public void  printOrder() {
        for (Item i : items){
            System.out.println("name: "+ i.getName() +" price:     " + i.getPrice()  + "qty:     "+ i.getQuantity() );
        }
    }

    public void createFakeOrder(){
            Item carneAsada = new Item(1,"Carne Asada", "Carne Asada with rice and pillo de gallo. Served with 2 flour tortillas", (float)12.99, 0);
            Item chilaquiles = new Item(1,"Chilaquiles", "Fried chip tortillas bathed in salsa verde topped with cream, cheese and delivious eggs. Served with 2 flour tortillas", (float) 10.99, 0);
            Item threeTacoPlate = new Item(1,"Three Tacos Plate", "3 tacos with meat of your choice, served with rice and beans ", (float)12.99, 0);
            Item burrito = new Item(1,"Burrito", "Flour tortilla with meat of choice, rice, beans, cheese, cream and salsa verde", (float)8.99, 0);
            Item polloAsado = new Item(1,"Pollo Asado", "Grilled chicken topped with special sauce. Comes with 1 enchilada, rice and beans. Served with 2 flour tortillas", (float)13.99, 0);
            Item nachos = new Item(1,"Nachos", "Nachos topped with chicken or beef, cream, pico de gallo and cheese", (float)11.99, 0);
            Item horchata = new Item(1,"Carne Asada", "Carne Asada with rice and pillo de gallo. Served with 2 flour tortillas", (float)4.99, 0);
            Item coke = new Item(1,"Coke", "12-Ounze drink", (float)3.99, 0);

            ArrayList<Item> orderItems = new ArrayList(Arrays.asList(carneAsada, chilaquiles, threeTacoPlate, burrito, polloAsado, nachos, horchata, coke));
            for(Item i : orderItems){
                addItem(i);
            }
        }
    }