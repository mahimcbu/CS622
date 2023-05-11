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
        String data = "";
        if(metric.equals("Blood Pressure")) {
            data = systolicBP + "/" + diastolicBP;
        }
        else if(metric.equals("BMI")) {
            data = String.format("%.1f", bmi);
        }
        else if(metric.equals("Age")) {
            data = String.valueOf(age);
        }
        else if(metric.equals("Weight")) {
            data = String.format("%.1f", weight);
        }
        else if(metric.equals("Cholesterol")) {
            data = "LDL: " + ldlCholesterol + ", HDL: " + hdlCholesterol + ", Triglycerides: " + triglycerideCholesterol;
        }
        else if(metric.equals("Diabetes")) {
            data = diabetes ? "Yes" : "No";
        }
        return data;
    }
}
