package application;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

class HealthDataCheckerTest {

	@Test
	void testCheckBloodPressure() {
		// Create a test CommonHealthData object with valid blood pressure values
		CommonHealthData commonHealthData = new CommonHealthData("John Doe", new Date(0), "Blood Pressure", 120, 80);

		// Call the method under test
		HealthDataChecker.checkBloodPressure(commonHealthData);

		// No assertions are added here since the method only prints to the console
	}

	@Test
	void testCheckCholesterol() {
		// Create a test CommonHealthData object with valid cholesterol values
		CommonHealthData commonHealthData = new CommonHealthData("John Doe", new Date(0), "Cholesterol", 100, 60, 150);

		// Call the method under test
		HealthDataChecker.checkCholesterol(commonHealthData);

		// No assertions are added here since the method only prints to the console
	}

	@Test
	void testCheckBloodGlucose() {
		// Create a test CommonHealthData object with a valid blood glucose level
		CommonHealthData commonHealthData = new CommonHealthData("John Doe", new Date(0), "Blood Glucose", 90);

		// Call the method under test
		HealthDataChecker.checkBloodGlucose(commonHealthData);

		// No assertions are added here since the method only prints to the console
	}

	@Test
	void testCheckBMI() {
		// Create a test CommonHealthData object with a valid BMI value
		CommonHealthData commonHealthData = new CommonHealthData("John Doe", new Date(0), "BMI", 70, 1.75);

		// Call the method under test
		HealthDataChecker.checkBMI(commonHealthData);

		// No assertions are added here since the method only prints to the console
	}

}
