package application;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class phiTest {
	//This Junit also tests the validate() method of CommonHealthData class.
	//It also test's the HealthData class
    @Test
    public void testPHI() {
        User user = new User("John", "Doe", "johndoe@example.com", "Passw0rd",
                new Date(), "Male", "555-1234");

        // Add common health data
        HealthData commonHealth1 = new CommonHealthData(user.getFullName(), new Date(), "Blood Pressure", 120, 60);
        HealthData commonHealth2 = new CommonHealthData(user.getFullName(), new Date(), "Cholesterol", 120, 60, 140);
        HealthData commonHealth3 = new CommonHealthData(user.getFullName(), new Date(), "Blood Glucose", 100);
        HealthData commonHealth4 = new CommonHealthData(user.getFullName(), new Date(), "BMI", 145.0, 65.0);

        // Add custom health data
        HealthData customHealth = new CustomHealthData("Ankle pain", new Date(), "ankle pain on 5-12-2023");

        // Add health data to the user
        user.addHealthData(commonHealth1);
        user.addHealthData(commonHealth2);
        user.addHealthData(commonHealth3);
        user.addHealthData(commonHealth4);
        user.addHealthData(customHealth);

        // Test user's health data
        assertEquals(5, user.getHealthDataList().size());
        assertTrue(user.getHealthDataList().contains(commonHealth1));
        assertTrue(user.getHealthDataList().contains(commonHealth2));
        assertTrue(user.getHealthDataList().contains(commonHealth3));
        assertTrue(user.getHealthDataList().contains(commonHealth4));
        assertTrue(user.getHealthDataList().contains(customHealth));

        // Test common health data validation
        assertDoesNotThrow(() -> ((CommonHealthData) commonHealth1).validate());
        assertDoesNotThrow(() -> ((CommonHealthData) commonHealth2).validate());
        assertDoesNotThrow(() -> ((CommonHealthData) commonHealth3).validate());
        assertDoesNotThrow(() -> ((CommonHealthData) commonHealth4).validate());

        
    }
}
