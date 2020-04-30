package edu.csulb.cecs.lavilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.csulb.cecs.lavilla.practice.CheckUpdateList;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.RestaurantOrder;

public class UserOrderHistory extends AppCompatActivity {

    private RecyclerView historyList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_history);

        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");
        mDatabase.keepSynced(true);

        historyList = findViewById(R.id.order_history_rv);
        historyList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RestaurantOrder, UserOrderHistory.HistoryHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RestaurantOrder, UserOrderHistory.HistoryHolder>(RestaurantOrder.class,R.layout.order_history_row, UserOrderHistory.HistoryHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(UserOrderHistory.HistoryHolder orderHolder, RestaurantOrder rOrder, int i) {
                orderHolder.setOrder(rOrder.getOrderId());
                orderHolder.setLocationId(rOrder.getLocationId());
                orderHolder.setStatus(rOrder.getStatus());
                orderHolder.setTotal(rOrder.getTotal());

            }
        };

        historyList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder {

        View view;

        public HistoryHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setOrder(String name) {
            TextView post_name = (TextView) view.findViewById(R.id.order_idTv);
            post_name.setText("Order#:" + name);
        }

        public void setLocationId(String desc) {
            TextView post_desc = (TextView) view.findViewById(R.id.item_locationTv);
            post_desc.setText("Location: " + desc);
        }

        public void setStatus(String status) {
            TextView post_desc = (TextView) view.findViewById(R.id.review_statusTv);
            post_desc.setText(status);
        }

        public void setTotal(int total) {
            TextView post_desc = (TextView) view.findViewById(R.id.total_priceTv);
            int dollar = total/100;
            int cent = total % 100;
            String dollars = String.valueOf(dollar);
            String cents = String.valueOf(cent);
            String format = "Total: $"+dollars+"."+cents;


            post_desc.setText(format);
        }

    }
}
