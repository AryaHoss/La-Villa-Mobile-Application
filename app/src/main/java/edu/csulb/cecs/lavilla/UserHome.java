package edu.csulb.cecs.lavilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import edu.csulb.cecs.lavilla.practice.CheckUpdateList;

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
                startActivity(new Intent(UserHome.this, CheckUpdateList.class));
            }
        });

        order_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(UserHome.this, MakeOrderMain.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getMenuInflater().inflate(R.menu.option_menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home_sign_out:
                Intent intent = new Intent(UserHome.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
