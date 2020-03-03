package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MakeOrderLocations extends Fragment {
    ListView locationsListView;
    ArrayAdapter<String> adapter;
    String[] data = {"data0","data1", "data2", "data3", "data4", "data5", "data6"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_order_locations, container, false);
        locationsListView = (ListView) view.findViewById(R.id.makeorder_locatioms);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        locationsListView.setAdapter(adapter);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
