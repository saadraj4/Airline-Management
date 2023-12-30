import java.util.*;
import java.io.*;

public class Reading_CSV {

    // Reads airport data from a CSV file and returns a list of Airport objects
    static List<Airport> readAirportsFromCsv(String airportsCsv) {
        List<Airport> airports = new ArrayList<>();
        String path = "./cases_v2/airports/" + airportsCsv + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String airportCode = parts[0];
                String airfieldName = parts[1];
                double latitude = Double.parseDouble(parts[2]);
                double longitude = Double.parseDouble(parts[3]);
                double parkingCost = Double.parseDouble(parts[4]);

                Airport airport = new Airport(airportCode, airfieldName, latitude, longitude, parkingCost);
                airports.add(airport);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return airports;
    }

    // Reads direction data from a CSV file and returns a list of Direction objects
    static List<Direction> readDirectionsFromCsv(String directionsCsv) {
        List<Direction> directions = new ArrayList<>();
        String path = "./cases_v2/directions/" + directionsCsv + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String fromAirport = parts[0].trim();
                String toAirport = parts[1].trim();

                Direction direction = new Direction(fromAirport, toAirport);
                directions.add(direction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return directions;
    }

    // Reads weather data from a CSV file and returns a list of Weather objects
    static List<Weather> readWeatherFromCsv(String weatherCsv) {
        List<Weather> weatherList = new ArrayList<>();
        String path = "./cases_v2/" + weatherCsv + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String airfieldName = parts[0].trim();
                long time = Long.parseLong(parts[1].trim());
                int weatherCode = Integer.parseInt(parts[2].trim());

                Weather weather = new Weather(airfieldName, time, weatherCode);
                weatherList.add(weather);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return weatherList;
    }


    // Reads mission input data from a CSV file and returns a list of MissionInput objects
    static List<MissionInput> readMissionInputs(String missionsIn) {
        String path = "./cases_v2/missions/" + missionsIn + ".in";  // Corrected path
        List<MissionInput> missionInputs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String planeModel = br.readLine().trim();
            List<MissionDetails> missionDetailsList = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String airportOrigin = parts[0].trim();
                String airportDestination = parts[1].trim();
                long timeOrigin = Long.parseLong(parts[2].trim());
                long deadline = Long.parseLong(parts[3].trim());

                MissionDetails missionDetails = new MissionDetails(airportOrigin, airportDestination, timeOrigin, deadline);
                missionDetailsList.add(missionDetails);
            }

            missionInputs.add(new MissionInput(planeModel, missionDetailsList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return missionInputs;
    }
}
