package com.example.megacitycab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DashboardController {

    @FXML
    private void handleCustomerBookings(ActionEvent event) throws Exception {
        MainApp.loadCustomerBookings();
    }

    @FXML
    private void handleDriverCarManagement(ActionEvent event) throws Exception {
        MainApp.loadDriverCarManagement();
    }

    @FXML
    private void handleBillingSystem(ActionEvent event) throws Exception {
        MainApp.loadBillingSystem();
    }

    @FXML
    private void handleLogout(ActionEvent event) throws Exception {
        MainApp.loadLogout();
    }
}
