package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderID;

    @Column(length = 15, nullable = false)
    private String orderType;

    @Column(nullable = false)
    private LocalDateTime orderedDate;
    
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "foodOrder", cascade = CascadeType.DETACH)
    private List<FoodMenu> foodMenus;
    
    @OneToMany(mappedBy = "foodOrder", cascade = CascadeType.DETACH)
    private List<Fee> fees;

    public User getUser() {
		return user;
	}

	public List<FoodMenu> getFoodMenus() {
		return foodMenus;
	}

	public void setFoodMenus(List<FoodMenu> foodMenus) {
		this.foodMenus = foodMenus;
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

	public List<Fee> getFees() {
		return fees;
	}

	public void setFees(List<Fee> fees) {
		this.fees = fees;
	}
}
