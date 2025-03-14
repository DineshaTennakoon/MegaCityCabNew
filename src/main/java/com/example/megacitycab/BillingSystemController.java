package com.example.megacitycab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingSystemController {

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtAmountDue;

    @FXML
    private Button btnBackToDashboard;

    @FXML
    private TableView<BillingRecord> billingTable;

    @FXML
    private TableColumn<BillingRecord, String> customerNameColumn;

    @FXML
    private TableColumn<BillingRecord, String> amountDueColumn;

    private ObservableList<BillingRecord> billingData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize TableView columns
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        amountDueColumn.setCellValueFactory(cellData -> cellData.getValue().amountDueProperty());

        // Load billing records from the database
        loadBillingRecords();
    }

    /**
     * Loads billing records from the MySQL database and displays them in the TableView.
     */
    private void loadBillingRecords() {
        billingData.clear(); // Clear existing data

        String query = "SELECT customer_name, amount_due FROM BillingRecords";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                billingData.add(new BillingRecord(rs.getString("customer_name"), rs.getString("amount_due")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        billingTable.setItems(billingData);
    }

    /**
     * Handles the 'Generate Bill' action, validates input, stores the record in MySQL, and updates the TableView.
     */
    @FXML
    private void handleGenerateBill() {
        String customerName = txtCustomerName.getText().trim();
        String amountDue = txtAmountDue.getText().trim();

        if (customerName.isEmpty() || amountDue.isEmpty()) {
            System.out.println("Both fields are required.");
            return;
        }

        // Save to MySQL
        if (insertBillingRecord(customerName, amountDue)) {
            // Add the new bill to the table
            billingData.add(new BillingRecord(customerName, amountDue));
            billingTable.refresh();

            // Clear the input fields
            txtCustomerName.clear();
            txtAmountDue.clear();
        }
    }

    /**
     * Inserts a billing record into the MySQL database.
     *
     * @param customerName The name of the customer.
     * @param amountDue    The amount due.
     * @return true if successful, false otherwise.
     */
    private boolean insertBillingRecord(String customerName, String amountDue) {
        String query = "INSERT INTO BillingRecords (customer_name, amount_due) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerName);
            stmt.setBigDecimal(2, new java.math.BigDecimal(amountDue));

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
