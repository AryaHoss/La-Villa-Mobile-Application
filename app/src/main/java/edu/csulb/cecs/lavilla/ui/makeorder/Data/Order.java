package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import java.util.ArrayList;


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
    }