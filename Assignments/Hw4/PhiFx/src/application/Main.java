package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import PHI.User;

import java.time.LocalDate;
import java.util.Date;

public class Main extends Application {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene createUserScene;

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
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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

        // Handle login button click event (placeholder action)
        loginButton.setOnAction(event -> {
            // Placeholder action, you can add your own logic here
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            System.out.println("Logging in with username: " + username + ", password: " + password);
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

            try {
                // Validate email and password inputs using User class methods
                User user = new User(firstName, lastName, email, password, convertToDate(dateOfBirth), gender, phoneNumber);

                // Perform any additional operations with the created user object
                // For example, save the user to a database

                // Show a success message or provide feedback to the user
                // Example: display a dialog
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User Creation");
                alert.setHeaderText(null);
                alert.setContentText("User created successfully!");
                alert.showAndWait();

                // Switch back to the login scene
                primaryStage.setScene(loginScene);
            } catch (IllegalArgumentException e) {
                // Show an error message or provide feedback to the user
                // Example: display an error dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
