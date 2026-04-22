package com.project.models;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 🔥 Optional but useful
    private String description;

    // 🔗 One department → many complaints
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore // 🔥 prevents infinite loop (important)
    private List<Complaint> complaints;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }
}