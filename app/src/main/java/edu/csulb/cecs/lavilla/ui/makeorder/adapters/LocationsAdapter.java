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
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Location;

public class LocationsAdapter extends ArrayAdapter<Location>{

    private static final String TAG =  "LocationsAdapter";
    private Context mContext;
    int resource;

    public LocationsAdapter(@NonNull Context context, int resource, @NonNull List<Location> objects) {
        super(context, resource,  objects);
        this.mContext = context;
        this.resource = resource;
    }


    //this is responsible for getting the view and attaching it to the listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String street = getItem(position).getStreet();
        String city = getItem(position).getCity();
        int zip = getItem(position).getZip();
        String id = getItem(position).getLocationId();

        //create the location with this info
        Location location = new Location(id,street, city, zip);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvStreet = (TextView) convertView.findViewById(R.id.locations_street);
        TextView tvCity = (TextView) convertView.findViewById(R.id.locations_city);
        TextView tvZip = (TextView) convertView.findViewById(R.id.locations_zip);
        TextView tvNumber = (TextView) convertView.findViewById(R.id.locations_number);


        tvStreet.setText(street);
        tvCity.setText(city);
        tvZip.setText(Integer.toString(zip));
        tvNumber.setText(Integer.toString(position+1));
        return convertView;
    }
}
