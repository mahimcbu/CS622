package PHI;

import java.util.ArrayList;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
	    HealthData healthData = new CommonHealthData("John", new Date(System.currentTimeMillis()), "Blood pressure", 120, 80, 25.0, 35, 80.0, 130, 70, 60, false);
	    CustomHealthData customHealthData = new CustomHealthData("Sarah", new Date(System.currentTimeMillis()));

	    // downcast healthData to CommonHealthData
	    if (healthData instanceof CommonHealthData) {
	        CommonHealthData commonHealthData = (CommonHealthData) healthData;
	        System.out.println(commonHealthData.getMetric() + ": " + commonHealthData.getSystolicBP() + "/" + commonHealthData.getDiastolicBP());
	    }

	    // downcast healthData to CustomHealthData
	    if (healthData instanceof CustomHealthData) {
	        CustomHealthData customHealthData2 = (CustomHealthData) healthData;
	        customHealthData2.addNote("This is a custom note");
	    }

	    // downcast customHealthData to HealthData
	    HealthData healthData2 = (HealthData) customHealthData;
	    System.out.println("Name: " + healthData2.getName());
	}



}
