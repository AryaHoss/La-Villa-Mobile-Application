package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminViewModel extends ViewModel {
    public Location locationToManage;
    ArrayList<RestaurantOrder> orders;
    RestaurantOrder orderManaged;

    public void getMenuItens(Object o) {
    }

    public void setLocation(Location locationSelected) {
        locationToManage = locationSelected;
    }

    public List<RestaurantOrder> getOrders() {
        return orders;
    }

    public void setOrderSelected(RestaurantOrder orderSelected) {
        orderManaged = orderSelected;
    }

    public void getOrdersFromSelectedLocation(Location location, final FirebaseCallBack firebaseCallBack) {
        if(orders ==null){
            orders = new ArrayList<>();
        }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Query query = database.child("Orders").orderByChild("locationId").equalTo(location.getLocationId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();
                for (DataSnapshot order: dataSnapshot.getChildren()) {
                    RestaurantOrder o = order.getValue(RestaurantOrder.class);
                    orders.add(o);
                    Log.d("TAG", "onDataChange: "+o.getLocationId()+o.getOrderMethod());

                }
                firebaseCallBack.callBack(orders);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface FirebaseCallBack{
        void callBack(ArrayList<RestaurantOrder> orders);

    }
}
