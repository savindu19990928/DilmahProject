package com.dilmah.hostel.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Guardian {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long gid;
	
	@Column(length = 25, nullable = false)
	private String name;
	
	@Column(length = 12, nullable = false)
	private String mobile;
	
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime assigned;
    
    @ManyToOne
    @JoinColumn(name = "hosteller")
    private Hosteller hosteller;

	public long getGid() {
		return gid;
	}

	public void setGid(long gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public LocalDateTime getAssigned() {
		return assigned;
	}

	public void setAssigned(LocalDateTime assigned) {
		this.assigned = assigned;
	}

	public Hosteller getHosteller() {
		return hosteller;
	}

	public void setHosteller(Hosteller hosteller) {
		this.hosteller = hosteller;
	}
}
