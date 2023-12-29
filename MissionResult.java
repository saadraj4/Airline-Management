import java.util.List;

public class MissionResult {
    private List<String> airportSequence;
    private List<String> parkOperations;  // Representing park durations in hours or "PARK" operations
    private double totalCost;

    public MissionResult(List<String> airportSequence, List<String> parkOperations, double totalCost) {
        this.airportSequence = airportSequence;
        this.parkOperations = parkOperations;
        this.totalCost = totalCost;
    }

    public List<String> getAirportSequence() {
        return airportSequence;
    }

    public List<String> getParkOperations() {
        return parkOperations;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        StringBuilder resultStringBuilder = new StringBuilder();

        for (String airport : airportSequence) {
            resultStringBuilder.append(airport).append(" ");
        }

        for (String parkOperation : parkOperations) {
            resultStringBuilder.append(parkOperation).append(" ");
        }

        resultStringBuilder.append(String.format("%.5f", totalCost));

        return resultStringBuilder.toString().trim();
    }
}
