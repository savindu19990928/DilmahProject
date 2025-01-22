package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.FacilityAdmin;

public interface FacilityAdminRepository extends JpaRepository<FacilityAdmin, String> {
	Optional<FacilityAdmin> findByUserID(String userID);
}
