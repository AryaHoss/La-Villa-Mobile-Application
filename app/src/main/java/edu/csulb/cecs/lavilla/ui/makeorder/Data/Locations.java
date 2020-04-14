package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import java.util.ArrayList;

public class Locations {

    private ArrayList<Location> locations;
    public Locations(){
        locations = new ArrayList<>();
    }

    public void addLocation(Location l){
        locations.add(l);
    }



    public ArrayList<Location> getLocations() {
        return locations;
    }


}
