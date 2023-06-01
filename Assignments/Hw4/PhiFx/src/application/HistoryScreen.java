package application;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

import PHI.*;

public class HistoryScreen {
    private User<HealthData<?>> user;
    private TableView<HealthData<?>> tableView;
    private ObservableList<HealthData<?>> data;

    public HistoryScreen(User<HealthData<?>> user) {
        this.user = user;
        data = FXCollections.observableArrayList(user.getHealthDataList());
    }

    public void display() {
        Stage stage = new Stage();
        stage.setTitle("Health Data History");

        // Create columns for the table
        TableColumn<HealthData<?>, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));

        TableColumn<HealthData<?>, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDate().toString()));

        TableColumn<HealthData<?>, String> metricColumn = new TableColumn<>("Metric");
        metricColumn.setCellValueFactory(param -> new SimpleStringProperty(getMetricValue(param.getValue())));

        metricColumn.setCellFactory(column -> {
            return new TableCell<HealthData<?>, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
        });

        TableColumn<HealthData<?>, Boolean> editColumn = new TableColumn<>("Edit");
        editColumn.setCellValueFactory(param -> new SimpleBooleanProperty(true));
        editColumn.setCellFactory(param -> new TableCell<HealthData<?>, Boolean>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    HealthData<?> healthData = getTableView().getItems().get(getIndex());
                    Stage editStage = new Stage();
                    editStage.setTitle("Edit Health Data");

                    HealthDataEntry entryScreen = new HealthDataEntry(editStage, user);
                    entryScreen.setCurrentHealthData(healthData); // Set the current health data

                    if (healthData instanceof CommonHealthData) {
                        CommonHealthData commonHealthData = (CommonHealthData) healthData;
                        String metric = commonHealthData.getMetric();

                        if (metric.equals("Blood Pressure")) {
                            Scene scene = entryScreen.showBloodPressureScene();
                            editStage.setScene(scene);
                            
                        } else if (metric.equals("Blood Glucose")) {
                            Scene scene = entryScreen.showBloodSugarScene();
                            editStage.setScene(scene);
                        } else if (metric.equals("BMI")) {
                            Scene scene = entryScreen.showBMIScene();
                            editStage.setScene(scene);
                        } else if (metric.equals("Cholesterol")) {
                            Scene scene = entryScreen.showCholesterolScene();
                            editStage.setScene(scene);
                        }
                    } else if (healthData instanceof CustomHealthData) {
                        Scene scene = entryScreen.showCustomHealthNoteScene();
                        editStage.setScene(scene);
                    }

                    // Show the edit stage after setting the scene
                    editStage.show();
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });


        // Create the table view
        tableView = new TableView<>();
        tableView.getColumns().addAll(nameColumn, dateColumn, metricColumn, editColumn);
        tableView.setItems(data);

        // Create a back button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            stage.close();
        });

        // Create a layout container
        VBox root = new VBox(tableView, backButton);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String getMetricValue(HealthData<?> healthData) {
        if (healthData instanceof CommonHealthData) {
            CommonHealthData commonHealthData = (CommonHealthData) healthData;
            String metric = commonHealthData.getMetric();

            if (metric.equals("Blood Pressure")) {
                int systolic = commonHealthData.getSystolicBP();
                int diastolic = commonHealthData.getDiastolicBP();
                return "Systolic: " + systolic + ", Diastolic: " + diastolic;
            } else if (metric.equals("Cholesterol")) {
                int ldl = commonHealthData.getLdlCholesterol();
                int hdl = commonHealthData.getHdlCholesterol();
                int triglycerides = commonHealthData.getTriglycerideCholesterol();
                return "LDL: " + ldl + ", HDL: " + hdl + ", Triglycerides: " + triglycerides;
            } else if (metric.equals("BMI")) {
                double weight = commonHealthData.getWeight();
                double height = commonHealthData.getHeight();
                double bmi = commonHealthData.calculateBMI();
                return "Weight: " + weight + ", Height: " + height + ", BMI: " + bmi;
            } else if (metric.equals("Blood Glucose")) {
                double glucoseLevel = commonHealthData.getGlucoseLevel();
                return "Glucose Level: " + glucoseLevel;
            } else {
                return "";
            }
        } else if (healthData instanceof CustomHealthData) {
            CustomHealthData customHealthData = (CustomHealthData) healthData;
            return "Custom note: " + customHealthData.getData(); // Return the metric value directly
        } else {
            return "";
        }
    }
}
