package edu.csulb.cecs.lavilla.ui.makeorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.csulb.cecs.lavilla.R;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;

public class CartAdapter extends ArrayAdapter<Item> {

    public static final String TAG = "CartAdapter:";
    private Context mcontext;
    private int resource;

    public CartAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);
        String name = item.getName();
        int qty = item.getQuantity();
        float totalItemPrice = (float) qty * item.getPrice();

        LayoutInflater inflater =  LayoutInflater.from(mcontext);
        convertView = inflater.inflate(resource, parent,false);

        TextView tvItemName = (TextView) convertView.findViewById(R.id.item_name);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.item_price);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.item_qty);

        tvItemName.setText(name);
        tvPrice.setText("$" + String.format("%.2f", totalItemPrice));
        tvQuantity.setText(Integer.toString(qty));

        return convertView;


    }
}
