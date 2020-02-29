package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewModelMock extends ViewModel {
    DatabaseReference database;
    RestaurantModel restaurantModel;

    public ViewModelMock() {
        this.database =  FirebaseDatabase.getInstance().getReference();
        restaurantModel = new RestaurantModel();
    }



    public void fetchData(final FireBaseCallBack fireBaseCallBack){
        RestaurantModel restaurant = new RestaurantModel("1250 Bellflower Blvd\nLong Beach, CA 90840", "Mon - Fri: 10:00 to 22:00\nWeekend: Closed", "(562) 985-4111");
        database.child("restaurants").child("001").setValue(restaurant);
        restaurantModel = new RestaurantModel();

        DatabaseReference ref = database.child("restaurants").child("001");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RestaurantModel rest = dataSnapshot.getValue(RestaurantModel.class);
                fireBaseCallBack.saveDataInterfaceMethod(rest, restaurantModel);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface FireBaseCallBack{
        void saveDataInterfaceMethod(RestaurantModel rm, RestaurantModel dest);
    }


}
