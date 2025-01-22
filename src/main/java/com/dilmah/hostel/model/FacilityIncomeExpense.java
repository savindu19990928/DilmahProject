package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class FacilityIncomeExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inExDI;

    @ManyToOne
    @JoinColumn(name = "facilityID", nullable = false)
    private Facility facility;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private boolean isIncome;

    @Column(length = 225, nullable = true)
    private String description;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public long getInExDI() {
        return inExDI;
    }

    public boolean isIncome() {
		return isIncome;
	}

	public void setIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}

	public void setInExDI(long inExDI) {
        this.inExDI = inExDI;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
