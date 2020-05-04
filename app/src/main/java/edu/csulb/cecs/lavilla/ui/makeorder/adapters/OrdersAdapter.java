package edu.csulb.cecs.lavilla.ui.makeorder.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.csulb.cecs.lavilla.R;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Order;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.RestaurantOrder;

public class OrdersAdapter extends ArrayAdapter<RestaurantOrder> {

    private Context mContext;
    int resource;
    BtnClickListener btnClickListener;

    public OrdersAdapter(@NonNull Context context, int resource, @NonNull List<RestaurantOrder> objects, BtnClickListener listener) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        btnClickListener = listener;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d("view", "getView: starting newwww viewww");
        RestaurantOrder order = getItem(position);
        int orderQty = order.getItems().size();
        int total = order.getTotal();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView orderNumberTV = (TextView) convertView.findViewById(R.id.order_number_label);
        TextView itemQtyTV = (TextView) convertView.findViewById(R.id.order_items_qty);
        TextView orderTotalTV = (TextView) convertView.findViewById(R.id.order_total);
        Button completeOrderBtn = (Button) convertView.findViewById(R.id.complete_order_button);
        completeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener != null){
                    btnClickListener.onBtnClick(position);
                }
            }
        });

        orderNumberTV.setText(Integer.toString(position+1));
        itemQtyTV.setText(Integer.toString(orderQty));
        orderTotalTV.setText("$"+Integer.toString(total));
        return convertView;
    }

    public interface BtnClickListener {
        public abstract void onBtnClick(int position);
    }
}
