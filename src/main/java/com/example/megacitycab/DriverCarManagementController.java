package com.example.megacitycab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverCarManagementController {

    @FXML
    private TextField txtDriverName;

    @FXML
    private TextField txtCarModel;

    @FXML
    private Button btnBackToDashboard;

    @FXML
    private TableView<DriverCarRecord> driverCarTable;

    @FXML
    private TableColumn<DriverCarRecord, String> driverNameColumn;

    @FXML
    private TableColumn<DriverCarRecord, String> carModelColumn;

    private ObservableList<DriverCarRecord> driverCarData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        driverNameColumn.setCellValueFactory(cellData -> cellData.getValue().driverNameProperty());
        carModelColumn.setCellValueFactory(cellData -> cellData.getValue().carModelProperty());

        loadDriverCarData(); // Load data from MySQL when app starts
    }

    private void loadDriverCarData() {
        driverCarData.clear(); // Clear existing data
        String query = "SELECT driver_name, car_model FROM DriverCar";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                driverCarData.add(new DriverCarRecord(rs.getString("driver_name"), rs.getString("car_model")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        driverCarTable.setItems(driverCarData);
    }

    @FXML
    private void handleAddDriverCar() {
        String driverName = txtDriverName.getText();
        String carModel = txtCarModel.getText();

        if (!driverName.isEmpty() && !carModel.isEmpty()) {
            saveDriverCarToDatabase(driverName, carModel);
            loadDriverCarData(); // Refresh table
        }

        txtDriverName.clear();
        txtCarModel.clear();
    }

    private void saveDriverCarToDatabase(String driverName, String carModel) {
        String query = "INSERT INTO DriverCar (driver_name, car_model) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, driverName);
            stmt.setString(2, carModel);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/megacitycab/Dashboard.fxml"));
            Parent dashboardParent = loader.load();
            Scene dashboardScene = new Scene(dashboardParent);

            Stage currentStage = (Stage) btnBackToDashboard.getScene().getWindow();
            currentStage.setScene(dashboardScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
