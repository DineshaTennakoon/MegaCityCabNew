package com.example.megacitycab;

import javafx.beans.property.SimpleStringProperty;

public class BillingRecord {

    private final SimpleStringProperty customerName;
    private final SimpleStringProperty amountDue;

    public BillingRecord(String customerName, String amountDue) {
        this.customerName = new SimpleStringProperty(customerName);
        this.amountDue = new SimpleStringProperty(amountDue);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public String getAmountDue() {
        return amountDue.get();
    }

    public SimpleStringProperty amountDueProperty() {
        return amountDue;
    }
}
