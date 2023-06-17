package application;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HealthMetricsReader {
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);
    public static double[] readIdealValue(String metric) {
        double[] idealValues = new double[2];
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\mahim\\Documents\\GitHub\\CS622\\Assignments\\Hw3\\HealthMetrics.txt"))) {
            Runnable fileReaderTask = new FileReaderTask(reader, metric);
            Future<?> future = executor.submit(fileReaderTask);
            future.get(); // Wait for the task to complete
            idealValues = ((FileReaderTask) fileReaderTask).getIdealValues();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the HealthMetrics file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while executing the file reading task: " + e.getMessage());
        }
        return idealValues;
    }
    private static class FileReaderTask implements Runnable {
        private final BufferedReader reader;
        private final String metric;
        private double[] idealValues;

        public FileReaderTask(BufferedReader reader, String metric) {
            this.reader = reader;
            this.metric = metric;
        }
        @Override
        public void run() {
            idealValues = new double[2];
            String line;
            try {
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
        }
        public double[] getIdealValues() {
            return idealValues;
        }
    }
}
