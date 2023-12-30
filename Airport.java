public class Airport {
    private String airportCode;
    private String airfieldName;
    private double latitude;
    private double longitude;
    private double parkingCost;

    public Airport(String airportCode, String airfieldName, double latitude, double longitude, double parkingCost) {
        this.airportCode = airportCode;
        this.airfieldName = airfieldName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkingCost = parkingCost;
    }

    // Gets the code assigned to the airport.
    
    public String getAirportCode() {
        return airportCode;
    }
    // Gets the name of the airport.
     
    public String getAirfieldName() {
        return airfieldName;
    }

    // Gets the latitude coordinate of the airport.
    
    public double getLatitude() {
        return latitude;
    }

    // Gets the longitude coordinate of the airport.
    
    public double getLongitude() {
        return longitude;
    }

    // Gets the cost of parking at the airport.
    
    public double getParkingCost() {
        return parkingCost;
    }

//    Overrides the default toString method to provide a string representation of the Airport object.
     
    @Override
    public String toString() {
        return "Airport{" +
                "airportCode='" + airportCode + '\'' +
                ", airfieldName='" + airfieldName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", parkingCost=" + parkingCost +
                '}';
    }
}
