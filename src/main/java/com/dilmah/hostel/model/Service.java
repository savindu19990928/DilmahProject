package com.dilmah.hostel.model;

import jakarta.persistence.*;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceID;

    @ManyToOne
    @JoinColumn(name = "subscriptionID", nullable = false)
    private Subscription subscription;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 225, nullable = false)
    private String description;

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
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
}
