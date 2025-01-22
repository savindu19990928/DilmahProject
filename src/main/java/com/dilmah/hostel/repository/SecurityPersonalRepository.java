package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.SecurityPersonal;

public interface SecurityPersonalRepository extends JpaRepository<SecurityPersonal, String> {
	Optional<SecurityPersonal> findByUserID(String userID);
}
