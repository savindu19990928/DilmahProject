package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instructionID;

    @Column(length = 25, nullable = false)
    private String title;

    @Column(length = 225, nullable = false)
    private String description;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // Getters and Setters
    public int getInstructionID() {
        return instructionID;
    }

    public void setInstructionID(int instructionID) {
        this.instructionID = instructionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
