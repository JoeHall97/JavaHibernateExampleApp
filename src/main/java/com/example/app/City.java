package com.example.app;

import jakarta.persistence.*;

@Entity
@Table(name = "Cities")
public class City {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   private String name;

   public City() {}

   public Long getId() { return id; }

   public String getName() { return name; }

   @Override
   public String toString() { return name; }
}
