package edu.csulb.cecs.lavilla;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.RestaurantOrder;


public class viewOrdersInProgress extends Fragment {

ArrayList<RestaurantOrder> orders;

    public viewOrdersInProgress() {

        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_orders_in_progress, container, false);
        return view;
    }
}
