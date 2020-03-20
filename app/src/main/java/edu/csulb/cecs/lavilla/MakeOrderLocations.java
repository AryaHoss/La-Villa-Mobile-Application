package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Location;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Locations;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;


public class MakeOrderLocations extends Fragment {
    ListView locationsListView;
    ArrayAdapter<String> adapter;
    String[] data = {"data0","data1", "data2", "data3", "data4", "data5", "data6"};
    ArrayList<Location> locations;
    LocationsAdapter locationsAdapter ;
    MakeOrderViewModel  mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Locations locationsList = new Locations();
        locations = locationsList.getLocations();

        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_order_locations, container, false);
        locationsListView = (ListView) view.findViewById(R.id.makeorder_locatioms);
        locationsAdapter = new LocationsAdapter(getContext(), R.layout.locations_adapter_view_layout , locations);
        locationsListView.setAdapter(locationsAdapter);
        locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location locationSelected = locations.get(position);
                mViewModel.setLocation(locationSelected);

                NavController navController = Navigation.findNavController(getActivity(), R.id.make_order_navHost);
                navController.navigate(R.id.action_makeOrderLocations_to_makeOrderViewMenu);

            }
        });


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
