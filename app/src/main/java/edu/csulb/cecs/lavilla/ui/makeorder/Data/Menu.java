package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import edu.csulb.cecs.lavilla.MainActivity;

public class Menu {
    private String TAG = "[Menu.java: ]";
    private ArrayList<Item> menu;
    private DatabaseReference database;
    public String a;
    public Menu(){
        menu = new ArrayList<Item>();
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    public void buildMenu(){
        DatabaseReference myMenu = this.database.child("Menu");

        myMenu.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
               Log.i(TAG, dataSnapshot.getKey());
               System.out.println(dataSnapshot.getKey());
               a = "wooo";
            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){
                Log.e(TAG, databaseError.toString());
                a = "awww";
            }
        });
    }

}
