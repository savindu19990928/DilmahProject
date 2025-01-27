package com.dilmah.hostel.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriptionID;

    @ManyToOne
    @JoinColumn(name = "facilityID", nullable = false)
    private Facility facility;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 225, nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal fee;

    @JsonIgnore
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.DETACH)
    private List<Service> services;
    
    @JsonIgnore
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.DETACH)
    private List<Fee> fees;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public int getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(int subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public List<Fee> getFees() {
		return fees;
	}

	public void setFees(List<Fee> fees) {
		this.fees = fees;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
