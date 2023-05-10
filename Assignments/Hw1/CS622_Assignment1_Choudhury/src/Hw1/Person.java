package Hw1;

public abstract class Person {
 private String fullName;
 private int age;
 private String email;
 private long id;
 
 
 public Person(String fullName, int age, String email, long id) {
	super();
	this.fullName = fullName;
	this.age = age;
	this.email = email;
	this.id = id;
 }


public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}

public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}
 
public abstract void addMember(Person person);

public abstract String getHealthInfo();
}
