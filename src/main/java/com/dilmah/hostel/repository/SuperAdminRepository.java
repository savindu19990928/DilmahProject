package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.SuperAdmin;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, String> {
	Optional<SuperAdmin> findByUserID(String userID);
}
