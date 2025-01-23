package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class FoodMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemID;

    @ManyToOne
    @JoinColumn(name = "orderID", nullable = false)
    private FoodOrder foodOrder;

    @Column(length = 15, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate menuDate;

    public Long getMenuItemID() {
        return menuItemID;
    }

    public void setMenuItemID(Long menuItemID) {
        this.menuItemID = menuItemID;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }
}
