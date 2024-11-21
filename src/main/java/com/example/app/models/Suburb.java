package com.example.app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Suburbs")
public class Suburb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cityId;
    private String name;

    public Suburb() {
    }

    public Suburb(Long id, Long cityId, String name) {
        this.id = id;
        this.cityId = cityId;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public Long getCityId() {
        return this.cityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) { this.name = newName; }

    @Override
    public String toString() {
        return this.name;
    }
}
