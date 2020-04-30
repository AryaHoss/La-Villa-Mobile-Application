package edu.csulb.cecs.lavilla;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Location;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Locations;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.LocationsAdapter;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import edu.csulb.cecs.lavilla.ui.makeorder.adapters.LocationsAdapter;

public class adminOrderLocation extends Fragment {
    ListView locationsListView;
    ArrayList<Location> locations;
    LocationsAdapter locationsAdapter;
    MakeOrderViewModel mViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_locations);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LocationDataHandler locationDataHandler = new LocationDataHandler(getContext());
        DatabaseReference h =  FirebaseDatabase.getInstance().getReference();
        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_view_locations, container, false);
        locationsListView = (ListView) view.findViewById(R.id.view_locations);

        System.out.println("calling get all locations");

        locationDataHandler.getAllLocations(new LocationDataHandler.FirebaseCallBack() {
            @Override
            public void getLocationMethod(Location location) { }

            @Override
            public void getAllLocationMethod(List<Location> locations) {

                locationsAdapter = new LocationsAdapter(getContext(), R.layout.locations_adapter_view_layout , locations);
                locationsListView.setAdapter(locationsAdapter);
                locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    //Before navigating to next fragment, viewModel gets the items from Firebase
                    //so theyre ready to display on the next fragment
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NavController navController = Navigation.findNavController(getActivity(), R.id.view_locations);
                        Location locationSelected = locations.get(position);
                        mViewModel.setLocation(locationSelected);
                        mViewModel.getMenuItens(itemList -> navController.navigate(R.id.action_makeOrderLocations_to_makeOrderViewMenu));

                    }
                });
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
