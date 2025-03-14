package com.example.megacitycab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.*;

public class CustomerBookingsController {

    @FXML
    private TextField txtCustomerName;

    @FXML
    private DatePicker dpBookingDate;

    @FXML
    private Button btnBackToDashboard;

    @FXML
    private TableView<BookingRecord> bookingsTable;

    @FXML
    private TableColumn<BookingRecord, String> customerNameColumn;

    @FXML
    private TableColumn<BookingRecord, String> bookingDateColumn;

    private ObservableList<BookingRecord> bookingData = FXCollections.observableArrayList();

    private static final String URL = "jdbc:mysql://localhost:3306/megacitycab";
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = "";  // Replace with your MySQL password

    @FXML
    private void initialize() {
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        bookingDateColumn.setCellValueFactory(cellData -> cellData.getValue().bookingDateProperty());

        loadBookingsFromDatabase(); // Load existing records from MySQL
    }

    @FXML
    private void handleSubmitBooking() {
        String customerName = txtCustomerName.getText();
        String bookingDate = (dpBookingDate.getValue() != null) ? dpBookingDate.getValue().toString() : "";

        if (!customerName.isEmpty() && !bookingDate.isEmpty()) {
            if (saveBookingToDatabase(customerName, bookingDate)) {
                bookingData.add(new BookingRecord(customerName, bookingDate)); // Add to UI
            }
        }

        txtCustomerName.clear();
        dpBookingDate.setValue(null);
    }

    private boolean saveBookingToDatabase(String customerName, String bookingDate) {
        String query = "INSERT INTO customer_bookings (customer_name, booking_date) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerName);
            pstmt.setDate(2, Date.valueOf(bookingDate));

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadBookingsFromDatabase() {
        String query = "SELECT customer_name, booking_date FROM customer_bookings";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String name = rs.getString("customer_name");
                String date = rs.getDate("booking_date").toString();
                bookingData.add(new BookingRecord(name, date));
            }

            bookingsTable.setItems(bookingData);
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
