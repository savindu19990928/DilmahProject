package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fineID;

    @ManyToOne
    @JoinColumn(name = "hostellerID", nullable = false)
    private Hosteller hosteller;
    
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User creator;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean paid;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime issued;

    public long getFineID() {
        return fineID;
    }

    public void setFineID(long fineID) {
        this.fineID = fineID;
    }

    public Hosteller getHosteller() {
        return hosteller;
    }

    public void setHosteller(Hosteller hosteller) {
        this.hosteller = hosteller;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDateTime getIssued() {
        return issued;
    }

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
}
