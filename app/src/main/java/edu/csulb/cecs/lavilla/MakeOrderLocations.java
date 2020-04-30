package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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


public class MakeOrderLocations extends Fragment {
    private ListView locationsListView;
    ArrayList<Location> locations;
    private LocationsAdapter locationsAdapter ;
    private MakeOrderViewModel  mViewModel;
    private Button cancel_order_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LocationDataHandler locationDataHandler = new LocationDataHandler(getContext());
        DatabaseReference h =  FirebaseDatabase.getInstance().getReference();
        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_order_locations, container, false);
        locationsListView = (ListView) view.findViewById(R.id.makeorder_locatioms);
        cancel_order_button = (Button) view.findViewById(R.id.location_cancel_button);

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
                        NavController navController = Navigation.findNavController(getActivity(), R.id.make_order_navHost);
                        Location locationSelected = locations.get(position);
                        mViewModel.setLocation(locationSelected);
                        mViewModel.getMenuItens(itemList -> navController.navigate(R.id.action_makeOrderLocations_to_makeOrderViewMenu));

                    }
                });
            }
        });

        cancel_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo check whether user has payment method and implement logic
                //mViewModel.postOrder();
                Intent intent = new Intent(getActivity(), UserHome.class);
                startActivity(intent);
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
