package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class BorrowedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int borrowedID;

    @ManyToOne
    @JoinColumn(name = "facilityProfileID", nullable = false)
    private HostellerFacilityProfile hostellerFacilityProfile;
    
    @OneToOne
    @JoinColumn(name = "itemID", nullable = false)
    private FacilityItem facilityItem;

    @Column(length = 225, nullable = false)
    private String description;

    @Column(length = 15, nullable = false)
    private String status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime borrowedAt;

    @Column(nullable = true)
    private LocalDateTime returnedAt;

    public int getBorrowedID() {
        return borrowedID;
    }

    public void setBorrowedID(int borrowedID) {
        this.borrowedID = borrowedID;
    }

    public HostellerFacilityProfile getHostellerFacilityProfile() {
        return hostellerFacilityProfile;
    }

    public void setHostellerFacilityProfile(HostellerFacilityProfile hostellerFacilityProfile) {
        this.hostellerFacilityProfile = hostellerFacilityProfile;
    }

    public FacilityItem getFacilityItem() {
		return facilityItem;
	}

	public void setFacilityItem(FacilityItem facilityItem) {
		this.facilityItem = facilityItem;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}
