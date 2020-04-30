package edu.csulb.cecs.lavilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminView extends Fragment {
    Button signOut;
    Button viewLocation;
    TextView welcomeTextView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_main_home, container, false);
        welcomeTextView = view.findViewById(R.id.welcome_textview);
        welcomeTextView.setText("Welcome!");
        viewLocation = view.findViewById(R.id.viewLocation_btn);

        viewLocation.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.admin_nav_host);
                navController.navigate(R.id.action_home_to_locations);
            }
        });

        return view;
    }
}

