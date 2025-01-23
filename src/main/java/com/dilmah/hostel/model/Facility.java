package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int facilityID;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 225, nullable = false)
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "facility", cascade = CascadeType.DETACH)
    private List<FacilityIncomeExpense> finances;
    
    @OneToMany(mappedBy = "facility", cascade = CascadeType.DETACH)
    private List<FacilityItem> items;
    
    @OneToMany(mappedBy = "facility", cascade = CascadeType.DETACH)
    private List<HostellerFacilityProfile> facilityProfile;
    
    @OneToMany(mappedBy = "facility", cascade = CascadeType.DETACH)
    private List<Subscription> subscriptions;

	// Getters and setters
    public int getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

    public List<FacilityItem> getItems() {
		return items;
	}

	public void setItems(List<FacilityItem> items) {
		this.items = items;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

	public List<FacilityIncomeExpense> getFinances() {
		return finances;
	}

	public void setFinances(List<FacilityIncomeExpense> finances) {
		this.finances = finances;
	}

	public List<HostellerFacilityProfile> getFacilityProfile() {
		return facilityProfile;
	}

	public void setFacilityProfile(List<HostellerFacilityProfile> facilityProfile) {
		this.facilityProfile = facilityProfile;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
}
