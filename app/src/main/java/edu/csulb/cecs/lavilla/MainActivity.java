package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ViewModelMock vm = new ViewModelMock();
    public TextView rest_phone, rest_hours, rest_address,rest_name;
    public RestaurantModel retrieved;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rest_phone = findViewById(R.id.rest_phone);
        rest_hours = findViewById(R.id.rest_hours);
        rest_address = findViewById(R.id.rest_address);
        rest_name = findViewById(R.id.rest_name);
        final RestaurantModel[] retrieved = {new RestaurantModel()};
        vm.fetchData(new ViewModelMock.FireBaseCallBack() {  // this interface is the one Brandon and I will be using to handle the UI
            @Override
            public void saveDataInterfaceMethod(RestaurantModel rm, RestaurantModel dest) {
                dest = rm;
                retrieved[0] = dest;
                System.out.println(rm.toString());
                rest_phone.setText(rm.getPhoneNumber());
                rest_hours.setText(rm.getHours());
                rest_address.setText(rm.getAddress());
            }
        });
        System.out.println(retrieved[0].toString());

    }






}