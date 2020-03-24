package edu.csulb.cecs.lavilla.ui.makeorder;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.*;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MakeOrderViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    DatabaseReference firebaseRootRef, MenuRef;
    ArrayList<String> MenuList;
    ValueEventListener valueEventListener;

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
            if(itemSelected.getItemId() == i.getItemId() && i.getQuantity()>0){
                i.decreaseQty();

            }else if (i.getQuantity()<=0){
                Log.d(TAG,i.getName()+" quantity is now currently "+ i.getQuantity());
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "Inside onDatachange() method");

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String items = ds.child("items").getValue(String.class);
                    MenuList.add(items);
                }

                Log.d(TAG, MenuList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }


        };
        MenuRef.addListenerForSingleValueEvent(valueEventListener);
        Log.d(TAG, "After attaching the listener");
        //Log.d(TAG, MenuList());

    }
    public String printData(){
       String strReturn = "";
        for(String S: MenuList){
            strReturn += S;
        }
        return strReturn;
    }
    private void readData(FirebaseCallback firebaseCallback){

    }
    private interface FirebaseCallback {
        void onCallback(List<String> list);
    }
}
