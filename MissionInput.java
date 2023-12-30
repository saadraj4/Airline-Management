import java.util.*;

public class MissionInput {
    private String planeModel;
    private List<MissionDetails> missionDetailsList;

    // Constructor to initialize MissionInput with plane model and a list of mission details
    public MissionInput(String planeModel, List<MissionDetails> missionDetailsList) {
        this.planeModel = planeModel;
        this.missionDetailsList = missionDetailsList;
    }

    // Getter method to retrieve the plane model
    public String getPlaneModel() {
        return planeModel;
    }

    // Getter method to retrieve the list of mission details
    public List<MissionDetails> getMissionDetailsList() {
        return missionDetailsList;
    }

    // Override toString method to provide a string representation of the MissionInput object
    @Override
    public String toString() {
        return "MissionInput{" +
                "planeModel='" + planeModel + '\'' +
                ", missionDetailsList=" + missionDetailsList +
                '}';
    }
}

// MissionDetails class to get the detail of the mission
class MissionDetails {
    private String airportOrigin;
    private String airportDestination;
    private long timeOrigin;
    private long deadline;

    // Constructor to initialize MissionDetails with origin airport, destination airport, time origin, and deadline
    public MissionDetails(String airportOrigin, String airportDestination, long timeOrigin, long deadline) {
        this.airportOrigin = airportOrigin;
        this.airportDestination = airportDestination;
        this.timeOrigin = timeOrigin;
        this.deadline = deadline;
    }

    // Getter method to retrieve the origin airport
    public String getAirportOrigin() {
        return airportOrigin;
    }

    // Getter method to retrieve the destination airport
    public String getAirportDestination() {
        return airportDestination;
    }

    // Getter method to retrieve the time of origin
    public long getTimeOrigin() {
        return timeOrigin;
    }

    // Getter method to retrieve the deadline
    public long getDeadline() {
        return deadline;
    }

    // Override toString method to provide a string representation of the MissionDetails object
    @Override
    public String toString() {
        return "MissionDetails{" +
                "airportOrigin='" + airportOrigin + '\'' +
                ", airportDestination='" + airportDestination + '\'' +
                ", timeOrigin=" + timeOrigin +
                ", deadline=" + deadline +
                '}';
    }
}
