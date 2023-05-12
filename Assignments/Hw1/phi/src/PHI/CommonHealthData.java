package PHI;

import java.util.Date;

public class CommonHealthData extends HealthData {
    private String metric;
    private int systolicBP;
    private int diastolicBP;
    private double bmi;
    private int age;
    private double weight;
    private int ldlCholesterol;
    private int hdlCholesterol;
    private int triglycerideCholesterol;
    private boolean diabetes;

    public CommonHealthData(String name, Date date, String metric, int systolicBP, int diastolicBP, double bmi, int age, double weight, int ldlCholesterol, int hdlCholesterol, int triglycerideCholesterol, boolean diabetes) {
        super(name, date);
        this.metric = metric;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.bmi = bmi;
        this.age = age;
        this.weight = weight;
        this.ldlCholesterol = ldlCholesterol;
        this.hdlCholesterol = hdlCholesterol;
        this.triglycerideCholesterol = triglycerideCholesterol;
        this.diabetes = diabetes;
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

    public double getBmi() {
        return bmi;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public int getLdlCholesterol() {
        return ldlCholesterol;
    }

    public int getHdlCholesterol() {
        return hdlCholesterol;
    }

    public int getTriglycerideCholesterol() {
        return triglycerideCholesterol;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

@Override

public String getData() {
    StringBuilder data = new StringBuilder();
    data.append("Recorded at: ").append(getDate()).append("\n");
    data.append("Metric: ").append(metric).append("\n");
    data.append("Systolic BP: ").append(systolicBP).append("\n");
    data.append("Diastolic BP: ").append(diastolicBP).append("\n");
    data.append("BMI: ").append(bmi).append("\n");
    data.append("Age: ").append(age).append("\n");
    data.append("Weight: ").append(weight).append("\n");
    data.append("LDL Cholesterol: ").append(ldlCholesterol).append("\n");
    data.append("HDL Cholesterol: ").append(hdlCholesterol).append("\n");
    data.append("Triglyceride Cholesterol: ").append(triglycerideCholesterol).append("\n");
    data.append("Diabetes: ").append(diabetes ? "Yes" : "No").append("\n");
    return data.toString();
}

}
