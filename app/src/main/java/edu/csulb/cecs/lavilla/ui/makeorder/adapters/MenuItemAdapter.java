package edu.csulb.cecs.lavilla.ui.makeorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.cecs.lavilla.R;
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
        Item item = getItem(position);
        String name = item.getName();
        String description = item.getDescription();
        float price = item.getPrice();
        int quantity = item.getQuantity();
        int id = item.getItemId();
        String imgUrl = item.getImgUrl();

        //create the location with this info
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvItemName = (TextView) convertView.findViewById(R.id.item_name);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.item_price);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.item_quantity);
        ImageView itemImg = (ImageView) convertView.findViewById(R.id.item_img);


        tvItemName.setText(name);
        tvPrice.setText("$ "+Float.toString(price));
        tvQuantity.setText(Integer.toString(quantity));
        Picasso.get().load(imgUrl).into(itemImg);
        return convertView;
    }



}
