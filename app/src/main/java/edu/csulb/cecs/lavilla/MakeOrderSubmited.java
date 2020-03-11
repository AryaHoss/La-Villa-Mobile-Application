package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.CartAdapter;


public class MakeOrderSubmited extends Fragment {

    ListView detailsListView;
    MakeOrderViewModel mViewModel;
    CartAdapter cartAdapter;


    public MakeOrderSubmited() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_order_submited, container, false);

        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);
        detailsListView =(ListView) view.findViewById(R.id.order_details_listview);
        cartAdapter = new CartAdapter(getContext(), R.layout.cart_adapter_view_layout, mViewModel.getOrder().getPickedItems());
        detailsListView.setAdapter(cartAdapter);


        return view;

    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
