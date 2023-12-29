import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        // Check if the correct number of arguments is provided
        if (args.length != 6) {
            System.err.println("Usage: java Main <airports-csv> <directions-csv> <weather-csv> <missions-in> <task1-out> <task2-out>");
            System.exit(1);
        }

        // Parse command line arguments-------------------------
        String airportsCsv = args[0];
        String directionsCsv = args[1];
        String weatherCsv = args[2];
        String missionsIn = args[3];
        String task1Out = args[4];
        String task2Out = args[5];

        // Reading csv files----------------------------------------
        List<Airport> airports = Reading_CSV.readAirportsFromCsv(airportsCsv);
        List<Direction> directions = Reading_CSV.readDirectionsFromCsv(directionsCsv);
        List<Weather> weatherList = Reading_CSV.readWeatherFromCsv(weatherCsv);
        List<MissionInput> missionInputs = Reading_CSV.readMissionInputs(missionsIn);


        List<MissionResult> task1Results = solveTask1(airports, directions, missionInputs,weatherList);

        writeTask1ResultsToFile(task1Results, task1Out);

        List<MissionResult> task2Results = solveTask2(airports, directions, weatherList, missionInputs);

        writeTask2ResultsToFile(task2Results, task2Out);
        System.out.println("Output is stored in ./cases_v2/outputs");
    }

    static List<MissionResult> solveTask1(List<Airport> airports, List<Direction> directions, List<MissionInput> missionsIn,List<Weather> weatherlist) {
        List<MissionResult> results = new ArrayList<>();

        for (MissionInput mission : missionsIn) {
            String planeModel = mission.getPlaneModel();
            for (MissionDetails missionDetails : mission.getMissionDetailsList()) {
                String originAirportCode = missionDetails.getAirportOrigin();
                String destinationAirportCode = missionDetails.getAirportDestination();

                // Find the corresponding airports for the mission
                Airport originAirport = findAirportByCode(airports, originAirportCode);
                Airport destinationAirport = findAirportByCode(airports, destinationAirportCode);

                // Check if both airports are found
                if (originAirport != null && destinationAirport != null) {
                    // Calculate distance between airports using the Haversine formula
                    double distance = Distance.calculateDistance(originAirport, destinationAirport);

                    // Select a subsidiary based on the flight requirements
                    LoungeSubsidiary subsidiary = Distance.determineSubsidiary(distance);;

                    // Calculate flight duration using the selected aircraft
                    double flightDuration = Distance.calculateFlightDuration(subsidiary, distance);

                    int departweatherCode = getWeatherCodeForAirport(originAirport, weatherlist);
                    int landweatherCode = getWeatherCodeForAirport(destinationAirport, weatherlist);
                    double departweather = WeatherUtil.calculateWeatherMultiplier(departweatherCode);
                    double landweather = WeatherUtil.calculateWeatherMultiplier(landweatherCode);
                    // Calculate flight cost
                    double flightCost = calculateFlightCost(distance, departweather, landweather); // Assuming no weather effect for Task 1

                    // Calculate total cost (no parking operations for Task 1)
                    double totalCost = flightCost;

                    // Create MissionResult and add to the results list
                    List<String> airportSequence = List.of(originAirportCode, destinationAirportCode);
                    List<String> parkOperations = List.of(); // No parking operations for Task 1
                    MissionResult missionResult = new MissionResult(airportSequence, parkOperations, totalCost);
                    results.add(missionResult);
                } else {
                    // Handle case where one or both airports are not found
                    System.out.println("Error: Airport not found for mission - " + mission);
                }
            }
        }

        return results;
    }

    static void writeTask1ResultsToFile(List<MissionResult> results, String task1Out) {
        String path = "./cases_v2/outputs/"+task1Out+".out";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (MissionResult result : results) {
                // Write the result string to the file
                writer.write(result.toString());
                writer.newLine();  // Move to the next line for the next result
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<MissionResult> solveTask2(List<Airport> airports, List<Direction> directions, List<Weather> weatherlist, List<MissionInput> missionsIn) {
        List<MissionResult> results = new ArrayList<>();


        for (MissionInput mission : missionsIn) {
            String planeModel = mission.getPlaneModel();
            for (MissionDetails missionDetails : mission.getMissionDetailsList()) {
                String originAirportCode = missionDetails.getAirportOrigin();
                String destinationAirportCode = missionDetails.getAirportDestination();

                // Check if the flight is possible based on directions
                boolean isFlightPossible = isFlightPossible(directions, originAirportCode, destinationAirportCode);

                // Find the corresponding airports for the mission
                Airport originAirport = findAirportByCode(airports, originAirportCode);
                Airport destinationAirport = findAirportByCode(airports, destinationAirportCode);
                if (isFlightPossible){
                }
                else
                {
                    // Check if both airports are found
                    if (originAirport != null && destinationAirport != null) {
                        // Calculate distance between airports using the Haversine formula
                        double distance = Distance.calculateDistance(originAirport, destinationAirport);

                        // Select a subsidiary based on the flight requirements
                        LoungeSubsidiary subsidiary = Distance.determineSubsidiary(distance); // Adjust this based on your logic

                        // Calculate flight duration using the selected aircraft
                        double flightDuration = Distance.calculateFlightDuration(subsidiary, distance);

                        // Calculate weather multipliers for departure and landing
                        int departweatherCode = getWeatherCodeForAirport(originAirport, weatherlist);
                        int landweatherCode = getWeatherCodeForAirport(destinationAirport, weatherlist);
                        double departWeather = WeatherUtil.calculateWeatherMultiplier(departweatherCode);
                        double landWeather = WeatherUtil.calculateWeatherMultiplier(landweatherCode);

                        // Calculate flight cost
                        double flightCost = calculateFlightCost(distance, departWeather, landWeather);

                        // Calculate parking cost (for simplicity, assume 6 hours of parking for every PARK operation)
                        double parkingCost = calculateParkingCost(destinationAirport, 6);

                        // Calculate total cost (considering flight and parking costs)
                        double totalCost = flightCost + parkingCost;

                        // Create MissionResult and add to the results list
                        List<String> airportSequence = List.of(originAirportCode, destinationAirportCode);
                        List<String> parkOperations = List.of("PARK"); // For Task 2, assuming a PARK operation
                        MissionResult missionResult = new MissionResult(airportSequence, parkOperations, totalCost);
                        results.add(missionResult);
                    } else {
                        // Handle case where one or both airports are not found
                        System.out.println("Error: Airport not found for mission - " + mission);
                    }
                }
            }
        }

        return results;
    }

    static void writeTask2ResultsToFile(List<MissionResult> results, String task2Out) {
        String path = "./cases_v2/outputs/"+task2Out+".out";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (MissionResult result : results) {
                // Write the result string to the file
                writer.write(result.toString());
                writer.newLine();  // Move to the next line for the next result
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Helper method to find an airport by its code in task1
    private static Airport findAirportByCode(List<Airport> airports, String airportCode) {
        for (Airport airport : airports) {
            if (airport.getAirportCode().equals(airportCode)) {
                return airport;
            }
        }
        return null;
    }
    // Helper method to calculate flight cost
    private static double calculateFlightCost(double distance, double weatherMultiplierDeparture, double weatherMultiplierLanding) {
        // Implement the flight cost calculation formula
        double cf = 300 * weatherMultiplierDeparture * weatherMultiplierLanding + distance;
        return cf;
    }
    // Helper method to get the weather code for an airport from the weather list
    private static int getWeatherCodeForAirport(Airport airport, List<Weather> weatherlist) {
        return weatherlist.stream()
                .filter(weather -> weather.getAirfieldName().equals(airport.getAirfieldName()))
                .map(Weather::getWeatherCode)
                .findFirst()
                .orElse(0); // Default weather code if not found
    }
// Helper method to get the parking cost
private static double calculateParkingCost(Airport airport, int totalParkingDuration) {
    double parkingCostPerHour = airport.getParkingCost(); // Assuming getParkingCost() is a method in Airport class
    int standardParkingDuration = 6; // Assuming 6 hours of parking for each operation

    // Calculate the number of standard parking operations
    int standardParkingOperations = totalParkingDuration / standardParkingDuration;

    // Calculate the remaining duration after standard parking operations
    int remainingParkingDuration = totalParkingDuration % standardParkingDuration;

    // Calculate the total parking cost
    double totalParkingCost = (standardParkingOperations * parkingCostPerHour * standardParkingDuration)
            + (remainingParkingDuration * parkingCostPerHour);

    return totalParkingCost;
}
// Helper functio to get the possible flights
private static boolean isFlightPossible(List<Direction> directions, String originAirportCode, String destinationAirportCode) {
    // Check if the origin and destination airports exist in the list of directions
    return directions.stream()
            .anyMatch(direction -> direction.getFromAirport().equals(originAirportCode)
                    && direction.getToAirport().equals(destinationAirportCode));
}

}
