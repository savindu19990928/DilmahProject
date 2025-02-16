package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.Warden;

@RepositoryRestResource(exported = false)
public interface WardenRepository extends JpaRepository<Warden, String> {
	 Optional<Warden> findByUserID(String userID);
}
