package edu.csulb.cecs.lavilla.ui.makeorder;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.*;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MakeOrderViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Order order;
    private Locations locations;
    private Item itemSelected;

    public MakeOrderViewModel() {
        this.locations = locations;
        order = new Order();
        order.createFakeOrder();
    }

    public MakeOrderViewModel(Locations locations) {
        this.locations = locations;
        order = new Order();
    }

    public Order getOrder(){ return order; }

    public void setOrderType(Order.OrderType orderType){
        order.setOrderType(orderType);
    }

    public void setLocation (Location location){
        getOrder().setOrderLocation(location);
    }

    public Item getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(Item item){
        itemSelected = item;
    }

    public void incrementItemQty() {
        for (Item i: getOrder().getItems().getValue()) {
            if(itemSelected.getItemId() == i.getItemId()){
                i.incrementQty();
            }
        }
    }

    public void decreaseItemQty(){
        for (Item i: getOrder().getItems().getValue()) {
            if(itemSelected.getItemId() == i.getItemId()){
                i.decreaseQty();
            }
        }
    }

    public void readData(){
       FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();//firebase root reference to database
       firebaseRootRef = firebaseDatabase.getReference();
       MenuRef = firebaseRootRef.child("Menu");
       MenuList = new ArrayList<>();

       Log.d(TAG, "Before attaching the listener");
        ValueEventListener valueEventListener = new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                Log.d(TAG, "Inside onDatachange() method");

                for (DataSnapShot ds : dataSnapshot.getChildren()){
                    String items;
                }
            }
        }

    }
}
