import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Check if the correct number of arguments is provided
        if (args.length != 6) {
            System.err.println(
                    "Usage: java Main <airports-csv> <directions-csv> <weather-csv> <missions-in> <task1-out> <task2-out>");
            System.exit(1);
        }

        // Parse command line arguments
        String airportsCsv = args[0];
        String directionsCsv = args[1];
        String weatherCsv = args[2];
        String missionsIn = args[3];
        String task1Out = args[4];
        String task2Out = args[5];

        // Reading csv files
        List<Airport> airports = Reading_CSV.readAirportsFromCsv(airportsCsv);
        List<Direction> directions = Reading_CSV.readDirectionsFromCsv(directionsCsv);
        List<Weather> weatherList = Reading_CSV.readWeatherFromCsv(weatherCsv);
        List<MissionInput> missionInputs = Reading_CSV.readMissionInputs(missionsIn);

        // Solve Task 1
        List<MissionResult> task1Results = solveTask1(airports, directions, missionInputs, weatherList);

        // Write Task 1 results to file
        writeTask1ResultsToFile(task1Results, task1Out);

        // Solve Task 2
        List<MissionResult> task2Results = solveTask2(airports, directions, weatherList, missionInputs);

        // Write Task 2 results to file
        writeTask2ResultsToFile(task2Results, task2Out);
        System.out.println("Output is stored in ./cases_v2/outputs");
    }
// Method for solvetask1
    static List<MissionResult> solveTask1(List<Airport> airports, List<Direction> directions,
            List<MissionInput> missionsIn, List<Weather> weatherList) {
        List<MissionResult> results = new ArrayList<>();

        // Iterate through each mission in the input list
        for (MissionInput mission : missionsIn) {
            String planeModel = mission.getPlaneModel();
            // Iterate through each mission detail in the mission input
            for (MissionDetails missionDetails : mission.getMissionDetailsList()) {
                String originAirportCode = missionDetails.getAirportOrigin();
                String destinationAirportCode = missionDetails.getAirportDestination();
                String intermediateAirportCode = null;
                double flightCost;

                // Check if the flight is possible based on directions
                boolean isFlightPossible = isFlightPossible(directions, originAirportCode, destinationAirportCode);

                // Find the corresponding airports for the mission
                Airport originAirport = findAirportByCode(airports, originAirportCode);
                Airport destinationAirport = findAirportByCode(airports, destinationAirportCode);

                // If the flight is not directly possible, find an alternative intermediate
                // airport
                if (!isFlightPossible) {
                    intermediateAirportCode = findAlternativeIntermediateAirport(directions, originAirportCode,
                            destinationAirportCode);
                }
                Airport intermediateAirport = findAirportByCode(airports, intermediateAirportCode);

                // Check if both airports are found
                if (originAirport != null && destinationAirport != null) {
                    // Calculate distance between airports using the Haversine formula
                    double distance;
                    if (intermediateAirportCode == null) {
                        // If there's a direct flight, calculate distance accordingly
                        distance = Distance.calculateDistance(originAirport, destinationAirport);

                    } else {
                        // If an intermediate airport is present, calculate distance for the two
                        // segments
                        double d1 = Distance.calculateDistance(originAirport, intermediateAirport);
                        double d2 = Distance.calculateDistance(intermediateAirport, destinationAirport);
                        distance = d1 + d2;
                    }

                    // Select a subsidiary based on the flight requirements
                    LoungeSubsidiary subsidiary = Distance.determineSubsidiary(distance);

                    // Calculate flight duration using the selected aircraft
                    double flightDuration = Distance.calculateFlightDuration(subsidiary, distance);

                    // Get weather codes for departure, intermediate, and landing airports
                    int departWeatherCode = getWeatherCodeForAirport(originAirport, weatherList);
                    int landWeatherCode = getWeatherCodeForAirport(destinationAirport, weatherList);
                    int interweatherCode = getWeatherCodeForAirport(intermediateAirport, weatherList);

                    // Calculate weather multipliers for departure, intermediate, and landing
                    double departWeather = WeatherUtil.calculateWeatherMultiplier(departWeatherCode);
                    double interweather = WeatherUtil.calculateWeatherMultiplier(interweatherCode);
                    double landWeather = WeatherUtil.calculateWeatherMultiplier(landWeatherCode);

                    // Calculate flight cost
                    if (!isFlightPossible) {
                        double flightCost1 = calculateFlightCost(distance, departWeather, interweather);
                        double flightCost2 = calculateFlightCost(distance, interweather, landWeather);
                        flightCost = flightCost1 + flightCost2;
                    } else {
                        flightCost = calculateFlightCost(distance, departWeather, landWeather);
                    }

                    // Calculate total cost (no parking operations for Task 1)
                    double totalCost = flightCost;

                    // Create MissionResult and add to the results list
                    List<String> airportSequence;
                    if (intermediateAirportCode != null) {
                        // If there's an intermediate airport, include it in the sequence
                        airportSequence = List.of(originAirportCode, intermediateAirportCode, destinationAirportCode);
                    } else {
                        // If it's a direct flight, the sequence is just the origin and destination
                        airportSequence = List.of(originAirportCode, destinationAirportCode);
                    }

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

// method to write result to file of task1
static void writeTask1ResultsToFile(List<MissionResult> results, String task1Out) {
    // Construct the file path for Task 1 output
    String path = "./cases_v2/outputs/" + task1Out + ".out";
    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
        // Iterate through each MissionResult and write the result string to the file
        for (MissionResult result : results) {
            writer.write(result.toString());
            writer.newLine(); // Move to the next line for the next result
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


// Method for solvetask1
static List<MissionResult> solveTask2(List<Airport> airports, List<Direction> directions,
List<Weather> weatherList, List<MissionInput> missionsIn) {
List<MissionResult> results = new ArrayList<>();

for (MissionInput mission : missionsIn) {
String planeModel = mission.getPlaneModel();
for (MissionDetails missionDetails : mission.getMissionDetailsList()) {
    String originAirportCode = missionDetails.getAirportOrigin();
    String destinationAirportCode = missionDetails.getAirportDestination();
    String intermediateAirportCode = null;
    double flightCost;
    double parkingCost;

    // Check if the flight is possible based on directions
    boolean isFlightPossible = isFlightPossible(directions, originAirportCode, destinationAirportCode);

    // Find the corresponding airports for the mission
    Airport originAirport = findAirportByCode(airports, originAirportCode);
    Airport destinationAirport = findAirportByCode(airports, destinationAirportCode);
    Airport intermediateAirport = findAirportByCode(airports, intermediateAirportCode);

    if (!isFlightPossible) {
        intermediateAirportCode = findAlternativeIntermediateAirport(directions, originAirportCode,
                destinationAirportCode);
    }

    // Check if both airports are found
    if (originAirport != null && destinationAirport != null) {
        // Calculate distance between airports using the Haversine formula
        double distance;
        if (intermediateAirportCode == null) {
            // If there's a direct flight, calculate distance accordingly
            distance = Distance.calculateDistance(originAirport, destinationAirport);
            parkingCost = calculateParkingCost(destinationAirport, 12);
        } else {
            // If an intermediate airport is present, calculate distance for the two segments
            intermediateAirport = findAirportByCode(airports, intermediateAirportCode);
            double d1 = Distance.calculateDistance(originAirport, intermediateAirport);
            double d2 = Distance.calculateDistance(intermediateAirport, destinationAirport);
            parkingCost = calculateParkingCost(intermediateAirport, 18) + calculateParkingCost(destinationAirport, 12);
            distance = d1 + d2;
        }

        // Select a subsidiary based on the flight requirements
        LoungeSubsidiary subsidiary = Distance.determineSubsidiary(distance);

        // Calculate flight duration using the selected aircraft
        double flightDuration = Distance.calculateFlightDuration(subsidiary, distance);

        // Calculate weather multipliers for departure, intermediate, and landing
        int departWeatherCode = getWeatherCodeForAirport(originAirport, weatherList);
        int interWeatherCode = getWeatherCodeForAirport(intermediateAirport, weatherList);
        int landWeatherCode = getWeatherCodeForAirport(destinationAirport, weatherList);
        double departWeather = WeatherUtil.calculateWeatherMultiplier(departWeatherCode);
        double interWeather = WeatherUtil.calculateWeatherMultiplier(interWeatherCode);
        double landWeather = WeatherUtil.calculateWeatherMultiplier(landWeatherCode);

        // Calculate flight cost
        if (!isFlightPossible) {
            double flightCost1 = calculateFlightCost(distance, departWeather, interWeather);
            double flightCost2 = calculateFlightCost(distance, interWeather, landWeather);
            flightCost = flightCost1 + flightCost2;
        } else {
            flightCost = calculateFlightCost(distance, departWeather, landWeather);
        }
        flightCost = flightCost + parkingCost;

        // Handle PARK operations
        List<String> parkOperations = new ArrayList<>();
        int numberOfParkOperations = 3; // Example value, adjust based on requirements
        if (intermediateAirportCode != null) {
            // If there's an intermediate airport, include PARK operations in the sequence
            for (int i = 1; i <= numberOfParkOperations; i++) {
                parkOperations.add("PARK");
            }
            // Create MissionResult and add to the results list
            MissionResult missionResult = new MissionResult(
                    List.of(originAirportCode, intermediateAirportCode, destinationAirportCode),
                    parkOperations,
                    flightCost);
            results.add(missionResult);
        } else {
            // If there's no intermediate airport, handle direct flight
            List<String> airportSequence = List.of(originAirportCode, destinationAirportCode);
            MissionResult missionResult = new MissionResult(airportSequence, new ArrayList<>(), flightCost);
            results.add(missionResult);
        }
    } else {
        // Handle case where one or both airports are not found
        System.out.println("Error: Airport not found for mission - " + mission);
    }
}
}

return results;
}

// method to write result to file
static void writeTask2ResultsToFile(List<MissionResult> results, String task2Out) {
    // Define the path for the output file
    String path = "./cases_v2/outputs/" + task2Out + ".out";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
        // Iterate through each MissionResult and write its string representation to the file
        for (MissionResult result : results) {
            writer.write(result.toString());
            writer.newLine(); // Move to the next line for the next result
        }
    } catch (IOException e) {
        // Print the stack trace in case of an exception during file writing
        e.printStackTrace();
    }
}


    // -------------------------Helper Methods--------------------------------------

    // Helper method to find an airport by its code in task1
    private static Airport findAirportByCode(List<Airport> airports, String airportCode) {
        // Iterate through the list of airports to find a match based on the airport code
        for (Airport airport : airports) {
            if (airport.getAirportCode().equals(airportCode)) {
                return airport; // Return the matching Airport object
            }
        }
        return null; // Return null if no match is found
    }

    // Helper method to calculate flight cost
    private static double calculateFlightCost(double distance, double weatherMultiplierDeparture,
        double weatherMultiplierLanding) {
    // Implement the flight cost calculation formula
    double cf = 300 * weatherMultiplierDeparture * weatherMultiplierLanding + distance;
    return cf;
}

    // Helper method to get the weather code for an airport from the weather list
    private static int getWeatherCodeForAirport(Airport airport, List<Weather> weatherlist) {
        if (airport == null) {
            // Handle case where airport is null (return a default value or throw an
            // exception)
            // For example, you can return a default weather code like 0:
            return 0;
        }
    
        return weatherlist.stream()
                .filter(weather -> airport.getAirfieldName().equals(weather.getAirfieldName()))
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
    private static boolean isFlightPossible(List<Direction> directions, String originAirportCode,
            String destinationAirportCode) {
        // Check if the origin and destination airports exist in the list of directions
        return directions.stream()
                .anyMatch(direction -> direction.getFromAirport().equals(originAirportCode)
                        && direction.getToAirport().equals(destinationAirportCode));
    }

    // Helper method to find the intermediate airport
    private static String findAlternativeIntermediateAirport(List<Direction> directions, String originAirportCode,
            String destinationAirportCode) {
        // Check if there's a direct flight path
        if (isFlightPossible(directions, originAirportCode, destinationAirportCode)) {
            return null; // No need for an intermediate airport
        }

        // Find an alternative intermediate airport dynamically
        for (Direction direction : directions) {
            String fromAirport = direction.getFromAirport();
            String toAirport = direction.getToAirport();

            // Check if the direction can be part of the route
            if (fromAirport.equals(originAirportCode) || toAirport.equals(destinationAirportCode)) {
                return (fromAirport.equals(originAirportCode)) ? toAirport : fromAirport;
            }
        }

        return null; // Unable to find a suitable intermediate airport
    }

}
