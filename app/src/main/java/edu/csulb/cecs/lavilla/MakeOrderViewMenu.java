package edu.csulb.cecs.lavilla;

import android.content.Context;
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

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.MenuItemAdapter;


public class MakeOrderViewMenu extends Fragment {

    ListView menuItemsListView;
    MenuItemAdapter itemsAdapter;
    MakeOrderViewModel mViewModel;

    public ArrayList<Item> createFakeItems(){
        Item carneAsada = new Item(1,"Carne Asada", "Carne Asada with rice and pillo de gallo. Served with 2 flour tortillas", (float)12.99, 0);
        Item chilaquiles = new Item(2,"Chilaquiles", "Fried chip tortillas bathed in salsa verde topped with cream, cheese and delivious eggs. Served with 2 flour tortillas", (float) 10.99, 0);
        Item threeTacoPlate = new Item(3,"Three Tacos Plate", "3 tacos with meat of your choice, served with rice and beans ", (float)12.99, 0);
        Item burrito = new Item(4,"Burrito", "Flour tortilla with meat of choice, rice, beans, cheese, cream and salsa verde", (float)8.99, 0);
        Item polloAsado = new Item(5,"Pollo Asado", "Grilled chicken topped with special sauce. Comes with 1 enchilada, rice and beans. Served with 2 flour tortillas", (float)13.99, 0);
        Item nachos = new Item(6,"Nachos", "Nachos topped with chicken or beef, cream, pico de gallo and cheese", (float)11.99, 0);
        Item horchata = new Item(7,"Carne Asada", "Carne Asada with rice and pillo de gallo. Served with 2 flour tortillas", (float)4.99, 0);
        Item coke = new Item(8,"Coke", "12-Ounze drink", (float)3.99, 0);

        ArrayList<Item> orderItems = new ArrayList(Arrays.asList(carneAsada, chilaquiles, threeTacoPlate, burrito, polloAsado, nachos, horchata, coke));
    return orderItems;
    }



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
        menuItemsListView = (ListView) view.findViewById(R.id.menu_items_listview);

        itemsAdapter = new MenuItemAdapter(getContext(), R.layout.items_adapter_view_layout,
                mViewModel.getOrder().getItems().getValue() );

        menuItemsListView.setAdapter(itemsAdapter);
        mViewModel.getOrder().getItems().observe(getActivity(), new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                itemsAdapter = new MenuItemAdapter(getContext(), R.layout.items_adapter_view_layout, items );
                menuItemsListView.setAdapter(itemsAdapter);

            }
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
