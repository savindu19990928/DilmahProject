package com.dilmah.hostel.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Hosteller {

    @Id
    @Column(length = 15, nullable = false, unique = true)
    private String userID;

    @Column(length = 25, nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "roomNumber")
    private Room room;

    @Column(length = 15, nullable = false)
    private String password;

    @Column(length = 12, nullable = false)
    private String mobile;
    
    @OneToMany(mappedBy = "hostellers", cascade = CascadeType.DETACH)
    private List<Guardian> gurdians;
    
    @Column(nullable = false)
    private BigDecimal salary;

    private Boolean isSick = false;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime assigned;

    // Getters and setters
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Boolean getIsSick() {
        return isSick;
    }

    public void setIsSick(Boolean isSick) {
        this.isSick = isSick;
    }

    public LocalDateTime getAssigned() {
        return assigned;
    }

    public void setAssigned(LocalDateTime assigned) {
        this.assigned = assigned;
    }
}
