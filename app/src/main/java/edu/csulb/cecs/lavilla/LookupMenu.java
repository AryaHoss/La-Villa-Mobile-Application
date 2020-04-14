package edu.csulb.cecs.lavilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class LookupMenu extends AppCompatActivity {

    RecyclerView mRecyclerView;
    UserMenuAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_menu);

        mRecyclerView = findViewById(R.id.menuRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new UserMenuAdapter(this, getMyList());
        mRecyclerView.setAdapter(myAdapter);
    }

    private ArrayList<UserMenuModel> getMyList(){

        ArrayList<UserMenuModel> models = new ArrayList<>();

        UserMenuModel m = new UserMenuModel();
        m.setTitle("Tacos");
        m.setDescription("Al Pastor in corn tortilla topped with onion and cilantro");
        m.setImg(R.drawable.tacos);
        models.add(m);

        m = new UserMenuModel();
        m.setTitle("Burrito");
        m.setDescription("Rice, Beans, Cheese, and Andilloue Sausage, wrapped in a flour tortilla");
        m.setImg(R.drawable.burrito);
        models.add(m);

        return models;
    }

}
