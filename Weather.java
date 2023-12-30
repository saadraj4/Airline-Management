public class Weather {
    // Name of the airfield
    private String airfieldName;
    // Time of the weather data
    private long time;
    // Code representing the weather
    private int weatherCode;

    // Constructor for the Weather class
    public Weather(String airfieldName, long time, int weatherCode) {
        this.airfieldName = airfieldName;
        this.time = time;
        this.weatherCode = weatherCode;
    }

    // Method to convert the Weather object to a string
    @Override
    public String toString() {
        return "Weather{" +
                "airfieldName='" + airfieldName + '\'' +
                ", time=" + time +
                ", weatherCode=" + weatherCode +
                '}';
    }

    // Getter for the airfield name
    public String getAirfieldName() {
        return airfieldName;
    }

    // Getter for the time
    public long getTime() {
        return time;
    }

    // Getter for the weather code
    public int getWeatherCode() {
        return weatherCode;
    }
}
