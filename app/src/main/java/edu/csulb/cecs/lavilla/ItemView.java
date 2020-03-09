package edu.csulb.cecs.lavilla;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemView extends Fragment implements View.OnClickListener {

    //widgets
    TextView tvTitle;
    ImageView ivItemImg;
    EditText etItemDescription;
    Button plusButtom;
    Button minusButton;
    EditText etItemQty;

    //vars
    Item item;
    String title;
    String description;
    int qty;

    //ViewModel
    MakeOrderViewModel mViewModel;

    public ItemView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_order_item_view, container, false);

        //get the viewmodel and selected item info
        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);
        item = (Item) mViewModel.getItemSelected();
        title = item.getName();
        description = item.getDescription();
        qty = item.getQuantity();

        //set  widgets

        tvTitle = view.findViewById(R.id.item_name);
        etItemDescription = view.findViewById(R.id.item_desc);
        etItemQty = view.findViewById(R.id.item_qty);
        tvTitle.setText(title);
        etItemDescription.setText(description);
        etItemQty.setText(Integer.toString(qty));

        //buttons
        plusButtom = view.findViewById(R.id.plus_btn);
        minusButton = view.findViewById(R.id.minus_btn);
        plusButtom.setOnClickListener(this);
        minusButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.plus_btn:{
                mViewModel.incrementItemQty();
                etItemQty.setText(Integer.toString(mViewModel.getItemSelected().getQuantity()));
                mViewModel.getItemSelected();
                break;
            }
            case R.id.minus_btn:{
                mViewModel.decreaseItemQty();
                etItemQty.setText(Integer.toString(mViewModel.getItemSelected().getQuantity()));
                break;
            }

        }
    }
}
