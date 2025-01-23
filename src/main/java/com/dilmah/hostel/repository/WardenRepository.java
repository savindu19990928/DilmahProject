package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Warden;

public interface WardenRepository extends JpaRepository<Warden, String> {
	 Optional<Warden> findByUserID(String userID);
}
