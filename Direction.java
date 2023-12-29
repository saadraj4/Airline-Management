public class Direction {
    private String fromAirport;
    private String toAirport;
    private String intermediateAirportCode;

    public Direction(String fromAirport, String toAirport) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public String getIntermediateAirportCode() {
        return intermediateAirportCode;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "fromAirport='" + fromAirport + '\'' +
                ", toAirport='" + toAirport + '\'' +
                '}';
    }
}