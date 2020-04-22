package edu.csulb.cecs.lavilla.ui.makeorder;

        import android.util.Log;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.ViewModel;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import edu.csulb.cecs.lavilla.LocationDataHandler;
        import edu.csulb.cecs.lavilla.ui.makeorder.Data.*;

        import static androidx.constraintlayout.widget.Constraints.TAG;

public class MakeOrderViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    DatabaseReference firebaseRootRef, MenuRef;
    ArrayList<Item> MenuList;
    ValueEventListener valueEventListener;

    private Order order;
    private Locations locations;
    private Item itemSelected;

    public MakeOrderViewModel() {
        this.locations = locations;
        order = new Order();
    }

    public MakeOrderViewModel(Locations locations) {
        this.locations = locations;
        order = new Order();
    }

    public void setOrderAddress(Address address){
        order.setAddress(address);
    }
    public Order getOrder(){ return order; }

    public void setOrderType(Order.OrderType orderType){
        order.setOrderType(orderType);
    }

    public Order.OrderType getOrderType() { return order.getOrderType(); }

    public void setLocation (Location location){
        getOrder().setOrderLocation(location);
    }

    public Item getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(Item item){
        itemSelected = item;
    }

    public Location getLocationSelected(){
        return getOrder().getOrderLocation();
    }
    public void incrementItemQty() {
        for (Item i: getOrder().getItems().getValue()) {
            if(itemSelected.getItemId() == i.getItemId()){
                i.incrementQty();
            }
        }
    }

    public void decreaseItemQty(){
        for (Item i: getOrder().getItems().getValue()) {
            if(itemSelected.getItemId() == i.getItemId() && i.getQuantity()>0){
                i.decreaseQty();

            }else if (i.getQuantity()<=0){
                Log.d(TAG,i.getName()+" quantity is now currently "+ i.getQuantity());
            }
        }
    }

    public void getMenuItens(FirebaseCallback firebaseCallBack){
        System.out.println("FINDING ITEMS AGAIN");

        firebaseRootRef = FirebaseDatabase.getInstance().getReference();
        MenuRef = firebaseRootRef.child("Menu");
        MenuList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println("menu items:");
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Item item = ds.getValue(Item.class);

                    if(item.getValidLocations().get(getLocationSelected().getLocationId())) // check if item is available on selected location
                    {
                        MenuList.add(item);
                    }
                    System.out.println(item.getName());
                    System.out.println(item.getItemDescription());
                }
                order.setItems(MenuList);

                firebaseCallBack.onCallback(MenuList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        MenuRef.addListenerForSingleValueEvent(valueEventListener);

    }

    public void postOrder(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String locationId = getLocationSelected().getLocationId();
        HashMap<String, Integer> items = new HashMap<>();
        for (Item i : getOrder().getPickedItems()){
            items.put(Integer.toString(i.getItemId()), i.getQuantity());
        }
        float total = getOrder().getTotal();
        String status = "Submitted";

        RestaurantOrder restaurantOrder = new RestaurantOrder(userId, items,total, status);
        DatabaseReference orderReference = FirebaseDatabase.getInstance().getReference("Orders").child(locationId);
        String orderID = orderReference.push().getKey();
        orderReference.child(orderID).setValue(restaurantOrder);

        DatabaseReference userOrderReference = FirebaseDatabase.getInstance().getReference("UserOrders").child(userId);
        userOrderReference.child(orderID).setValue(restaurantOrder);

    }

    public interface FirebaseCallback {
        void onCallback(List<Item> itemList);
    }
}
