package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RestaurantOrderViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    DatabaseReference firebaseRootRef, MenuRef;
    ArrayList<RestaurantOrder> orderList;
    ValueEventListener valueEventListener;

    private RestaurantOrder order;
    private Locations locations;
    private Item itemSelected;

    public RestaurantOrderViewModel() {
        this.locations = locations;
        order = new RestaurantOrder();
    }

    public RestaurantOrderViewModel(Locations locations) {
        this.locations = locations;
        order = new RestaurantOrder();
    }

    public void setOrderStatus(String status){
        order.setStatus(status);
    }
    public String getStatus(){ return order.getStatus(); }

   public String getOrderId(){ return getOrderId(); }

   public void setOrderId(String orderId) {order.setOrderId(orderId);}


    public Date getPurchaseDate() {
        return order.getPurchaseDate();
    }

    public void setPurchaseDate(Date date) {
        order.setPurchaseDate(date);
    }

    public String getUserId() {
        return order.getUserId();
    }

    public String getOrderMethod(){
        return order.getOrderMethod();
    }

    public String getLocationId() {
        return order.getLocationId();
    }

    public Map<String, Integer> getItems() {
        return order.getItems();
    }

    public int getTotal() {
        return order.getTotal();
    }

    public void setTotal(int total) {
        order.setTotal(total);
    }

    public void setShippingAddress(Map<String, String> shippingAddress) {
        order.setShippingAddress(shippingAddress);
    }

    public Map<String, String> getShippingAddress() {
        return order.getShippingAddress();
    }




    public ArrayList<RestaurantOrder> getPendingOrders(){
        System.out.println("FINDING ITEMS AGAIN");

        firebaseRootRef = FirebaseDatabase.getInstance().getReference();
        MenuRef = firebaseRootRef.child("Orders");
        orderList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println("menu items:");
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    RestaurantOrder order = ds.getValue(RestaurantOrder.class);

                    orderList.add(order);

                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        MenuRef.addListenerForSingleValueEvent(valueEventListener);
return orderList;
    }



    public interface FirebaseCallback {
        void onCallback(List<Item> itemList);
    }
}
