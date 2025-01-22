package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class HostellerFacilityProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int facilityProfileID;

    @ManyToOne
    @JoinColumn(name = "hostellerID", nullable = false)
    private Hosteller hosteller;

    @ManyToOne
    @JoinColumn(name = "facilityID", nullable = false)
    private Facility facility;
    
    @OneToMany(mappedBy = "hostellerFacilityProfile", cascade = CascadeType.DETACH)
    private List<BorrowedItem> borrowedItems;

    @Column(length = 15, nullable = false)
    private String status;

    @Column(length = 225, nullable = true)
    private String description;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinedAt;

    public int getFacilityProfileID() {
        return facilityProfileID;
    }

    public void setFacilityProfileID(int facilityProfileID) {
        this.facilityProfileID = facilityProfileID;
    }

    public Hosteller getHosteller() {
        return hosteller;
    }

    public void setHosteller(Hosteller hosteller) {
        this.hosteller = hosteller;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

	public List<BorrowedItem> getBorrowedItems() {
		return borrowedItems;
	}

	public void setBorrowedItems(List<BorrowedItem> borrowedItems) {
		this.borrowedItems = borrowedItems;
	}
}
