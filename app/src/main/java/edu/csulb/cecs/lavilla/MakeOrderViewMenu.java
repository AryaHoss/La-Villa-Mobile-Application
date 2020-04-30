package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.MenuItemAdapter;


public class MakeOrderViewMenu extends Fragment {

    private ListView menuItemsListView;
    private MenuItemAdapter itemsAdapter;
    private MakeOrderViewModel mViewModel;

    public MakeOrderViewMenu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_make_order_view_menu, container, false);
        menuItemsListView = (ListView) view.findViewById(R.id.menu_items_listview);

        itemsAdapter = new MenuItemAdapter(getContext(), R.layout.items_adapter_view_layout,
                mViewModel.getOrder().getItems().getValue() );
        menuItemsListView.setAdapter(itemsAdapter);
        mViewModel.getOrder().getItems().observe(getActivity(), items -> {
            itemsAdapter = new MenuItemAdapter(getContext(), R.layout.items_adapter_view_layout, items );
            menuItemsListView.setAdapter(itemsAdapter);
        });

        menuItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item itemSelected = mViewModel.getOrder().getItems().getValue().get(position);
                mViewModel.setItemSelected(itemSelected);

                NavController navController = Navigation.findNavController(getActivity(), R.id.make_order_navHost);
                navController.navigate(R.id.action_makeOrderViewMenu_to_itemView);
            }
        });


        //set button to go to cart fragment;
        Button goToCartBtn = view.findViewById(R.id.goto_cart_btn);
        goToCartBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.make_order_navHost);
                navController.navigate(R.id.action_makeOrderViewMenu_to_makeOrderCart);
            }
        });

        Button cancel_order_button = view.findViewById(R.id.menu_cancel_button);
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
