package PHI;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
    	/**
    	 * Precondition: None.
    	 *
    	 * Postcondition: Creates a new User object with the specified health data and adds it to the list of users. 
    	 *                Then adds commonHealth and customHealth objects to the user's health data list.
    	 *                Finally, prints out the user's health data by iterating over the health data list and 
    	 *                downcasting the objects to CommonHealthData or CustomHealthData as appropriate.
    	 */
        // Create a user with some health data
        User user = new User("John", "Doe", "johndoe@example.com", "Passw0rd", new Date(), "Male", "555-1234");
        HealthData commonHealth = new CommonHealthData("Blood Pressure", new Date(), "Blood Pressure", 120, 80, 23.5, 35, 145.0, 100, 60, 120, false);
        HealthData customHealth = new CustomHealthData("Ankle pain", new Date(), "ankle pain on 5-12-2023");
        

        user.addHealthData(commonHealth);
        user.addHealthData(customHealth);

        // Print out the user's health data
        System.out.println("User's health data:");
        for (HealthData healthData : user.getHealthDataList()) {
            System.out.println(healthData.getMetric() + ": " + healthData.getData());

            // Demonstrate polymorphism and downcasting
            if (healthData instanceof CommonHealthData) {
                CommonHealthData common = (CommonHealthData) healthData;
                System.out.println("one example to prove downcasting: age - "+common.getAge());
            } else if (healthData instanceof CustomHealthData) {
                CustomHealthData custom = (CustomHealthData) healthData;
                for (String note : custom.getNotes()) {
                    System.out.println("after downcasting, - " + note);
                }
            }
        }
    }
}
