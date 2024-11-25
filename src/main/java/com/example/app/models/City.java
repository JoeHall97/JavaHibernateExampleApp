package com.example.app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Cities")
public class City {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   @Column(name = "created_at")
   private java.sql.Timestamp createdOn;

   public City() {
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) { this.name = name; }

   @Override
   public String toString() {
      return name;
   }
}
