package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Hosteller;

public interface HostellerRepository extends JpaRepository<Hosteller, String> {
	Optional<Hosteller> findByUserID(String userID);
}
