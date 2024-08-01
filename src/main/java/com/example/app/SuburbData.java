package com.example.app;

import java.time.LocalDate;

public class SuburbData 
{
    private String name;
    private float temperature;
    private LocalDate date;

    public SuburbData () { }

    public SuburbData(String name, float temperature, LocalDate date)
    {
        this.name = name;
        this.temperature = temperature;
        this.date = date;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getTemperature() { return temperature; }
    public void setTemperature(float temp) { this.temperature = temp; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString()
    {
        return name + " was " + temperature + " on " + date.toString();
    }
}
