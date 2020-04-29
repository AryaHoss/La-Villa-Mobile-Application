package edu.csulb.cecs.lavilla.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.csulb.cecs.lavilla.R;
import edu.csulb.cecs.lavilla.UserMenuModel;

public class CheckUpdateList extends AppCompatActivity {

    private RecyclerView mMenuList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_update_list);

        mDatabase = FirebaseDatabase.getInstance().getReference("Menu");
        mDatabase.keepSynced(true);

        mMenuList = findViewById(R.id.menu_recyclerView);
        mMenuList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<UserMenuModel,MenuHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserMenuModel, MenuHolder>(UserMenuModel.class,R.layout.check_update_row,MenuHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(MenuHolder menuHolder, UserMenuModel userMenuModel, int i) {
                menuHolder.setName(userMenuModel.getName());
                menuHolder.setDesc(userMenuModel.getDescription());

            }
        };

        mMenuList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MenuHolder extends RecyclerView.ViewHolder{

        View view;

        public MenuHolder(View itemView){
            super(itemView);
            view = itemView;
        }
        public void setName(String name){
            TextView post_name = (TextView)view.findViewById(R.id.menuTitle);
            post_name.setText(name);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)view.findViewById(R.id.menuDescription);
            post_desc.setText(desc);
        }
    }
}
