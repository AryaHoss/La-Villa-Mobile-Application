package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDataHandler extends ViewModel {
    private DatabaseReference database;

    public RestaurantDataHandler(){
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    public void writeRestaurant(String rest_id, RestaurantModel rest){
        this.database.child("restaurants").child(rest_id).setValue(rest);
    }

    public void readRestaurant(String rest_id, final FirebaseCallBack firebaseCallBack){
        DatabaseReference ref = database.child("restaurants").child(rest_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RestaurantModel rest = dataSnapshot.getValue(RestaurantModel.class);
                firebaseCallBack.readRestaurantMethod(rest);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readAllRestaurants(final FirebaseCallBack firebaseCallBack){
        final List<RestaurantModel> allRestaurants = new ArrayList<>();
        Query ref = database.child("restaurants");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allRestaurants.clear();
                for(DataSnapshot restaurantSnapshot: dataSnapshot.getChildren()){
                    RestaurantModel rest = restaurantSnapshot.getValue(RestaurantModel.class);
                    allRestaurants.add(rest);
                }
                firebaseCallBack.readAllRestaurantsMethod(allRestaurants);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void removeRestaurant(String rest_id){
        this.database.child("restaurants").child(rest_id).removeValue();
    }

    public void removeAllRestaurants(){
        this.database.child("restaurants").removeValue();
    }

    public interface FirebaseCallBack{
        void readRestaurantMethod(RestaurantModel restaurant);
        void readAllRestaurantsMethod(List<RestaurantModel> allRestaurant);
    }
}
