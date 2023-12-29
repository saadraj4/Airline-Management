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

    public String getAirportCode() {
        return airportCode;
    }

    public String getAirfieldName() {
        return airfieldName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getParkingCost() {
        return parkingCost;
    }

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
