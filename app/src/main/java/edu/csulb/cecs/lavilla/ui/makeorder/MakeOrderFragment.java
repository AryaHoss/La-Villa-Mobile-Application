package edu.csulb.cecs.lavilla.ui.makeorder;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.csulb.cecs.lavilla.R;

public class MakeOrderFragment extends Fragment {

    private MakeOrderViewModel mViewModel;

    public static MakeOrderFragment newInstance() {
        return new MakeOrderFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.make_order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MakeOrderViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button deliveryButton =  view.findViewById(R.id.delivery_button);
        deliveryButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.make_order_navHost);
                navController.navigate(R.id.action_makeOrderFragment_to_makeOrderLocations);
            }
        });

        Button pickupButton = view.findViewById(R.id.pickup_button);
        pickupButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.make_order_navHost);
                navController.navigate(R.id.action_makeOrderFragment_to_makeOrderLocations);
            }
        });
        }
}
