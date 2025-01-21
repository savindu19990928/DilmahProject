package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    private Long roomNumber;

    @Column(length = 15, nullable = false)
    private String status;

    private Integer availability;

    @OneToMany(mappedBy = "room", cascade = CascadeType.DETACH)
    private List<Hosteller> hostellers;
    
    public Long getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Long roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public List<Hosteller> getHostellers() {
        return hostellers;
    }

    public void setHostellers(List<Hosteller> hostellers) {
        this.hostellers = hostellers;
    }
}

