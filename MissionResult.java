import java.util.List;

public class MissionResult {
    private List<String> airportSequence;
    private List<String> parkOperations;  // Representing park durations in hours or "PARK" operations
    private double totalCost;

    // Constructor to initialize MissionResult object with provided parameters
    public MissionResult(List<String> airportSequence, List<String> parkOperations, double totalCost) {
        this.airportSequence = airportSequence;
        this.parkOperations = parkOperations;
        this.totalCost = totalCost;
    }

    // Getter method to retrieve the sequence of airports visited during the mission
    public List<String> getAirportSequence() {
        return airportSequence;
    }

    // Getter method to retrieve the list of park operations
    public List<String> getParkOperations() {
        return parkOperations;
    }

    // Getter method to retrieve the total cost incurred during the mission
    public double getTotalCost() {
        return totalCost;
    }

    // Override toString method to provide a formatted string representation of the MissionResult object
    @Override
    public String toString() {
        StringBuilder resultStringBuilder = new StringBuilder();

        // Append airport sequence
        for (String airport : airportSequence) {
            resultStringBuilder.append(airport).append(" ");
        }

        // Append park operations
        for (String parkOperation : parkOperations) {
            resultStringBuilder.append(parkOperation).append(" ");
        }

        // Append total cost with formatting
        resultStringBuilder.append(String.format("%.5f", totalCost));

        return resultStringBuilder.toString().trim();
    }
}
