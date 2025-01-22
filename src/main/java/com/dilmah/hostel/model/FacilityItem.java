package com.dilmah.hostel.model;

import jakarta.persistence.*;

@Entity
public class FacilityItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemID;

    @ManyToOne
    @JoinColumn(name = "facilityID", nullable = false)
    private Facility facility;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 225, nullable = true)
    private String description;

    @Column(nullable = false)
    private float quantity;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
