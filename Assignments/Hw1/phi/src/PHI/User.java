package PHI;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String gender;
    private String phoneNumber;
    private ArrayList<HealthData> healthDataList;

    public User(String firstName, String lastName, String email, String password, Date dateOfBirth, String gender, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.healthDataList = new ArrayList<>();
    }

    public void addHealthData(HealthData healthData) {
        healthDataList.add(healthData);
    }

    public void removeHealthData(HealthData healthData) {
        healthDataList.remove(healthData);
    }

    public ArrayList<HealthData> getHealthDataList() {
        return healthDataList;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Other getters and setters for the remaining attributes
}
