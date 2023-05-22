package PHI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HealthMetricsReader {
    /**
     * Reads the ideal values for a given metric from the HealthMetrics file.
     * 
     * @param metric the metric for which to retrieve the ideal values
     * @return an array containing the ideal values [lowerBound, upperBound]
     * @precondition the HealthMetrics file exists and is accessible
     * @postcondition the returned array contains the ideal values for the given metric,
     *       or [0.0, 0.0] if the metric is not found in the file
     */
    public static double[] readIdealValue(String metric) {
        double[] idealValues = new double[2];
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\mahim\\Documents\\GitHub\\CS622\\Assignments\\Hw2\\HealthMetrics.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3 && values[0].trim().equals(metric.trim())) {
                    idealValues[0] = Double.parseDouble(values[1].trim());
                    idealValues[1] = Double.parseDouble(values[2].trim());
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the HealthMetrics file: " + e.getMessage());
        }
        return idealValues;
    }
}

