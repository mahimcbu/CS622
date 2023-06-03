package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.Date;

public class HealthDataEntry {
    private Stage primaryStage;
    private Scene healthDataEntryScene;
    private Scene historyScene;
    private User<HealthData<?>> user;
    private HealthData<?> currentHealthData;
    
    
    public void setCurrentHealthData(HealthData<?> currentHealthData) {
        this.currentHealthData = currentHealthData;
    }
    

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public User<HealthData<?>> getUser() {
        return user;
    }
    private HealthData<CommonHealthData> healthData;

    public HealthDataEntry(Stage primaryStage, User<HealthData<?>> user) {
        this.primaryStage = primaryStage;
        this.user = user;
//        healthData = new HealthData<>();

        createHealthDataEntryScene();
//        createHistoryScene();
    }

    public void showHealthDataEntryScene() {
        primaryStage.setScene(healthDataEntryScene);
        primaryStage.setTitle("Health Data Entry");
        primaryStage.show();
    }

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
            showBloodPressureScene();
        });

        // Handle cholesterol button click event
        cholesterolButton.setOnAction(event -> {
            showCholesterolScene();
        });

        // Handle BMI button click event
        bmiButton.setOnAction(event -> {
            showBMIScene();
        });

        // Handle blood sugar button click event
        bloodSugarButton.setOnAction(event -> {
            showBloodSugarScene();
        });

        // Handle custom health note button click event
        customHealthNoteButton.setOnAction(event -> {
            showCustomHealthNoteScene();
        });

        // Handle history button click event
        historyButton.setOnAction(event -> {
        	showHistoryScreen();
        });
    }

    public Scene showBloodPressureScene() {
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
            String name = "Blood Pressure";
            Date date = new Date();
            Date originalDate = date;
            String metric = "Blood Pressure";
            int systolicBP = Integer.parseInt(systolicBPTextField.getText());
            int diastolicBP = Integer.parseInt(diastolicBPTextField.getText());

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
            showSuccessMessage();
        });

        primaryStage.setScene(bloodPressureScene);
        return bloodPressureScene;
    }

    public Scene showCholesterolScene() {
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
            String name = "Cholesterol";
            Date date = new Date();
            Date originalDate = date;
            String metric = "Cholesterol";
            int ldlCholesterol = Integer.parseInt(ldlCholesterolTextField.getText());
            int hdlCholesterol = Integer.parseInt(hdlCholesterolTextField.getText());
            int triglycerideCholesterol = Integer.parseInt(triglycerideCholesterolTextField.getText());

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
            showSuccessMessage();
        });

        primaryStage.setScene(cholesterolScene);
        return cholesterolScene;
    }

    public Scene showBMIScene() {
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
            String name = "BMI";
            Date date = new Date();
            Date originalDate = date;

            String metric = "BMI";
            double weight = Double.parseDouble(weightTextField.getText());
            double height = Double.parseDouble(heightTextField.getText());

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
            showSuccessMessage();
        });

        primaryStage.setScene(bmiScene);
        return bmiScene;
    }

    public Scene showBloodSugarScene() {
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
            String name = "Blood Glucose";
            Date date = new Date();
            Date originalDate = date;

            String metric = "Blood Glucose";
            double glucoseLevel = Double.parseDouble(glucoseLevelTextField.getText());

            CommonHealthData healthDataEntry = new CommonHealthData(name, originalDate, metric, glucoseLevel);
            try {
				((CommonHealthData) healthDataEntry).validate();
			} catch (HealthDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            user.addHealthData(healthDataEntry);
            HealthDataChecker.checkBloodGlucose(healthDataEntry);


            showHealthDataEntryScene();
            showSuccessMessage();
        });

        primaryStage.setScene(bloodSugarScene);
        return bloodSugarScene;
    }

    public Scene showCustomHealthNoteScene() {
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

        // Handle submit button click event
        submitButton.setOnAction(event -> {
            String name = "Custom Health Note";
            Date date = new Date();
            Date originalDate = date;

//            String metric = "Note";
            String note = noteTextField.getText();

            CustomHealthData healthDataEntry = new CustomHealthData(name, originalDate, note);
            user.addHealthData(healthDataEntry);
//            healthData.addHealthDataEntry(healthDataEntry);

            showHealthDataEntryScene();
            showSuccessMessage();
        });

        primaryStage.setScene(customHealthNoteScene);
        return customHealthNoteScene;
    }

    private void showHistoryScene() {
        // Create UI components for history scene
        Label titleLabel = new Label("Health Data History");

        StringBuilder historyText = new StringBuilder();
//        for (CommonHealthData entry : healthData.getHealthDataEntries()) {
//            historyText.append(entry.toString()).append("\n");
//        }

        Label historyLabel = new Label(historyText.toString());

        Button backButton = new Button("Back");

        // Create layout container for history scene
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, historyLabel, backButton);

        // Create history scene
        historyScene = new Scene(root, 400, 300);

        // Handle back button click event
        backButton.setOnAction(event -> {
            showHealthDataEntryScene();
        });

        primaryStage.setScene(historyScene);
    }
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
