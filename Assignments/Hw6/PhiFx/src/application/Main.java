package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;

public class Main extends Application {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene createUserScene;
    private HealthDataEntry healthDataEntry;
    private User<HealthData<?>> user; // Declare user as an instance variable

    private static final String url = "jdbc:sqlite:healthtracker.db";
//    private void printUserRecords() {
//        try (Connection conn = DriverManager.getConnection(url);
//             Statement stmt = conn.createStatement()) {
//            String selectUsersSql = "SELECT * FROM BmiData";
//            ResultSet resultSet = stmt.executeQuery(selectUsersSql);
//
//            while (resultSet.next()) {
//                String weight = resultSet.getString("weight");
//                String height = resultSet.getString("height");
////                String email = resultSet.getString("email");
//                // Retrieve other columns as needed
//
//                System.out.println("weight: " + weight + " " + "height: " + height);
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving user data: " + e.getMessage());
//        }
//    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        createLoginScene();
        createCreateUserScene();

        // Set the initial scene to the login scene
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login/Create User");
        primaryStage.show();
    }
    private void createLoginScene() {
        // Create UI components for login scene
        Label usernameLabel = new Label("Username:");
        TextField usernameTextField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordTextField = new PasswordField();
        Button loginButton = new Button("Login");
        Button createUserButton = new Button("Create User");

        // Create layout container for login scene
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(5);
        root.setPadding(new Insets(10));

        // Add UI components to the grid pane
        root.add(usernameLabel, 0, 0);
        root.add(usernameTextField, 1, 0);
        root.add(passwordLabel, 0, 1);
        root.add(passwordTextField, 1, 1);
        root.add(loginButton, 0, 2);
        root.add(createUserButton, 1, 2);

        // Create login scene
        loginScene = new Scene(root, 400, 200);

        // Handle create user button click event
        createUserButton.setOnAction(event -> {
            primaryStage.setScene(createUserScene);
        });

        // Handle login button click event
        loginButton.setOnAction(event -> {
            String email = usernameTextField.getText();
            String password = passwordTextField.getText();

            // Perform login validation
            boolean loginSuccessful = validateLogin(email, password);

            if (loginSuccessful) {
                // Login successful
                System.out.println("Login successful. Email: " + email);

                // Proceed with further logic or switch to another scene
                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()) {
                    // Retrieve user data from the User table
                	String getUserSql = "SELECT * FROM User WHERE email = '" + email + "'";
                    ResultSet resultSet = stmt.executeQuery(getUserSql);

                    if (resultSet.next()) {
                        // Retrieve user data from the result set
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");
                        String storedEmail = resultSet.getString("email");
                        String storedPassword = resultSet.getString("password");
                        LocalDate dateOfBirth = resultSet.getObject("dateOfBirth", LocalDate.class);
                        String gender = resultSet.getString("gender");
                        String phoneNumber = resultSet.getString("phoneNumber");

                        // Create User object
                        user = new User<>(firstName, lastName, storedEmail, storedPassword, dateOfBirth, gender, phoneNumber);

                        // Switch to the health data entry scene
                        healthDataEntry = new HealthDataEntry(primaryStage, user);
//                        printUserRecords();
                        healthDataEntry.showHealthDataEntryScene();
                    }
                } catch (SQLException e) {
                    System.out.println("Error retrieving user data: " + e.getMessage());
                }
            } else {
                // Login failed
                System.out.println("Invalid email or password. Please try again.");

                // Display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password. Please try again.");
                alert.showAndWait();
            }
        });
    }



    private void createCreateUserScene() {
        // Create UI components for create user scene
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameTextField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameTextField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordTextField = new PasswordField();
        Label dateOfBirthLabel = new Label("Date of Birth:");
        DatePicker dateOfBirthPicker = new DatePicker();
        Label genderLabel = new Label("Gender:");
        TextField genderTextField = new TextField();
        Label phoneNumberLabel = new Label("Phone Number:");
        TextField phoneNumberTextField = new TextField();
        Button createUserButton = new Button("Create User");

        // Create layout container for create user scene
        VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                firstNameLabel, firstNameTextField,
                lastNameLabel, lastNameTextField,
                emailLabel, emailTextField,
                passwordLabel, passwordTextField,
                dateOfBirthLabel, dateOfBirthPicker,
                genderLabel, genderTextField,
                phoneNumberLabel, phoneNumberTextField,
                createUserButton
        );

        // Create create user scene
        createUserScene = new Scene(root, 500, 500);

        // Handle create user button click event
        createUserButton.setOnAction(event -> {
            // Retrieve user input
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            LocalDate dateOfBirth = dateOfBirthPicker.getValue();
            String gender = genderTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                // Check if the email already exists in the User table
                String checkEmailSql = "SELECT * FROM User WHERE email = '" + email + "'";
                ResultSet resultSet = stmt.executeQuery(checkEmailSql);

                if (resultSet.next()) {
                    // Email already exists, show an error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("User Creation Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Email already exists. Please use a different email.");
                    alert.showAndWait();
                } else {
                    // Email is unique, proceed with user creation
                    // Create User object
                    User<HealthData<?>> user = new User<>(firstName, lastName, email, password, dateOfBirth, gender, phoneNumber);

                    // Insert the user into the User table
                    String insertUserSql = "INSERT INTO User (firstName, lastName, email, password, dateOfBirth, gender, phoneNumber) VALUES ('"
                            + user.getFirstName() + "', '"
                            + user.getLastName() + "', '"
                            + user.getEmail() + "', '"
                            + user.getPassword() + "', '"
                            + user.getDateOfBirth() + "', '"
                            + user.getGender() + "', '"
                            + user.getPhoneNumber() + "')";

                    stmt.executeUpdate(insertUserSql);

                    // Show a success message or provide feedback to the user
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("User Creation");
                    alert.setHeaderText(null);
                    alert.setContentText("User created successfully!");
                    alert.showAndWait();

                    // Switch to the health data entry scene
                    healthDataEntry = new HealthDataEntry(primaryStage, user);
                    healthDataEntry.showHealthDataEntryScene();
                }
            } catch (SQLException e) {
                System.out.println("Error creating user: " + e.getMessage());
            }
        });
    }


    private java.sql.Date convertToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    private boolean validateLogin(String email, String password) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // Query the User table to validate login credentials
            String selectUserSql = "SELECT * FROM User WHERE email = '" + email + "' AND password = '" + password + "'";
            ResultSet resultSet = stmt.executeQuery(selectUserSql);

            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error querying user: " + e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}