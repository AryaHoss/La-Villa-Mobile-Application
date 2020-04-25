package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Location;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Order;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.RestaurantOrder;

public class OrderActivity extends AppCompatActivity {

    TextView order_number, order_purchase_date, order_method, order_total, order_status;
    Button home_button;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        home_button = findViewById(R.id.order_home_button);

        order_number = findViewById(R.id.order_number);
        order_purchase_date = findViewById(R.id.order_purchase_date);
        order_method = findViewById(R.id.order_method);
        order_total = findViewById(R.id.order_total);
        order_status = findViewById(R.id.order_status);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(orderId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RestaurantOrder order = dataSnapshot.getValue(RestaurantOrder.class);
                order_number.setText(order.getOrderId());
                order_purchase_date.setText(new SimpleDateFormat("MM/dd/yyyy").format(order.getPurchaseDate()));
                order_method.setText(order.getOrderMethod());
                order_total.setText("$" + String.valueOf((float) order.getTotal() / 100));
                order_status.setText(order.getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_home = new Intent(OrderActivity.this, UserHome.class);
                startActivity(intent_home);
            }
        });
    }
}
