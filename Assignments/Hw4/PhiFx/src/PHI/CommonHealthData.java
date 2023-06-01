package PHI;

import java.util.Date;
import java.text.DecimalFormat;


public class CommonHealthData extends HealthData {
    private String metric;
    private int systolicBP;
	private int diastolicBP;
    private int age;
    private double weight;
    private double height;
    private int ldlCholesterol;
    private int hdlCholesterol;
    private int triglycerideCholesterol;
    private double glucoseLevel;

    public CommonHealthData(String name, Date date, String metric, int systolicBP, int diastolicBP) {
        super(name, date);
        this.metric = metric;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
    }

    public CommonHealthData(String name, Date date, String metric, int ldlCholesterol, int hdlCholesterol, int triglycerideCholesterol) {
        super(name, date);
        this.metric = metric;
        this.ldlCholesterol = ldlCholesterol;
        this.hdlCholesterol = hdlCholesterol;
        this.triglycerideCholesterol = triglycerideCholesterol;
    }


    public CommonHealthData(String name, Date date, String metric, double glucoseLevel) {
        super(name, date);
        this.metric = metric;
        this.glucoseLevel = glucoseLevel;
    }

    public CommonHealthData(String name, Date date, String metric, double weight, double height) {
        super(name, date);
        this.metric = metric;
        this.weight = weight;
        this.height = height;
    }


    @Override
    public String getMetric() {
        return metric;
    }

    public int getSystolicBP() {
        return systolicBP;
    }

    public int getDiastolicBP() {
        return diastolicBP;
    }

    public int getAge() {
        return age;
    }
    public void setSystolicBP(int systolicBP) {
		this.systolicBP = systolicBP;
	}

	public void setDiastolicBP(int diastolicBP) {
		this.diastolicBP = diastolicBP;
	}
    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getLdlCholesterol() {
        return ldlCholesterol;
    }

    public void setLdlCholesterol(int ldlCholesterol) {
        this.ldlCholesterol = ldlCholesterol;
    }

    public int getHdlCholesterol() {
        return hdlCholesterol;
    }

    public void setHdlCholesterol(int hdlCholesterol) {
        this.hdlCholesterol = hdlCholesterol;
    }

    public int getTriglycerideCholesterol() {
        return triglycerideCholesterol;
    }

    public void setTriglycerideCholesterol(int triglycerideCholesterol) {
        this.triglycerideCholesterol = triglycerideCholesterol;
    }

    public double getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(double glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }

    public double calculateBMI() {
    	/**
    	 * Calculates the Body Mass Index (BMI) based on the height and weight values.
    	 *
    	 * Pre-condition:
    	 * - The height and weight values have been set.
    	 *
    	 * Post-condition:
    	 * - The BMI value is calculated and returned.
    	 * - The height and weight values remain unchanged.
    	 */
        double heightInMeters = height * 0.0254; // Convert inches to meters (1 inch = 0.0254 meters)
        double weightInKilograms = weight * 0.453592; // Convert pounds to kilograms (1 pound = 0.453592 kilograms)
        double bmi = weightInKilograms / (heightInMeters * heightInMeters);
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format to two decimal places
        return Double.parseDouble(decimalFormat.format(bmi));
    }
    public void validate() throws HealthDataException {
    	/**
         * Validates the health data based on the metric type.
         * 
         * @throws HealthDataException if the health data fails validation
         * 
         * Pre-condition:
         * - The health data values have been set.
         * 
         * Post-condition:
         * - The health data is valid/positive according to the specific metric type.
         * - If the validation fails, a HealthDataException is thrown.
         */
        if (systolicBP < 0 || diastolicBP < 0) {
            throw new HealthDataException("Blood pressure values cannot be negative.");
        }
        if (getMetric().equals("BMI")) {
        if (weight <= 0 || height <= 0) {
            throw new HealthDataException("Weight and height values must be positive.");
        }
        }
        if (getMetric().equals("Cholesterol")) {
            if (ldlCholesterol < 0 || hdlCholesterol < 0 || triglycerideCholesterol < 0) {
                throw new HealthDataException("Cholesterol values must be positive.");
            }
        }
        if (getMetric().equals("Blood Glucose")) {
        if ( glucoseLevel <= 0) {
            throw new HealthDataException("glucoseLevel value must be positive.");
        }
        }
    }



    @Override
    public String getData() {
        StringBuilder data = new StringBuilder();
        data.append("Recorded at: ").append(getDate()).append("\n");
        data.append("Metric: ").append(metric).append("\n");
        data.append("Systolic BP: ").append(systolicBP).append("\n");
        data.append("Diastolic BP: ").append(diastolicBP).append("\n");
        data.append("BMI: ").append(calculateBMI()).append("\n");
        if (age != 0) {
            data.append("Age: ").append(age).append("\n");
        }
        if (weight != 0) {
            data.append("Weight: ").append(weight).append("\n");
        }
        if (height != 0) {
            data.append("Height: ").append(height).append("\n");
        }
        if (ldlCholesterol != 0) {
            data.append("LDL Cholesterol: ").append(ldlCholesterol).append("\n");
        }
        if (hdlCholesterol != 0) {
            data.append("HDL Cholesterol: ").append(hdlCholesterol).append("\n");
        }
        if (triglycerideCholesterol != 0) {
            data.append("Triglyceride Cholesterol: ").append(triglycerideCholesterol).append("\n");
        }
        if (glucoseLevel != 0) {
            data.append("Glucose Level: ").append(glucoseLevel).append("\n");
        }
        return data.toString();
    }
}
