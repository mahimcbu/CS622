package Hw1;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		Person person1 = new User("John Smith", 30, 1234, "johnsmith@email.com", "Smith", 5678);

		// Downcast person1 to a User
		User user1 = (User) person1;

		// Access User-specific field
		String familyName = user1.getFamilyName();
		System.out.println(familyName);


	}

}
