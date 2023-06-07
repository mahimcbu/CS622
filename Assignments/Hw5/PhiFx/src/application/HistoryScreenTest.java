package application;

import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HistoryScreenTest {
    private static HistoryScreen historyScreen;

    @BeforeClass
    public static void setUp() {
        // Initialize JavaFX Toolkit
        JFXPanel panel = new JFXPanel();
        Stage stage = new Stage();

        // Create a sample user object for testing
        User<HealthData<?>> user = new User<>("John", "Doe", "john@example.com",
                "password", new Date(), "Male", "1234567890");

        // Create HistoryScreen object for testing
        historyScreen = new HistoryScreen(user);
    }

    @Test
    public void testCalculateAverageMetrics() {
        // Create sample health data for testing
        CommonHealthData healthData1 = new CommonHealthData("John Doe", new Date(), "BMI", 70, 1.8);
        CommonHealthData healthData2 = new CommonHealthData("John Doe", new Date(), "BMI", 80, 1.7);
        CommonHealthData healthData3 = new CommonHealthData("John Doe", new Date(), "Cholesterol", 120, 80, 150);
        CommonHealthData healthData4 = new CommonHealthData("John Doe", new Date(), "Blood Pressure", 120, 80);
        CustomHealthData healthData5 = new CustomHealthData("John Doe", new Date(), "Custom Note");

        // Add health data to the user's list
        historyScreen.data.addAll(healthData1, healthData2, healthData3, healthData4, healthData5);

        // Calculate the average metrics
        historyScreen.calculateAverageMetrics(historyScreen.data);

        // Verify the average metrics
        Label averageMetricsLabel = (Label) historyScreen.tableView.getScene().lookup("#averageMetricsLabel");
        assertNotNull(averageMetricsLabel);
        String actualText = averageMetricsLabel.getText();
        String expectedText = "Average BMI: 25.55\n" +
                "Average LDL: 120.00\n" +
                "Average HDL: 80.00\n" +
                "Average Systolic BP: 120.00\n" +
                "Average Systolic BP: 80.00\n";
        assertEquals(expectedText, actualText);
    }
}
