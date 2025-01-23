package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feeID;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private FoodOrder foodOrder;
    
    @ManyToOne
    @JoinColumn(name = "userID")
    private Hosteller hosteller;

    @ManyToOne
    @JoinColumn(name = "subscriptionID")
    private Subscription subscription;

    @Column(length = 225, nullable = false)
    private String reason;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean paid;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime issued;

    public long getFeeID() {
        return feeID;
    }

    public void setFeeID(long feeID) {
        this.feeID = feeID;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public Hosteller getHosteller() {
		return hosteller;
	}

	public void setHosteller(Hosteller hosteller) {
		this.hosteller = hosteller;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
}
