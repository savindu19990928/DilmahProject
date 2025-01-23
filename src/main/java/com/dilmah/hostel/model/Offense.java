package com.dilmah.hostel.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
public class Offense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offenseID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private Hosteller hosteller;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 225, nullable = false)
    private String description;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public int getOffenseID() {
        return offenseID;
    }

    public void setOffenseID(int offenseID) {
        this.offenseID = offenseID;
    }

    public Hosteller getHosteller() {
        return hosteller;
    }

    public void setHosteller(Hosteller hosteller) {
        this.hosteller = hosteller;
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
}
