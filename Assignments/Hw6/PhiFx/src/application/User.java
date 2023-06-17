package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User<T extends HealthData<?>> {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String gender;
    private String phoneNumber;
    private ArrayList<T> healthDataList;

    public User(String firstName, String lastName, String email, String password, Date dateOfBirth, String gender, String phoneNumber) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (dateOfBirth.after(new Date())) {
            throw new IllegalArgumentException("Invalid date of birth");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.healthDataList = new ArrayList<>();
    }

    public void addHealthData(T healthData) {
        healthDataList.add(healthData);
    }

    public void removeHealthData(T healthData) {
        healthDataList.remove(healthData);
    }
    public void editHealthData(int index, T newHealthData) {
        if (index < 0 || index >= healthDataList.size()) {
            throw new IllegalArgumentException("Invalid index for editing health data");
        }

        HealthData<?> existingData = healthDataList.get(index);
        String existingMetric = existingData.getMetric();
        String newMetric = newHealthData.getMetric();

        if (!existingMetric.equals(newMetric)) {
            throw new IllegalArgumentException("Cannot edit health data at index " + index + " with a different metric type");
        }
        System.out.println("Editing health data at index " + index + " for " + existingMetric);
        healthDataList.set(index, newHealthData);
    }




    public ArrayList<T> getHealthDataList() {
        return healthDataList;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        // Password must have at least 8 characters, one uppercase letter, one lowercase letter, and one digit
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
