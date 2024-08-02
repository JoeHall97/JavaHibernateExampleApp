package com.example.app;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SuburbData {
    private String name;
    private float temperature;
    private Date date;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public SuburbData() {
    }

    public SuburbData(String name, float temperature, Date date) {
        this.name = name;
        this.temperature = temperature;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temp) {
        this.temperature = temp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.name + " was " + this.temperature + "C on " + sdf.format(this.date);
    }
}
