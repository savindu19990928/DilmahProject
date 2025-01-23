package com.dilmah.hostel.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @Column(length = 15, nullable = false, unique = true)
    private String userID;
    
    @Column(length = 25, nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private String password;
    
    @Column(length = 12, nullable = false)
    private String contactDetails;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime assigned;
    
    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private List<Fine> fineCreations;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public LocalDateTime getAssigned() {
		return assigned;
	}

	public List<Fine> getFineCreations() {
		return fineCreations;
	}

	public void setFineCreations(List<Fine> fineCreations) {
		this.fineCreations = fineCreations;
	}
}
