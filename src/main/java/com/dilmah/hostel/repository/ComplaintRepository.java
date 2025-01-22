package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

}
