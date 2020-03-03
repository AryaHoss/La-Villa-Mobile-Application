package edu.csulb.cecs.lavilla.ui.makeorder.Data;

import java.util.ArrayList;

public class Locations {
    public class Location{
        private int locationId;
        private String street;
        private String city;
        private int zip;

        public Location() {
        }

        public Location(int locationId, String street, String city, int zip) {
            this.locationId = locationId;
            this.street = street;
            this.city = city;
            this.zip = zip;
        }

        public int getLocationId() {
            return locationId;
        }

        public String getStreet() {
            return street;
        }

        public String getCity() {
            return city;
        }

        public int getZip() {
            return zip;
        }
    }

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
