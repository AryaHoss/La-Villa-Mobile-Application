package edu.csulb.cecs.lavilla.ui.makeorder;

import androidx.lifecycle.ViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.*;

import java.util.ArrayList;

public class MakeOrderViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Order order;

    public MakeOrderViewModel() {
        order = new Order();
    }

    public void setOrderType(Order.OrderType orderType){
        order.setOrderType(orderType);
    }
}
