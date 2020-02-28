package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    public TextView rest_phone, rest_hours, rest_address,rest_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rest_phone = findViewById(R.id.rest_phone);
        rest_hours = findViewById(R.id.rest_hours);
        rest_address = findViewById(R.id.rest_address);
        rest_name = findViewById(R.id.rest_name);

        RestaurantModel restaurant = new RestaurantModel("1250 Bellflower Blvd\nLong Beach, CA 90840", "Mon - Fri: 10:00 to 22:00\nWeekend: Closed", "(562) 985-4111");
        database.child("restaurants").child("001").setValue(restaurant);

        DatabaseReference ref = database.child("restaurants").child("001");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RestaurantModel rest = dataSnapshot.getValue(RestaurantModel.class);
                rest_phone.setText(rest.getPhoneNumber());
                rest_hours.setText(rest.getHours());
                rest_address.setText(rest.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}