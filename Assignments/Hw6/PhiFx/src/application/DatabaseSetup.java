package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void main(String[] args) {
        // SQLite database connection
        String url = "jdbc:sqlite:healthtracker.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
        	
            // Create User table
            String createUserTableSql = "CREATE TABLE IF NOT EXISTS User (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "firstName TEXT NOT NULL," +
                    "lastName TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "dateOfBirth TEXT NOT NULL," +
                    "gender TEXT NOT NULL," +
                    "phoneNumber TEXT" +
                    ")";
            stmt.execute(createUserTableSql);
            String createBloodPressureDataTableSql = "CREATE TABLE IF NOT EXISTS BloodPressureData (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER NOT NULL," +
                    "systolicBP INTEGER NOT NULL," +
                    "diastolicBP INTEGER NOT NULL," +
                    "dateRecorded TEXT NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES User(id)" +
                    ")";
            stmt.execute(createBloodPressureDataTableSql);
            String createCholesterolDataTableSql = "CREATE TABLE IF NOT EXISTS CholesterolData (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER NOT NULL," +
                    "ldlCholesterol REAL NOT NULL," +
                    "hdlCholesterol REAL NOT NULL," +
                    "triglycerideCholesterol REAL NOT NULL," +
                    "dateRecorded TEXT NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES User(id)" +
                    ")";
            stmt.execute(createCholesterolDataTableSql);
            String createBmiDataTableSql = "CREATE TABLE IF NOT EXISTS BmiData (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER NOT NULL," +
                    "weight REAL NOT NULL," +
                    "height REAL NOT NULL," +
                    "dateRecorded TEXT NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES User(id)" +
                    ")";
            stmt.execute(createBmiDataTableSql);
            String createDiabetesDataTableSql = "CREATE TABLE IF NOT EXISTS DiabetesData (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER NOT NULL," +
                    "bloodSugarLevel REAL NOT NULL," +
                    "dateRecorded TEXT NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES User(id)" +
                    ")";
            stmt.execute(createDiabetesDataTableSql);
            String createCustomHealthDataTableSql = "CREATE TABLE IF NOT EXISTS CustomHealthData (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER NOT NULL," +
                    "note TEXT NOT NULL," +
                    "dateRecorded TEXT NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES User(id)" +
                    ")";
            stmt.execute(createCustomHealthDataTableSql);

            System.out.println("Database and tables created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating database and tables: " + e.getMessage());
        }
    }
}
