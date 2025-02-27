package com.dilmah.hostel.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Hosteller extends User {
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "roomNumber")
    private Room room;
    
	@JsonIgnore
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Guardian> gurdians;
    
	@JsonIgnore
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Complaint> complaints;
    
	@JsonIgnore
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Offense> Offense;
    
	@JsonIgnore
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Fine> fines;
    
	@JsonIgnore
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<HostellerFacilityProfile> facilityProfile;
    
    @Column(nullable = false)
    private BigDecimal salary;
    
    @Column(nullable = true)
    private String photo;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Guardian> getGurdians() {
		return gurdians;
	}

	public void setGurdians(List<Guardian> gurdians) {
		this.gurdians = gurdians;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	public List<Fine> getFines() {
		return fines;
	}

	public void setFines(List<Fine> fines) {
		this.fines = fines;
	}

	public List<HostellerFacilityProfile> getFacilityProfile() {
		return facilityProfile;
	}

	public void setFacilityProfile(List<HostellerFacilityProfile> facilityProfile) {
		this.facilityProfile = facilityProfile;
	}

	public List<Offense> getOffense() {
		return Offense;
	}

	public void setOffense(List<Offense> offense) {
		Offense = offense;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
