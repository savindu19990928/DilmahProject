package com.dilmah.hostel.model;

import jakarta.persistence.*;

@Entity
public class Warden extends User {
    @Column(length = 25, nullable = true)
    private String inChargeName;

    @Column(length = 12, nullable = true)
    private String inChargeMobile;

	public String getInChargeName() {
		return inChargeName;
	}

	public void setInChargeName(String inChargeName) {
		this.inChargeName = inChargeName;
	}

	public String getInChargeMobile() {
		return inChargeMobile;
	}

	public void setInChargeMobile(String inChargeMobile) {
		this.inChargeMobile = inChargeMobile;
	}
}

