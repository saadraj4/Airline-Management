public class Distance {
    static double calculateDistance(Airport airport1, Airport airport2) {
        // Implementation of Haversine formula
        double lat1 = Math.toRadians(airport1.getLatitude());
        double lon1 = Math.toRadians(airport1.getLongitude());
        double lat2 = Math.toRadians(airport2.getLatitude());
        double lon2 = Math.toRadians(airport2.getLongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of the Earth in kilometers
        double r = 6371.0;

        // Calculate the distance using Haversine formula
        double distance = c * r;

        // Determine the subsidiary based on the distance and calculate flight duration
        LoungeSubsidiary subsidiary = determineSubsidiary(distance);
        double flightDuration = calculateFlightDuration(subsidiary, distance);

        return distance;
    }

    // Helper method to determine the subsidiary based on the distance
    static LoungeSubsidiary determineSubsidiary(double distance) {
        if (distance <= 175) {
            return LoungeSubsidiary.LOUNGE_TURKEY;
        } else if (distance <= 1500) {
            return LoungeSubsidiary.LOUNGE_CONTINENTAL_ASIA;
        } else if (distance <= 5000) {
            return LoungeSubsidiary.LOUNGE_INTERNATIONAL;
        } else {
            // Handle distances beyond the range of the provided subsidiaries
            return null;
        }
    }

    // Helper method to calculate flight duration based on the subsidiary and distance
    static double calculateFlightDuration(LoungeSubsidiary subsidiary, double distance) {
        if (subsidiary != null) {
            switch (subsidiary) {
                case LOUNGE_TURKEY:
                    return calculateCarreidas160FlightDuration(distance);
                case LOUNGE_CONTINENTAL_ASIA:
                    return calculateOrionIIIFlightDuration(distance);
                case LOUNGE_CONTINENTAL_EUROPE:
                    return calculateSkyfleetS570FlightDuration(distance);
                case LOUNGE_INTERNATIONAL:
                    return calculateT16SkyhopperFlightDuration(distance);
                default:
                    // Handle unknown subsidiaries
                    return 0.0;
            }
        } else {
            // Handle null subsidiary
            return 0.0;
        }
    }

    // Helper method to calculate flight duration for Carreidas 160
    private static double calculateCarreidas160FlightDuration(double distance) {
        if (distance <= 175) {
            return 6.0;
        } else if (distance <= 350) {
            return 12.0;
        } else {
            return 18.0;
        }
    }

    // Helper method to calculate flight duration for Orion III
    private static double calculateOrionIIIFlightDuration(double distance) {
        if (distance <= 1500) {
            return 6.0;
        } else if (distance <= 3000) {
            return 12.0;
        } else {
            return 18.0;
        }
    }

    // Helper method to calculate flight duration for Skyfleet S570
    private static double calculateSkyfleetS570FlightDuration(double distance) {
        if (distance <= 500) {
            return 6.0;
        } else if (distance <= 1000) {
            return 12.0;
        } else {
            return 18.0;
        }
    }

    // Helper method to calculate flight duration for T-16 Skyhopper
    private static double calculateT16SkyhopperFlightDuration(double distance) {
        if (distance <= 2500) {
            return 6.0;
        } else if (distance <= 5000) {
            return 12.0;
        } else {
            return 18.0;
        }
    }

}
