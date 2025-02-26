package com.dilmah.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderID;
    
    @Column(length = 30, nullable = false)
    @Pattern(regexp = "^(breakfast|lunch|dinner)$", message = "Order type must be either 'Breakfast', 'Lunch', or 'Dinner'")
    private String orderType;
    
    @Column(nullable = false)
    private LocalDateTime orderedDate;
    
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    public User getUser() {
		return user;
	}
    
	public void setUser(User user) {
		this.user = user;
	}

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public LocalDateTime getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDateTime orderedDate) {
        this.orderedDate = orderedDate;
    }
}
