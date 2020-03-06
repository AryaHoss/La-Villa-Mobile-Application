package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Location;

public class MenuItemAdapter extends ArrayAdapter<Item> {

    private static final String TAG =  "MenuItemsAdapter";
    private Context mContext;
    int resource;

    public MenuItemAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String description = getItem(position).getDescription();
        float price = getItem(position).getPrice();
        int quantity = 0;
        int id = getItem(position).getItemId();

        //create the location with this info
        Item item = new Item(id, name, description, price, quantity);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvItemName = (TextView) convertView.findViewById(R.id.item_name);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.item_price);
        TextView tvQuantiy = (TextView) convertView.findViewById(R.id.item_quantity);


        tvItemName.setText(name);
        tvPrice.setText(Float.toString(price));
        tvQuantiy.setText(Integer.toString(0));
        return convertView;
    }

}
