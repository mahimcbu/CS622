package Hw1;


public class User extends Person {
    private String familyName;
    private int familyPassword;
    
    public User(String fullName, int age, long id, String email, String familyName, int familyPassword) {
        super(fullName, age, email, id);
        this.familyName = familyName;
        this.familyPassword = familyPassword;
        System.out.println("Hello");
    }
    @Override
    public void addMember(Person p) {
        // implementation here
    }
    @Override
    public String getHealthInfo() {
        // implementation here
        return "";
    }
    public void logIn() {
    	
    };
    // getters and setters
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getFamilyPassword() {
        return familyPassword;
    }

    public void setFamilyPassword(int familyPassword) {
        this.familyPassword = familyPassword;
    }
}