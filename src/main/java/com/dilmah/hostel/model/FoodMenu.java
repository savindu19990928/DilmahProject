package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class FoodMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemID;

    @Column(length = 15, nullable = false)
    private String name;
    
    @Column(length = 15, nullable = false)
    private String orderType;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime menuDate;

    public Long getMenuItemID() {
        return menuItemID;
    }

    public void setMenuItemID(Long menuItemID) {
        this.menuItemID = menuItemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDateTime menuDate) {
        this.menuDate = menuDate;
    }

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
