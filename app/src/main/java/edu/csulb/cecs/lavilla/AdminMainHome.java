package edu.csulb.cecs.lavilla;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Order;


public class AdminMainHome extends Fragment {
Button viewLocation;

    public AdminMainHome() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_main_home, container, false);
        viewLocation = view.findViewById(R.id.viewLocation_btn);
        viewLocation.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                NavController navController = Navigation.findNavController(getActivity(), R.id.admin_nav_graph);
                navController.navigate(R.id.homeToLocation);
                navController.navigate(R.id.orderLocationToViewInProgress);
                navController.navigate(R.id.inProgressToStatusUpdate);

            }
        });

        return view;
    }
}
