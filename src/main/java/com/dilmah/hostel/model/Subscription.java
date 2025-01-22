package com.dilmah.hostel.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

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

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.DETACH)
    private List<Service> services;
    
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.DETACH)
    private List<Fee> fees;

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
}
