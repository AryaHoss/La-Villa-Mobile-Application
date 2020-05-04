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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.AdminViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Location;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.Locations;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.RestaurantOrder;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.LocationsAdapter;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import edu.csulb.cecs.lavilla.ui.makeorder.adapters.LocationsAdapter;

public class adminOrderLocations extends Fragment {
    ListView locationsListView;
    ArrayList<Location> locations;
    LocationsAdapter locationsAdapter;
    AdminViewModel mViewModel;



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
        mViewModel = new ViewModelProvider(getActivity()).get(AdminViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_order_location, container, false);
        locationsListView = (ListView) view.findViewById(R.id.admin_locations);
        String adminId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        locationDataHandler.getManagedLoactions(adminId, new LocationDataHandler.FirebaseCallBack() {
            @Override
            public void getLocationMethod(Location location) {            }

            @Override
            public void getAllLocationMethod(List<Location> allLocations) {
                locationsAdapter = new LocationsAdapter(getContext(), R.layout.locations_adapter_view_layout , allLocations);
                locationsListView.setAdapter(locationsAdapter);
                locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NavController navController = Navigation.findNavController(getActivity(), R.id.admin_nav_host);
                        Location locationSelected = allLocations.get(position);
                        mViewModel.setLocation(locationSelected);
                        mViewModel.getOrdersFromSelectedLocation(locationSelected, new AdminViewModel.FirebaseCallBack() {
                            @Override
                            public void callBack(ArrayList<RestaurantOrder> orders) {
                                navController.navigate(R.id.action_locations_to_locationOrders);
                            }
                        });


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
