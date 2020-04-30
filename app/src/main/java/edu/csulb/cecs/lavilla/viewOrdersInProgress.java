package edu.csulb.cecs.lavilla;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.AdminViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.RestaurantOrder;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.OrdersAdapter;


public class viewOrdersInProgress extends Fragment {

    ArrayList<RestaurantOrder> orders;
    ListView orderItemsListView;
    AdminViewModel mViewModel;
    OrdersAdapter ordersAdapter;
    public viewOrdersInProgress() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(AdminViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_orders_in_progress, container, false);
        orderItemsListView = (ListView) view.findViewById(R.id.managed_orders_in_progress);
        ordersAdapter = new OrdersAdapter(getContext(), R.layout.adapter_orders, mViewModel.getOrders());
        List<RestaurantOrder> orderstest = mViewModel.getOrders();
        Log.d("adapter", "created adapter ");
        orderItemsListView.setAdapter(ordersAdapter);
        Log.d("adapter", "set adapter ");

        orderItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantOrder orderSelected = mViewModel.getOrders().get(position);
                mViewModel.setOrderSelected(orderSelected);

                NavController controller = Navigation.findNavController(getActivity(), R.id.admin_nav_host);
                controller.navigate(R.id.action_orders_to_orderInfo);
            }
        });
        Log.d("adapter", "set clickmlisteners");

        return view;
    }
}
