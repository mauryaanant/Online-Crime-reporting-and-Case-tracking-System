package com.project.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String complaintId; // 🔥 unique tracking ID

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private String category;

    private String status;   // Pending, In Progress, Resolved
    private String priority; // Low, Medium, High

    private String evidencePath;

    private LocalDateTime createdAt;

    // 🔗 Many complaints → one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 🔗 Many complaints → one department
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // 🔥 Auto set time before saving
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public String getEvidencePath() {
        return evidencePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setEvidencePath(String evidencePath) {
        this.evidencePath = evidencePath;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}