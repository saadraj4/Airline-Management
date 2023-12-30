public class Direction {
    private String fromAirport;
    private String toAirport;
    private String intermediateAirportCode;

//    Constructs a Direction object with the specified origin and destination airports.
 
    public Direction(String fromAirport, String toAirport) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
    }

    // Gets the code of the origin airport.
    
    public String getFromAirport() {
        return fromAirport;
    }

    // Gets the code of the destination airport.
    
    public String getToAirport() {
        return toAirport;
    }

    // Gets the code of an intermediate airport, if applicable.
   
    public String getIntermediateAirport() {
        return intermediateAirportCode;
    }

    // Overrides the default toString method to provide a string representation of the Direction object.
     
    @Override
    public String toString() {
        return "Direction{" +
                "fromAirport='" + fromAirport + '\'' +
                ", toAirport='" + toAirport + '\'' +
                '}';
    }
}
