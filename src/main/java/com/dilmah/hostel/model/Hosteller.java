package com.dilmah.hostel.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Hosteller extends User {
    @ManyToOne
    @JoinColumn(name = "roomNumber")
    private Room room;
    
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Guardian> gurdians;
    
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Complaint> complaints;
    
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Fee> fees;
    
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Offense> Offense;
    
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<Fine> fines;
    
    @OneToMany(mappedBy = "hosteller", cascade = CascadeType.DETACH)
    private List<HostellerFacilityProfile> facilityProfile;
    
    @Column(nullable = false)
    private BigDecimal salary;

    @Column(nullable = false)
    private Boolean isSick = false;

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

	public Boolean getIsSick() {
		return isSick;
	}

	public void setIsSick(Boolean isSick) {
		this.isSick = isSick;
	}

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	public List<Fee> getFees() {
		return fees;
	}

	public void setFees(List<Fee> fees) {
		this.fees = fees;
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
}
