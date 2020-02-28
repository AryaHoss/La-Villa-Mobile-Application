package edu.csulb.cecs.lavilla.ui.makeorder;

import androidx.lifecycle.ViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.*;

public class MakeOrderViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Order order;
    private Locations locations;


    public MakeOrderViewModel(Locations locations) {
        this.locations = locations;
        order = new Order();
    }

    public void setOrderType(Order.OrderType orderType){
        order.setOrderType(orderType);
    }
}
