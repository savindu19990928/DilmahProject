package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class InOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inOutID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private Hosteller hosteller;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime outTime;

    @Column(nullable = true)
    private LocalDateTime returnedTime;
    
    @Column(nullable = true)
    private LocalDateTime leavedTime;
    
    @Column(nullable = false)
    private LocalDateTime inTime;

    @Column(length = 5, nullable = false)
    private String otp1;
    
    @Column(length = 5, nullable = false)
    private String otp2;
    
    @Column(nullable = true)
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

	public String getOtp1() {
		return otp1;
	}

	public void setOtp1(String otpIn) {
		this.otp1 = otpIn;
	}

	public String getOtp2() {
		return otp2;
	}

	public void setOtp2(String otpOut) {
		this.otp2 = otpOut;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getInTime() {
		return inTime;
	}

	public void setInTime(LocalDateTime inTime) {
		this.inTime = inTime;
	}

	public LocalDateTime getApproved() {
		return approved;
	}

	public LocalDateTime getLeavedTime() {
		return leavedTime;
	}

	public void setLeavedTime(LocalDateTime leavedTime) {
		this.leavedTime = leavedTime;
	}
}
