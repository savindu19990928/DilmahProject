package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class InOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inOutID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private Hosteller hosteller;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime outTime;

    @Column(nullable = false)
    private LocalDateTime returnedTime;

    @Column(length = 10, nullable = false)
    private String otpIn;
    
    @Column(length = 10, nullable = false)
    private String otpOut;
    
    private LocalDateTime approved;

    public long getInOutID() {
        return inOutID;
    }

    public void setInOutID(long inOutID) {
        this.inOutID = inOutID;
    }

    public Hosteller getHosteller() {
        return hosteller;
    }

    public void setHosteller(Hosteller hosteller) {
        this.hosteller = hosteller;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalDateTime outTime) {
        this.outTime = outTime;
    }

    public LocalDateTime getReturnedTime() {
        return returnedTime;
    }

    public void setReturnedTime(LocalDateTime returnedTime) {
        this.returnedTime = returnedTime;
    }

    public LocalDateTime isApproved() {
        return approved;
    }

    public void setApproved(LocalDateTime approved) {
        this.approved = approved;
    }

	public String getOtpIn() {
		return otpIn;
	}

	public void setOtpIn(String otpIn) {
		this.otpIn = otpIn;
	}

	public String getOtpOut() {
		return otpOut;
	}

	public void setOtpOut(String otpOut) {
		this.otpOut = otpOut;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
