package edu.csulb.cecs.lavilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class UserHome extends AppCompatActivity {
    LinearLayout account_btn;
    LinearLayout order_btn;
    LinearLayout menu_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        account_btn = findViewById(R.id.manage_account);
        order_btn = findViewById(R.id.order_btn);
        menu_btn = findViewById(R.id.menu_lookup);

        account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, UserAccountEdit.class));
            }
        });

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, LookupMenu.class));
            }
        });
    }

}
