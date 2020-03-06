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

import java.util.ArrayList;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;


public class MakeOrderViewMenu extends Fragment {

    ListView menuItemsListView;
    ArrayList<Item> items;
    MenuItemAdapter itemsAdapter;
    MakeOrderViewModel mViewModel;




    public MakeOrderViewMenu() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_make_order_view_menu, container, false);

        itemsAdapter = new MenuItemAdapter(getContext(), R.layout.locations_adapter_view_layout, items );


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
