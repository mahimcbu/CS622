package PHI;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
    	/**
    	 * The main method to demonstrate the functionality of the health data tracking system.
    	 *
    	 * Pre-condition:
    	 * - Appropriate classes with constructors exists
    	 * - custom validate exception handling method exists
    	 *
    	 * Post-condition:
    	 * - Prints the health data for a user, including blood pressure, cholesterol levels, blood glucose level, BMI, and custom health data.
    	 * - Validates each health data entry before adding it to the user's health data list.
    	 * - Handles and prints any validation errors that occur during the process.
    	 */
        try {
            // Create a user with some health data
        	User user = new User("John", "Doe", "johndoe@example.com", "Passw0rd", new Date(), "Male", "555-1234");
        	// Add common health data
        	HealthData commonHealth1 = new CommonHealthData(user.getFullName(), new Date(), "Blood Pressure", 120, 60);
        	CommonHealthData commonHealthData1 = (CommonHealthData) commonHealth1;
        	commonHealthData1.validate();
        	
            user.addHealthData(commonHealth1); // Add the health data before validation
            HealthData commonHealth2 = new CommonHealthData(user.getFullName(), new Date(), "Cholesterol", 120, 60, 140);
        	CommonHealthData commonHealthData2 = (CommonHealthData) commonHealth2;
        	commonHealthData2.validate();
            user.addHealthData(commonHealth2);
            
            HealthData commonHealth3 = new CommonHealthData(user.getFullName(), new Date(), "Blood Glucose", 100);
        	CommonHealthData commonHealthData3 = (CommonHealthData) commonHealth3;
        	commonHealthData3.validate();
            user.addHealthData(commonHealth3);
            HealthData commonHealth4 = new CommonHealthData(user.getFullName(), new Date(), "BMI", 145.0, 65.0);
        	CommonHealthData commonHealthData4 = (CommonHealthData) commonHealth4;
        	commonHealthData4.validate();
            user.addHealthData(commonHealth4);
            // Add custom health data
            HealthData customHealth = new CustomHealthData("Ankle pain", new Date(), "ankle pain on 5-12-2023");
            user.addHealthData(customHealth);

            // Print out John Doe's health data
            System.out.println(user.getFullName() + "'s health data:");
            for (HealthData healthData : user.getHealthDataList()) {
                System.out.println(healthData.getMetric() + ": Recorded at: " + healthData.getDate());
                System.out.println("Metric: " + healthData.getMetric());

                if (healthData instanceof CommonHealthData) {
                    CommonHealthData commonHealthData = (CommonHealthData) healthData;

                    // Check if the health data is related to blood pressure
                    if (commonHealthData.getMetric().equals("Blood Pressure")) {
                        System.out.println("Systolic BP: " + commonHealthData.getSystolicBP());
                        System.out.println("Diastolic BP: " + commonHealthData.getDiastolicBP());
                        HealthDataChecker.checkBloodPressure(commonHealthData);
                    }

                    // Check if the health data is related to cholesterol levels
                    if (commonHealthData.getMetric().equals("Cholesterol")) {
                        System.out.println("ldl: " + commonHealthData.getLdlCholesterol());
                        System.out.println("hdl: " + commonHealthData.getHdlCholesterol());
                        System.out.println("tri: " + commonHealthData.getTriglycerideCholesterol());

                        HealthDataChecker.checkCholesterol(commonHealthData);
                    }

                    // Check if the health data is related to blood glucose level
                    if (commonHealthData.getMetric().equals("Blood Glucose")) {
                        System.out.println("blood sugar: " + commonHealthData.getGlucoseLevel());
                        HealthDataChecker.checkBloodGlucose(commonHealthData);
                    }

                    // Check if the health data is related to BMI
                    if (commonHealthData.getMetric().equals("BMI")) {
                        System.out.println("height: " + commonHealthData.getHeight());
                        System.out.println("weight: " + commonHealthData.getWeight());
                        System.out.println("bmi: " + commonHealthData.calculateBMI());
                        HealthDataChecker.checkBMI(commonHealthData);
                    }
                } else if (healthData instanceof CustomHealthData) {
                    CustomHealthData customHealthData = (CustomHealthData) healthData;
                    System.out.println("Notes: " + customHealthData.getNotes());
                }

                System.out.println();
            }
        } catch (HealthDataException e) {
            System.out.println("Health data validation error: " + e.getMessage());
        }
    }
}
