module com.example.megacitycab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.megacitycab to javafx.fxml;
    exports com.example.megacitycab;
}