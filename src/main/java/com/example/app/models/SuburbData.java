package com.example.app.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SuburbData {
    private Suburb suburb;
    private float temperature;
    private LocalDate date;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public SuburbData() {
    }

    public SuburbData(Suburb suburb, float temperature, LocalDate date) {
        this.suburb = suburb;
        this.temperature = temperature;
        this.date = date;
    }

    public Suburb getSuburb() {
        return this.suburb;
    }

    public void setSuburb(Suburb newSuburb) {
        this.suburb = newSuburb;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temp) {
        this.temperature = temp;
    }

    public void setTemperature(String temp) throws NullPointerException, NumberFormatException {
        this.temperature = Float.parseFloat(temp);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String date) throws DateTimeParseException {
        this.date = LocalDate.parse(date);
    }

    @Override
    public String toString() {
        return this.suburb.getName() + " was " + this.temperature + "C on " + this.date.toString();
    }
}
