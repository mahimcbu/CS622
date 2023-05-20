package PHI;


public class HealthDataChecker {
    public static void checkBloodPressure(CommonHealthData common) {
    	/**
    	 * Checks if the blood pressure values are within the ideal range.
    	 *
    	 * Pre-condition:
    	 * - The `common` parameter is not null.
    	 * - The `common` object has valid systolic and diastolic blood pressure values set.
    	 *
    	 * Post-condition:
    	 * - Prints whether the blood pressure is within the ideal range or not.
    	 * - No changes are made to the `common` object or its properties.
    	 */
        int systolic = common.getSystolicBP();
        int diastolic = common.getDiastolicBP();

        // Read ideal range from the HealthMetrics text file
        double[] idealValues = HealthMetricsReader.readIdealValue("Systolic Blood Pressure");

      
        double idealMinSystolic = idealValues[0];
        double idealMaxSystolic = idealValues[1];
        double idealMinDiastolic = HealthMetricsReader.readIdealValue("Diastolic Blood Pressure")[0];
        double idealMaxDiastolic = HealthMetricsReader.readIdealValue("Diastolic Blood Pressure")[1];

        if (systolic >= idealMinSystolic && systolic <= idealMaxSystolic && diastolic >= idealMinDiastolic && diastolic <= idealMaxDiastolic) {
            System.out.println("Blood pressure is within the ideal range.");
        } else {
            System.out.println("Blood pressure is not within the ideal range.");
        }
    }

    public static void checkCholesterol(CommonHealthData common) {
    	/**
    	 * Checks if the cholesterol levels and triglyceride level are within the ideal range.
    	 *
    	 * Pre-condition:
    	 * - The `common` parameter is not null.
    	 * - The `common` object has valid LDL, HDL, and triglyceride cholesterol values set.
    	 *
    	 * Post-condition:
    	 * - Prints whether the cholesterol levels and triglyceride level are within the ideal range or not.
    	 * - No changes are made to the `common` object or its properties.
    	 */
        int ldl = common.getLdlCholesterol();
        int hdl = common.getHdlCholesterol();
        int tri = common.getTriglycerideCholesterol();

        // Read ideal range from the HealthMetrics text file
        double[] idealLDLValues = HealthMetricsReader.readIdealValue("LDL Cholesterol");
        double[] idealHDLValues = HealthMetricsReader.readIdealValue("HDL Cholesterol");
        double[] idealTriValues = HealthMetricsReader.readIdealValue("Triglyceride Cholesterol");

        // Compare with the ideal range
        double idealMinLDL = idealLDLValues[0];
        double idealMaxLDL = idealLDLValues[1];
        double idealMinHDL = idealHDLValues[0];
        double idealMaxHDL = idealHDLValues[1];
        double idealMinTri = idealTriValues[0];
        double idealMaxTri = idealTriValues[1];

        if (ldl >= idealMinLDL && ldl <= idealMaxLDL && hdl >= idealMinHDL && hdl <= idealMaxHDL) {
            System.out.println("Cholesterol levels are within the ideal range.");
        } else {
            System.out.println("Cholesterol levels are not within the ideal range.");
        }
        if (tri >= idealMinTri && tri <= idealMaxTri) {
            System.out.println("Triglyceride level is within the ideal range.");
        } else {
            System.out.println("Triglyceride level is not within the ideal range.");
        }
    }

    public static void checkBloodGlucose(CommonHealthData common) {
    	/**
    	 * Checks if the blood glucose level is within the normal range.
    	 *
    	 * Pre-condition:
    	 * - The `common` parameter is not null.
    	 * - The `common` object has a valid blood glucose level value set.
    	 *
    	 * Post-condition:
    	 * - Prints whether the blood glucose level is within the normal range or indicates diabetes.
    	 * - No changes are made to the `common` object or its properties.
    	 */
        double glucoseLevel = common.getGlucoseLevel();

        // Read ideal range from the HealthMetrics text file
        double[] idealValues = HealthMetricsReader.readIdealValue("Blood Glucose");

        // Compare with the ideal range
        double idealMin = idealValues[0];
        double idealMax = idealValues[1];

        if (glucoseLevel >= idealMin && glucoseLevel <= idealMax) {
            System.out.println("Blood glucose level is within the normal range.");
        } else {
            System.out.println("Blood glucose level indicates diabetes.");
        }
    }

    public static void checkBMI(CommonHealthData common) {
    	/**
    	 * Checks if the blood glucose level is within the normal range.
    	 *
    	 * Pre-condition:
    	 * - The `common` parameter is not null.
    	 * - The `common` object has a valid bmi value set from the calculateBMI().
    	 *
    	 * Post-condition:
    	 * - Prints whether the bmi is within the normal range or indicates overweight.
    	 * - No changes are made to the `common` object or its properties.
    	 */
        double bmi = common.calculateBMI();

        // Read ideal range from the HealthMetrics text file
        double[] idealValues = HealthMetricsReader.readIdealValue("BMI");

        // Compare with the ideal range
        double idealMin = idealValues[0];
        double idealMax = idealValues[1];

        if (bmi >= idealMin && bmi <= idealMax) {
            System.out.println("BMI is within the ideal range.");
        } else {
            System.out.println("BMI is not within the ideal range.");
        }
    }
}
