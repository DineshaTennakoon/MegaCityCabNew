package com.example.megacitycab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LogoutController {

    @FXML
    private Button btnYes;  // Reference the Yes button
    @FXML
    private Button btnNo;   // Reference the No button

    @FXML
    private void handleYes() {
        System.out.println("Logging out...");
        // Close the confirmation window
        Stage stage = (Stage) btnYes.getScene().getWindow();
        stage.close(); // Close the logout confirmation window

        // Perform logout and redirect to the login screen
        try {
            MainApp.loadLogin(); // Redirect to the login screen
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNo() {
        System.out.println("Canceling logout...");
        // Close the logout confirmation window
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close(); // Close the logout confirmation window

        // Go back to the previous screen (e.g., Dashboard)
        try {
            MainApp.loadDashboard(); // Redirect to the Dashboard page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
