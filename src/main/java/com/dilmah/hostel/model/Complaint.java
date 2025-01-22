package com.dilmah.hostel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int complaintID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private Hosteller hosteller;

    @Column(length = 15, nullable = false)
    private String type;

    @Column(length = 225, nullable = false)
    private String description;

    @Column(length = 10, nullable = false)
    private String status;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt;

    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

    public Hosteller getHosteller() {
        return hosteller;
    }

    public void setHosteller(Hosteller hosteller) {
        this.hosteller = hosteller;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
