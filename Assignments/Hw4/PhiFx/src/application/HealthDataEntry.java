package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.Date;

public class HealthDataEntry {
    private Stage primaryStage;
    private Scene healthDataEntryScene;
    private TableView<HealthData<?>> tableView;
    private User<HealthData<?>> user;
 



    public void setCurrentHealthData(HealthData<?> currentHealthData) {
    }
    

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
            if (isEdit) {
            	if (existingHealthData instanceof CommonHealthData) {
                    CommonHealthData commonHealthData = (CommonHealthData) existingHealthData;
                    commonHealthData.setSystolicBP(systolicBP);
                    commonHealthData.setDiastolicBP(diastolicBP);
                    try {
                        commonHealthData.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }
                }
                tableView.refresh(); // Refresh the table view to reflect the changes
                Stage primaryStage = (Stage) bloodPressureScene.getWindow();
                primaryStage.close();
            } else {
            String name = "Blood Pressure";
            Date date = new Date();
            Date originalDate = date;
            String metric = "Blood Pressure";
    

            CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, systolicBP, diastolicBP);
            try {
				((CommonHealthData) healthDataEntry).validate();
			} catch (HealthDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            user.addHealthData(healthDataEntry);
            HealthDataChecker.checkBloodPressure(healthDataEntry);


            showHealthDataEntryScene();
            }
        });

        primaryStage.setScene(bloodPressureScene);
        return bloodPressureScene;
    }
    /**
     * Shows the Cholesterol scene for data entry.
     * Precondition: Button exist and click-able
     * POSTCONDITION: A new Scene object for cholesterol entry is created and returned.
     */
    public Scene showCholesterolScene(CommonHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        // Create UI components for cholesterol scene
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
                    try {
                        commonHealthData.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }
                }
                tableView.refresh(); // Refresh the table view to reflect the changes
                Stage primaryStage = (Stage) cholesterolScene.getWindow();
                primaryStage.close();
            } else {
            String name = "Cholesterol";
            Date date = new Date();
            Date originalDate = date;
            String metric = "Cholesterol";


            CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, ldlCholesterol,
                    hdlCholesterol, triglycerideCholesterol);
//            healthData.addHealthDataEntry(healthDataEntry);
            try {
				((CommonHealthData) healthDataEntry).validate();
			} catch (HealthDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            user.addHealthData(healthDataEntry);
            HealthDataChecker.checkCholesterol(healthDataEntry);


            showHealthDataEntryScene();
            }
        });

        primaryStage.setScene(cholesterolScene);
        return cholesterolScene;
    }
    /**
     * Shows the BMI scene for data entry.
     * Precondition: Button exist and click-able
     * POSTCONDITION: A new Scene object for BMI entry is created and returned.
     */
    public Scene showBMIScene(CommonHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        // Create UI components for BMI scene
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
            if (isEdit) {
            	if (existingHealthData instanceof CommonHealthData) {
                    CommonHealthData commonHealthData = (CommonHealthData) existingHealthData;
                    commonHealthData.setWeight(weight);
                    commonHealthData.setHeight(height);
                    try {
                        commonHealthData.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }
                }
                tableView.refresh(); // Refresh the table view to reflect the changes
                Stage primaryStage = (Stage) bmiScene.getWindow();
                primaryStage.close();
            } else {
            String name = "BMI";
            Date date = new Date();
            Date originalDate = date;

            String metric = "BMI";


            CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, weight, height);
            try {
				((CommonHealthData) healthDataEntry).validate();
			} catch (HealthDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            user.addHealthData(healthDataEntry);
            HealthDataChecker.checkBMI(healthDataEntry);
            showHealthDataEntryScene();
            }
        });

        primaryStage.setScene(bmiScene);
        return bmiScene;
    }


    /**
     * Shows the blood sugar scene for data entry.
     * Precondition: Button exist and click-able
     * POSTCONDITION: A new Scene object for blood sugar entry is created and returned.
     */
    public Scene showBloodSugarScene(CommonHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        // Create UI components for blood sugar scene
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
            double glucoseLevel = Double.parseDouble(glucoseLevelTextField.getText());

            if (isEdit) {
            	if (existingHealthData instanceof CommonHealthData) {
                    CommonHealthData commonHealthData = (CommonHealthData) existingHealthData;
                    commonHealthData.setGlucoseLevel(glucoseLevel);
                    try {
                        commonHealthData.validate();
                    } catch (HealthDataException e) {
                        e.printStackTrace();
                    }
                }
            	
                tableView.refresh(); // Refresh the table view to reflect the changes
                Stage primaryStage = (Stage) bloodSugarScene.getWindow();
                primaryStage.close();
            } else {
                // Create a new health data entry
                String name = "Blood Glucose";
                Date date = new Date();
                Date originalDate = date;

                String metric = "Blood Glucose";

                // Create a new CommonHealthData object
                CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, glucoseLevel);
                try {
                    healthDataEntry.validate();
                } catch (HealthDataException e) {
                    e.printStackTrace();
                }
                user.addHealthData(healthDataEntry);
                HealthDataChecker.checkBloodGlucose(healthDataEntry);
                showHealthDataEntryScene();

            }
            
            

        });

        primaryStage.setScene(bloodSugarScene);
        return bloodSugarScene;
    }

    /**
     * Shows the custom health note scene for data entry.
     * Precondition: Button exist and click-able
     * POSTCONDITION: A new Scene object for custom health note entry is created and returned.
     */
    public Scene showCustomHealthNoteScene(CustomHealthData existingHealthData, boolean isEdit, TableView<HealthData<?>> tableView) {
        // Create UI components for custom health note scene
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
        String note = noteTextField.getText();
        // Handle submit button click event
        submitButton.setOnAction(event -> {
        	   if (isEdit) {
               	if (existingHealthData instanceof CustomHealthData) {
                       CustomHealthData customHealthData = (CustomHealthData) existingHealthData;
//                       customHealthData.setNotes(note);

                   }
                   tableView.refresh(); // Refresh the table view to reflect the changes
                   Stage primaryStage = (Stage) customHealthNoteScene.getWindow();
                   primaryStage.close();
               } else {
            String name = "Custom Health Note";
            Date date = new Date();
            Date originalDate = date;

//            String metric = "Note";
            

            CustomHealthData healthDataEntry = new CustomHealthData(name, originalDate, note);
            user.addHealthData(healthDataEntry);
//            healthData.addHealthDataEntry(healthDataEntry);

            showHealthDataEntryScene();
            showSuccessMessage();
               }
        });

        primaryStage.setScene(customHealthNoteScene);
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
