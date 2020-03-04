package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import java.util.ArrayList;

public class Locations {

    private ArrayList<Location> locations;
    public Locations(){
        locations = new ArrayList<>();
        createLocations();
    }

    public void addLocation(Location l){
        locations.add(l);
    }

    public Location getLocationById(int id){
        for (Location loc: locations) {
            if(loc.getLocationId() == id)
                return loc;
        }
        return null;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    private void createLocations(){
        Location[] tempLocations =  new Location[]{
                new Location(1, "12345 Kingsdale Ave", "Redondo Beach", 90289),
                new Location(3, "3453 Maple st", "Torrance", 90222),
                new Location(2, "5422 Reseda Ave", "Reseda", 91405),
                new Location(4, "3434 Manhattan Ave", "Lawndale", 91444)
        };
        for (Location l: tempLocations ) {
            locations.add(l);

        }
    }
}
