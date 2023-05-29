package PHI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HealthMetricsReaderTest {

    @Test
    public void testReadIdealValue() {
        double[] idealValues = HealthMetricsReader.readIdealValue("Systolic Blood Pressure");
        assertEquals(2, idealValues.length);
        assertEquals(80.0, idealValues[0]);
        assertEquals(120.0, idealValues[1]);

        idealValues = HealthMetricsReader.readIdealValue("LDL Cholesterol");
        assertEquals(2, idealValues.length);
        assertEquals(0.0, idealValues[0]);
        assertEquals(100.0, idealValues[1]);

        idealValues = HealthMetricsReader.readIdealValue("BMI");
        assertEquals(2, idealValues.length);
        assertEquals(18.5, idealValues[0]);
        assertEquals(24.9, idealValues[1]);

        idealValues = HealthMetricsReader.readIdealValue("Nonexistent Metric");
        assertEquals(2, idealValues.length);
        assertEquals(0.0, idealValues[0]);
        assertEquals(0.0, idealValues[1]);
    }
}
