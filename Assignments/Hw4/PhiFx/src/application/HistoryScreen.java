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
        metricColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMetric()));

        TableColumn<HealthData<?>, Boolean> editColumn = new TableColumn<>("Edit");
        editColumn.setCellValueFactory(param -> new SimpleBooleanProperty(true));
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                    editButton.setOnAction(event -> {
                        HealthData<?> healthData = getTableView().getItems().get(getIndex());
                        editHealthData(healthData);
                    });
                }
            }
        });

        // Create the table view
        tableView = new TableView<>();
        tableView.getColumns().addAll(nameColumn, dateColumn, metricColumn, editColumn);
        tableView.setItems(data);

        // Create a save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            saveEditedHealthData();
        });

        // Create a layout container
        VBox root = new VBox(tableView, saveButton);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void editHealthData(HealthData<?> healthData) {
        // You can implement the logic to edit the health data here
        // For simplicity, let's just print the selected health data for now
        System.out.println("Edit Health Data: " + healthData);
    }

    private void saveEditedHealthData() {
        // You can implement the logic to save the edited health data here
        // For simplicity, let's just print the data to be saved for now
        for (HealthData<?> healthData : data) {
            System.out.println("Save Edited Health Data: " + healthData);
        }
    }
}