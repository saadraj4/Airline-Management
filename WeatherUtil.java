public class WeatherUtil {

    public static double calculateWeatherMultiplier(int weatherCode) {
        // Convert the weatherCode to binary representation
        String binaryRepresentation = Integer.toBinaryString(weatherCode);

        // Ensure the binary representation has 5 digits, padding with leading zeros if necessary
        binaryRepresentation = String.format("%5s", binaryRepresentation).replace(' ', '0');

        // Extract individual bits representing different weather conditions
        int Bw = Integer.parseInt(binaryRepresentation.substring(0, 1));
        int Br = Integer.parseInt(binaryRepresentation.substring(1, 2));
        int Bs = Integer.parseInt(binaryRepresentation.substring(2, 3));
        int Bh = Integer.parseInt(binaryRepresentation.substring(3, 4));
        int Bb = Integer.parseInt(binaryRepresentation.substring(4, 5));

        // Calculate the weather multiplier (W) based on the individual bits
        double W = (Bw * 1.05 + (1 - Bw)) *
                   (Br * 1.05 + (1 - Br)) *
                   (Bs * 1.10 + (1 - Bs)) *
                   (Bh * 1.15 + (1 - Bh)) *
                   (Bb * 1.20 + (1 - Bb));

        return W;
    }
}
