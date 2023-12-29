import java.util.*;

public class MissionInput {
    private String planeModel;
    private List<MissionDetails> missionDetailsList;

    public MissionInput(String planeModel, List<MissionDetails> missionDetailsList) {
        this.planeModel = planeModel;
        this.missionDetailsList = missionDetailsList;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public List<MissionDetails> getMissionDetailsList() {
        return missionDetailsList;
    }

    @Override
    public String toString() {
        return "MissionInput{" +
                "planeModel='" + planeModel + '\'' +
                ", missionDetailsList=" + missionDetailsList +
                '}';
    }
}

class MissionDetails {
    private String airportOrigin;
    private String airportDestination;
    private long timeOrigin;
    private long deadline;

    public MissionDetails(String airportOrigin, String airportDestination, long timeOrigin, long deadline) {
        this.airportOrigin = airportOrigin;
        this.airportDestination = airportDestination;
        this.timeOrigin = timeOrigin;
        this.deadline = deadline;
    }

    public String getAirportOrigin() {
        return airportOrigin;
    }

    public String getAirportDestination() {
        return airportDestination;
    }

    public long getTimeOrigin() {
        return timeOrigin;
    }

    public long getDeadline() {
        return deadline;
    }

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
