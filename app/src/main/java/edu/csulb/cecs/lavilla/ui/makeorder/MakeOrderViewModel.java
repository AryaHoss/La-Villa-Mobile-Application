package edu.csulb.cecs.lavilla.ui.makeorder;

import androidx.lifecycle.ViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.*;

public class MakeOrderViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Order order;
    private Locations locations;

    public MakeOrderViewModel() {
        this.locations = locations;
        order = new Order();
        order.createFakeOrder();
    }

    public MakeOrderViewModel(Locations locations) {
        this.locations = locations;
        order = new Order();
    }

    public Order getOrder(){ return order; }

    public void setOrderType(Order.OrderType orderType){
        order.setOrderType(orderType);
    }

    public void setLocation (Location location){
        getOrder().setOrderLocation(location);
    }
}
