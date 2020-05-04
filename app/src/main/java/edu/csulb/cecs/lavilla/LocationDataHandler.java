package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Location;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;

public class LocationDataHandler extends ViewModel {
    private DatabaseReference database;

    public LocationDataHandler(Context context){

        this.database = FirebaseDatabase.getInstance().getReference();
    }

    public void setLocation(String loc_id, Location loc){
        this.database.child("locations").child(loc_id).setValue(loc);
    }

    public void getLocation(String rest_id, final FirebaseCallBack firebaseCallBack){
        DatabaseReference ref = database.child("locations").child(rest_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Location loc = dataSnapshot.getValue(Location.class);
                firebaseCallBack.getLocationMethod(loc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllLocations(final FirebaseCallBack firebaseCallBack){
        final List<Location> allLocations = new ArrayList<>();
        Query ref = database.child("locations");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allLocations.clear();
                for(DataSnapshot locationSnapshot: dataSnapshot.getChildren()){
                    Location loc = locationSnapshot.getValue(Location.class);
                    System.out.println(loc.getCity()+ "    "+ loc.getStreet());
                    allLocations.add(loc);
                }
                firebaseCallBack.getAllLocationMethod(allLocations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void removeLocation(String rest_id){
        this.database.child("locations").child(rest_id).removeValue();
    }

    public void getManagedLoactions(String uid, final FirebaseCallBack firebaseCallback){
        List<Location> managedLocations = new ArrayList<>();
        Query query = database.child("locationsManaged").child(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", "onChildAdded: "+dataSnapshot.getKey());
                for(DataSnapshot locationSnapshot: dataSnapshot.getChildren()){
                    Log.d("TAG", "location-->: "+locationSnapshot.getKey());

                    Location loc = locationSnapshot.getValue(Location.class);
                    System.out.println("location!!!!! ----->>>>"+loc.getCity()+ "    "+ loc.getStreet());
                    managedLocations.add(loc);
                    Log.d("TAG", "locationsize: "+managedLocations.size());

                }
                firebaseCallback.getAllLocationMethod(managedLocations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }
        );

    }

    public void removeAllLocations(){
        this.database.child("locations").removeValue();
    }

    public interface FirebaseCallBack{
        void getLocationMethod(Location location);
        void getAllLocationMethod(List<Location> allLocations);
    }
}
