package com.dilmah.hostel.model;

import jakarta.persistence.*;

@Entity
public class FacilityAdmin extends User {

    @OneToOne
    @JoinColumn(name = "facilityID", nullable = false)
    private Facility facility;

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}
}

