package edu.csulb.cecs.lavilla;

import android.os.Bundle;


import com.google.firebase.FirebaseApp;

import androidx.appcompat.app.AppCompatActivity;


public class MakeOrderMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_make_order_main);


    }

}
