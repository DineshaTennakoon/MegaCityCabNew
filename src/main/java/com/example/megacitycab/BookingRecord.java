package com.example.megacitycab;

import javafx.beans.property.SimpleStringProperty;

public class BookingRecord {

    private final SimpleStringProperty customerName;
    private final SimpleStringProperty bookingDate;

    public BookingRecord(String customerName, String bookingDate) {
        this.customerName = new SimpleStringProperty(customerName);
        this.bookingDate = new SimpleStringProperty(bookingDate);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public SimpleStringProperty bookingDateProperty() {
        return bookingDate;
    }
}
