package com.example.megacitycab;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DriverCarRecord {
    private final StringProperty driverName;
    private final StringProperty carModel;

    public DriverCarRecord(String driverName, String carModel) {
        this.driverName = new SimpleStringProperty(driverName);
        this.carModel = new SimpleStringProperty(carModel);
    }

    public StringProperty driverNameProperty() {
        return driverName;
    }

    public StringProperty carModelProperty() {
        return carModel;
    }
}
