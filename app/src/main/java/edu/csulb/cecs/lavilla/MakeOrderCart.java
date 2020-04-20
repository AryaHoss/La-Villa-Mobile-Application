package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.CartAdapter;


public class MakeOrderCart extends Fragment {

    ListView cartListView;
    MakeOrderViewModel mViewModel;
    CartAdapter cartAdapter;
    Button checkoutBtn;


    public MakeOrderCart() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_order_cart, container, false);
        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);
        cartListView = (ListView) view.findViewById(R.id.cart_listview);
        cartAdapter = new CartAdapter(getContext(), R.layout.cart_adapter_view_layout, mViewModel.getOrder().getPickedItems());
        cartListView.setAdapter(cartAdapter);
        TextView tvOrderTotal = view.findViewById(R.id.order_total);
        tvOrderTotal.setText("$ "+Float.toString(mViewModel.getOrder().getTotal()));
        checkoutBtn = view.findViewById(R.id.checkout_btn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo check whether user has payment method and implement logic
                mViewModel.postOrder();
//                NavController  nController = Navigation.findNavController(getActivity(), R.id.make_order_navHost);
//                nController.navigate(R.id.action_makeOrderCart_to_makeOrderSubmited);

                int total = (int) (mViewModel.getOrder().getTotal() * 100);

                Intent intent = new Intent(getActivity(),CheckoutActivity.class);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });
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
