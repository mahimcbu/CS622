package application;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class HealthDataEntry {
    private Stage primaryStage;
    private Scene healthDataEntryScene;
    private TableView<HealthData<?>> tableView;
    private User<HealthData<?>> user;
    private static final String url = "jdbc:sqlite:healthtracker.db";

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public User<HealthData<?>> getUser() {
        return user;
    }
    private HealthData<CommonHealthData> healthData;
    /**
     * Creates a new instance of HealthDataEntry.
     * @param primaryStage the primary stage for the application
     * @param user the user associated with the health data
     * PRECONDITION: primaryStage is not null
     * PRECONDITION: user is not null
     */
    public HealthDataEntry(Stage primaryStage, User<HealthData<?>> user) {
        this.primaryStage = primaryStage;
        this.user = user;
        createHealthDataEntryScene();

    }
    /**
     * Displays the health data entry scene.
     * PreCondiotn: User created successfully 
     * POSTCONDITION: The health data entry scene is shown on the primary stage.
     */
    public void showHealthDataEntryScene() {
        primaryStage.setScene(healthDataEntryScene);
        primaryStage.setTitle("Health Data Entry");
        primaryStage.show();
    }
    /**
     * Creates the health data entry scene and sets up event handlers for the buttons.
     * Precondition: ShowHealthDataEntryScene works
     * POSTCONDITION: The health data entry scene is created with all the necessary UI components and event handlers.
     */
    private void createHealthDataEntryScene() {
        // Create UI components for health data entry scene
        Label titleLabel = new Label("Health Data Entry");

        Button bloodPressureButton = new Button("Blood Pressure");
        Button cholesterolButton = new Button("Cholesterol");
        Button bmiButton = new Button("BMI");
        Button bloodSugarButton = new Button("Blood Sugar");
        Button customHealthNoteButton = new Button("Custom Health Note");
        Button historyButton = new Button("History");

        // Create layout container for health data entry scene
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, bloodPressureButton, cholesterolButton, bmiButton,
                bloodSugarButton, customHealthNoteButton, historyButton);

        // Create health data entry scene
        healthDataEntryScene = new Scene(root, 400, 300);

        // Handle blood pressure button click event
        bloodPressureButton.setOnAction(event -> {  
          showBloodPressureScene(null, false, tableView);
        });
        
        // Handle cholesterol button click event
        cholesterolButton.setOnAction(event -> {
            showCholesterolScene(null, false, tableView);
        });

        // Handle BMI button click event
        bmiButton.setOnAction(event -> {
            showBMIScene(null, false, tableView);
        });

        // Handle blood sugar button click event
        bloodSugarButton.setOnAction(event -> {
            showBloodSugarScene(null, false, tableView);
        });

        // Handle custom health note button click event
        customHealthNoteButton.setOnAction(event -> {
            showCustomHealthNoteScene(null, false, tableView);
        });

        // Handle history button click event
        historyButton.setOnAction(event -> {
        	showHistoryScreen();
        });
    }
    /**
     * Shows the blood pressure scene for data entry.
     * Precondition: Button exist and click-able
     * POSTCONDITION: A new Scene object for blood pressure entry is created and returned.
     */
    public Scene showBloodPressureScene(CommonHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        Stage bloodPressureStage = new Stage(); // Create a new stage

        // Create UI components for blood pressure scene
        Label titleLabel = new Label("Blood Pressure");

        Label systolicBPLabel = new Label("Systolic BP:");
        TextField systolicBPTextField = new TextField();

        Label diastolicBPLabel = new Label("Diastolic BP:");
        TextField diastolicBPTextField = new TextField();

        Button submitButton = new Button("Submit");

        // Create layout container for blood pressure scene
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, systolicBPLabel, systolicBPTextField, diastolicBPLabel,
                diastolicBPTextField, submitButton);

        // Create blood pressure scene
        Scene bloodPressureScene = new Scene(root, 400, 300);

        // Handle submit button click event
        submitButton.setOnAction(event -> {
            int systolicBP = Integer.parseInt(systolicBPTextField.getText());
            int diastolicBP = Integer.parseInt(diastolicBPTextField.getText());

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {

                // Fetch the user ID from the User table based on the user's email
                String selectUserSql = "SELECT id FROM User WHERE email = '" + user.getEmail() + "'";
                ResultSet resultSet = stmt.executeQuery(selectUserSql);
                int userId = resultSet.getInt("id");

                if (isEdit && existingHealthData instanceof CommonHealthData) {
                    CommonHealthData commonHealthData = (CommonHealthData) existingHealthData;
                    commonHealthData.setSystolicBP(systolicBP);
                    commonHealthData.setDiastolicBP(diastolicBP);

                    try {
                        commonHealthData.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }

                    // Create the SQL UPDATE statement to update the blood pressure data
                    String updateDataSql = "UPDATE BloodPressureData SET systolicBP = " + systolicBP
                            + ", diastolicBP = " + diastolicBP + " WHERE userId = " + userId;

                    // Execute the UPDATE statement
                    stmt.executeUpdate(updateDataSql);

                    tableView.refresh();
                    Platform.runLater(() -> {
                        Stage editbloodpreStage = (Stage) bloodPressureScene.getWindow();
                        editbloodpreStage.close();
                        bloodPressureStage.close();
                    });
                } else {
                    // Create the SQL INSERT statement to insert the blood pressure data
                    String insertDataSql = "INSERT INTO BloodPressureData (userId, systolicBP, diastolicBP, dateRecorded) VALUES ("
                            + userId + ", " + systolicBP + ", " + diastolicBP + ", '" + new Date() + "')";

                    // Execute the INSERT statement
                    stmt.executeUpdate(insertDataSql);

                    // Create a new health data entry and add it to the user's health data
                    String name = "Blood Pressure";
                    Date date = new Date();
                    Date originalDate = date;
                    String metric = "Blood Pressure";
                    CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, systolicBP, diastolicBP);
                    try {
                        healthDataEntry.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }
                    user.addHealthData(healthDataEntry);
                    HealthDataChecker.checkBloodPressure(healthDataEntry);

                    Platform.runLater(() -> {
                        bloodPressureStage.close();
                        showHealthDataEntryScene();
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        bloodPressureStage.setScene(bloodPressureScene); // Set the blood pressure scene to the new stage
        bloodPressureStage.show(); // Show the new stage

        return bloodPressureScene;
    }


    /**
     * Shows the Cholesterol scene for data entry.
     * Precondition: Button exists and is clickable.
     * POSTCONDITION: A new Scene object for cholesterol entry is created and returned.
     */
    public Scene showCholesterolScene(CommonHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        // Create UI components for cholesterol scene
        Stage cholesterolStage = new Stage(); // Create a new stage

        Label titleLabel = new Label("Cholesterol");

        Label ldlCholesterolLabel = new Label("LDL Cholesterol:");
        TextField ldlCholesterolTextField = new TextField();

        Label hdlCholesterolLabel = new Label("HDL Cholesterol:");
        TextField hdlCholesterolTextField = new TextField();

        Label triglycerideCholesterolLabel = new Label("Triglyceride Cholesterol:");
        TextField triglycerideCholesterolTextField = new TextField();

        Button submitButton = new Button("Submit");

        // Create layout container for cholesterol scene
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, ldlCholesterolLabel, ldlCholesterolTextField, hdlCholesterolLabel,
                hdlCholesterolTextField, triglycerideCholesterolLabel, triglycerideCholesterolTextField, submitButton);

        // Create cholesterol scene
        Scene cholesterolScene = new Scene(root, 400, 300);

        // Handle submit button click event
        submitButton.setOnAction(event -> {
            int ldlCholesterol = Integer.parseInt(ldlCholesterolTextField.getText());
            int hdlCholesterol = Integer.parseInt(hdlCholesterolTextField.getText());
            int triglycerideCholesterol = Integer.parseInt(triglycerideCholesterolTextField.getText());

            if (isEdit) {
                if (existingHealthData instanceof CommonHealthData) {
                    CommonHealthData commonHealthData = (CommonHealthData) existingHealthData;
                    commonHealthData.setLdlCholesterol(ldlCholesterol);
                    commonHealthData.setHdlCholesterol(hdlCholesterol);
                    commonHealthData.setTriglycerideCholesterol(triglycerideCholesterol);

                    try (Connection conn = DriverManager.getConnection(url);
                         Statement stmt = conn.createStatement()) {

                        // Create the SQL UPDATE statement to update the cholesterol data
                        String selectUserSql = "SELECT id FROM User WHERE email = '" + user.getEmail() + "'";
                        ResultSet resultSet = stmt.executeQuery(selectUserSql);
                        int userId = resultSet.getInt("id");
                        String updateDataSql = "UPDATE CholesterolData SET ldlCholesterol = " + ldlCholesterol
                                + ", hdlCholesterol = " + hdlCholesterol + ", triglycerideCholesterol = " + triglycerideCholesterol
                                + " WHERE userId = " + userId;

                        // Execute the UPDATE statement
                        stmt.executeUpdate(updateDataSql);

                        commonHealthData.validate();
                    } catch (SQLException | HealthDataException e) {
                        e.printStackTrace();
                    }
                }

                tableView.refresh(); // Refresh the table view to reflect the changes
                Platform.runLater(() -> {
                    Stage editCholesterolStage = (Stage) cholesterolScene.getWindow();
                    editCholesterolStage.close();
                    cholesterolStage.close();
                });
            } else {
                String name = "Cholesterol";
                Date date = new Date();
                Date originalDate = date;
                String metric = "Cholesterol";

                CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, ldlCholesterol,
                        hdlCholesterol, triglycerideCholesterol);

                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()) {

                    String selectUserSql = "SELECT id FROM User WHERE email = '" + user.getEmail() + "'";
                    ResultSet resultSet = stmt.executeQuery(selectUserSql);
                    int userId = resultSet.getInt("id");

                    // Create the SQL INSERT statement to insert the cholesterol data
                    String insertDataSql = "INSERT INTO CholesterolData (userId, ldlCholesterol, hdlCholesterol, triglycerideCholesterol, dateRecorded) VALUES ("
                            + userId + ", " + ldlCholesterol + ", " + hdlCholesterol + ", " + triglycerideCholesterol + ", '" + originalDate + "')";

                    // Execute the INSERT statement
                    stmt.executeUpdate(insertDataSql);

                    healthDataEntry.validate();
                } catch (SQLException | HealthDataException e) {
                    e.printStackTrace();
                }

                user.addHealthData(healthDataEntry);
                HealthDataChecker.checkCholesterol(healthDataEntry);
                Platform.runLater(() -> {
                    cholesterolStage.close();
                    showHealthDataEntryScene();
                });
            }
        });

        cholesterolStage.setScene(cholesterolScene);
        cholesterolStage.show();
        return cholesterolScene;
    }

    /**
     * Shows the BMI scene for data entry.
     * Precondition: Button exists and is clickable.
     * POSTCONDITION: A new Scene object for BMI entry is created and returned.
     */
    public Scene showBMIScene(CommonHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        // Create UI components for BMI scene
        Stage bmiStage = new Stage(); // Create a new stage

        Label titleLabel = new Label("BMI");

        Label weightLabel = new Label("Weight:");
        TextField weightTextField = new TextField();

        Label heightLabel = new Label("Height:");
        TextField heightTextField = new TextField();

        Button submitButton = new Button("Submit");

        // Create layout container for BMI scene
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, weightLabel, weightTextField, heightLabel,
                heightTextField, submitButton);

        // Create BMI scene
        Scene bmiScene = new Scene(root, 400, 300);

        // Handle submit button click event
        submitButton.setOnAction(event -> {
            double weight = Double.parseDouble(weightTextField.getText());
            double height = Double.parseDouble(heightTextField.getText());

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {

                // Fetch the user ID from the User table based on the user's email
                String selectUserSql = "SELECT id FROM User WHERE email = '" + user.getEmail() + "'";
                ResultSet resultSet = stmt.executeQuery(selectUserSql);
                int userId = resultSet.getInt("id");

                if (isEdit && existingHealthData instanceof CommonHealthData) {
                    CommonHealthData commonHealthData = (CommonHealthData) existingHealthData;
                    commonHealthData.setWeight(weight);
                    commonHealthData.setHeight(height);

                    try {
                        commonHealthData.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }

                    // Create the SQL UPDATE statement to update the BMI data
                    String updateDataSql = "UPDATE BMIData SET weight = " + weight + ", height = " + height
                            + " WHERE userId = " + userId;

                    // Execute the UPDATE statement
                    stmt.executeUpdate(updateDataSql);

                    tableView.refresh();
                    Platform.runLater(() -> {
                        Stage editBmiStage = (Stage) bmiScene.getWindow();
                        editBmiStage.close();
                        bmiStage.close();
                    });
                } else {
                    // Create the SQL INSERT statement to insert the BMI data
                    String insertDataSql = "INSERT INTO BMIData (userId, weight, height, dateRecorded) VALUES ("
                            + userId + ", " + weight + ", " + height + ", '" + new Date() + "')";

                    // Execute the INSERT statement
                    stmt.executeUpdate(insertDataSql);

                    // Create a new health data entry and add it to the user's health data
                    String name = "BMI";
                    Date date = new Date();
                    Date originalDate = date;
                    String metric = "BMI";

                    CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, weight, height);
                    try {
                        healthDataEntry.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }
                    user.addHealthData(healthDataEntry);
                    HealthDataChecker.checkBMI(healthDataEntry);

                    Platform.runLater(() -> {
                        bmiStage.close();
                        showHealthDataEntryScene();
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        bmiStage.setScene(bmiScene);
        bmiStage.show();
        return bmiScene;
    }


    /**
     * Shows the blood sugar scene for data entry.
     * Precondition: Button exists and is clickable.
     * POSTCONDITION: A new Scene object for blood sugar entry is created and returned.
     */
    public Scene showBloodSugarScene(CommonHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        // Create UI components for blood sugar scene
        Stage bloodSugarStage = new Stage(); // Create a new stage

        Label titleLabel = new Label("Blood Glucose");

        Label glucoseLevelLabel = new Label("Glucose Level:");
        TextField glucoseLevelTextField = new TextField();

        Button submitButton = new Button("Submit");

        // Create layout container for blood sugar scene
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, glucoseLevelLabel, glucoseLevelTextField, submitButton);

        // Create blood sugar scene
        Scene bloodSugarScene = new Scene(root, 400, 300);

        // Handle submit button click event
        submitButton.setOnAction(event -> {
            double bloodSugarLevel = Double.parseDouble(glucoseLevelTextField.getText());

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {

                // Fetch the user ID from the User table based on the user's email
                String selectUserSql = "SELECT id FROM User WHERE email = '" + user.getEmail() + "'";
                ResultSet resultSet = stmt.executeQuery(selectUserSql);
                int userId = resultSet.getInt("id");

                if (isEdit && existingHealthData instanceof CommonHealthData) {
                    CommonHealthData commonHealthData = (CommonHealthData) existingHealthData;
                    commonHealthData.setGlucoseLevel(bloodSugarLevel);

                    try {
                        commonHealthData.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }

                    // Create the SQL UPDATE statement to update the blood sugar data
                    String updateDataSql = "UPDATE DiabetesData SET bloodSugarLevel = " + bloodSugarLevel
                            + " WHERE userId = " + userId;

                    // Execute the UPDATE statement
                    stmt.executeUpdate(updateDataSql);

                    tableView.refresh();
                    Platform.runLater(() -> {
                        Stage editBloodSugarStage = (Stage) bloodSugarScene.getWindow();
                        editBloodSugarStage.close();
                        bloodSugarStage.close();
                    });
                } else {
                    // Create the SQL INSERT statement to insert the blood sugar data
                    String insertDataSql = "INSERT INTO DiabetesData (userId, bloodSugarLevel, dateRecorded) VALUES ("
                            + userId + ", " + bloodSugarLevel + ", '" + new Date() + "')";

                    // Execute the INSERT statement
                    stmt.executeUpdate(insertDataSql);

                    // Create a new health data entry and add it to the user's health data
                    String name = "Blood Glucose";
                    Date date = new Date();
                    Date originalDate = date;
                    String metric = "Blood Glucose";

                    CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, bloodSugarLevel);
                    try {
                        healthDataEntry.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }
                    user.addHealthData(healthDataEntry);
                    HealthDataChecker.checkBloodGlucose(healthDataEntry);

                    Platform.runLater(() -> {
                        bloodSugarStage.close();
                        showHealthDataEntryScene();
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        bloodSugarStage.setScene(bloodSugarScene);
        bloodSugarStage.show();
        return bloodSugarScene;
    }


    /**
     * Shows the custom health note scene for data entry.
     * Precondition: Button exists and is clickable.
     * POSTCONDITION: A new Scene object for custom health note entry is created and returned.
     */
    public Scene showCustomHealthNoteScene(CustomHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        // Create UI components for custom health note scene
        Stage customStage = new Stage(); // Create a new stage

        Label titleLabel = new Label("Custom Health Note");

        Label noteLabel = new Label("Note:");
        TextField noteTextField = new TextField();

        Button submitButton = new Button("Submit");

        // Create layout container for custom health note scene
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, noteLabel, noteTextField, submitButton);

        // Create custom health note scene
        Scene customHealthNoteScene = new Scene(root, 400, 300);

        // Handle submit button click event
        submitButton.setOnAction(event -> {
            String note = noteTextField.getText();

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {

                // Fetch the user ID from the User table based on the user's email
                String selectUserSql = "SELECT id FROM User WHERE email = '" + user.getEmail() + "'";
                ResultSet resultSet = stmt.executeQuery(selectUserSql);
                int userId = resultSet.getInt("id");

                if (isEdit && existingHealthData instanceof CustomHealthData) {
                    CustomHealthData customHealthData = (CustomHealthData) existingHealthData;
//                    customHealthData.setNotes(note);

                    // Create the SQL UPDATE statement to update the custom health note data
                    String updateDataSql = "UPDATE CustomHealthData SET note = '" + note
                            + "' WHERE userId = " + userId;

                    // Execute the UPDATE statement
                    stmt.executeUpdate(updateDataSql);

                    tableView.refresh();
                    Stage editCustomStage = (Stage) customHealthNoteScene.getWindow();
                    Platform.runLater(() -> {
                        editCustomStage.close();
                        customStage.close();
                    });
                } else {
                    // Create the SQL INSERT statement to insert the custom health note data
                    String insertDataSql = "INSERT INTO CustomHealthData (userId, note, dateRecorded) VALUES ("
                            + userId + ", '" + note + "', '" + new Date() + "')";

                    // Execute the INSERT statement
                    stmt.executeUpdate(insertDataSql);

                    // Create a new health data entry and add it to the user's health data
                    String name = "Custom Health Note";
                    Date date = new Date();
                    Date originalDate = date;

                    CustomHealthData healthDataEntry = new CustomHealthData(name, originalDate, note);
                    user.addHealthData(healthDataEntry);
                    customStage.close();
                    Platform.runLater(() -> {
                        showHealthDataEntryScene();
                        showSuccessMessage();
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        customStage.setScene(customHealthNoteScene);
        customStage.show();
        return customHealthNoteScene;
    }



    /**
     * Shows the history screen with the user's health data entries.
     * Precondition: Button exist and click-able
     * POSTCONDITION: The history screen is shown on the primary stage.
     */
    private void showHistoryScreen() {
        HistoryScreen historyScreen = new HistoryScreen(user);
        historyScreen.display();
    }
    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Health data entry saved successfully!");
        alert.showAndWait();
    }

    

    
}
