package Hw1;

import java.util.ArrayList;
import java.util.List;

public class Family extends Person {
    private List<Person> members;
    private String familyName;
    private String familyPassword;
    
    public Family(String fullName, int age, long id, String email, String familyName, String familyPassword) {
        super(fullName, age, email, id);
        this.members = new ArrayList<>();
        this.familyName = familyName;
        this.familyPassword = familyPassword;
    }
    
    public void addMember(Person p) {
        members.add(p);
    }
    
    public String getHealthInfo() {
        // implementation here
        return "";
    }
    
    // getters and setters
    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyPassword() {
        return familyPassword;
    }

    public void setFamilyPassword(String familyPassword) {
        this.familyPassword = familyPassword;
    }
}