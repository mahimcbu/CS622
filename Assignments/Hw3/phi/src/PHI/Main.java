package PHI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /**
         * The main method to demonstrate the functionality of the health data tracking system.
         *
         * Pre-condition:
         * - Appropriate classes with constructors exist
         * - Custom validation exception handling method exists
         *
         * Post-condition:
         * - Prints the health data for a user, including blood pressure, cholesterol levels, blood glucose level, BMI, and custom health data.
         * - Validates each health data entry before adding it to the user's health data list.
         * - Handles and prints any validation errors that occur during the process.
         */
        try {
            // Create a user with some health data
        	// Create a Scanner object to read input from the terminal
            Scanner scanner = new Scanner(System.in);

//            // Prompt the user to enter their information
//            System.out.print("Enter your first name: ");
//            String firstName = scanner.nextLine();
//
//            System.out.print("Enter your last name: ");
//            String lastName = scanner.nextLine();
//
//            System.out.print("Enter your email: ");
//            String email = scanner.nextLine();
//
//            System.out.print("Enter your password: ");
//            String password = scanner.nextLine();
//
//            System.out.print("Enter your date of birth (yyyy-mm-dd): ");
//            String dobString = scanner.nextLine();
//            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
//
//            System.out.print("Enter your gender: ");
//            String gender = scanner.nextLine();
//
//            System.out.print("Enter your phone number: ");
//            String phoneNumber = scanner.nextLine();

            // Create a user with the provided information
//            User<HealthData<?>> user = new User<>(firstName, lastName, email, password, dob, gender, phoneNumber);
            User<HealthData<?>> user = new User<>("John", "Doe", "johndoe@example.com", "Passw0rd", new Date(), "Male", "555-1234");


            // Add common health data
            HealthData<?> commonHealth1 = new CommonHealthData(user.getFullName(), new Date(), "Blood Pressure", 120, 60);
            ((CommonHealthData) commonHealth1).validate();
            user.addHealthData(commonHealth1); // Add the health data before validation

            HealthData<?> commonHealth2 = new CommonHealthData(user.getFullName(), new Date(), "Cholesterol", -120, 60, 140);
            ((CommonHealthData) commonHealth2).validate();
            user.addHealthData(commonHealth2);

            HealthData<?> commonHealth3 = new CommonHealthData(user.getFullName(), new Date(), "Blood Glucose", 100);
            ((CommonHealthData) commonHealth3).validate();
            user.addHealthData(commonHealth3);

            HealthData<?> commonHealth4 = new CommonHealthData(user.getFullName(), new Date(), "BMI", 145.0, 65.0);
            ((CommonHealthData) commonHealth4).validate();
            user.addHealthData(commonHealth4);

            // Add custom health data
            HealthData<?> customHealth = new CustomHealthData("Ankle pain", new Date(), "ankle pain on 5-12-2023");
            user.addHealthData(customHealth);

            // Print out John Doe's health data (original)
            System.out.println("Original health data:");
            printHealthData(user);

            // Edit health data
            int indexToEdit = 1; // Index of the health data entry to edit
            HealthData<?> newHealthData = new CommonHealthData(user.getFullName(), new Date(), "Cholesterol", 130, 70, 300);
            ((CommonHealthData) newHealthData).validate();

            user.editHealthData(indexToEdit, newHealthData);

            System.out.println("Updated health data:");

            // Print out John Doe's health data (updated)
            printHealthData(user);
        } catch (HealthDataException e) {
            System.out.println("Health data validation error: " + e.getMessage());
       // } catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }

    private static void printHealthData(User<HealthData<?>> user) {
        System.out.println(user.getFullName() + "'s health data:");
        for (HealthData<?> healthData : user.getHealthDataList()) {
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
    }
}
