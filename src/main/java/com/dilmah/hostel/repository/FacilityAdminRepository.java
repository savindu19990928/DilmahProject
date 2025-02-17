package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.FacilityAdmin;

@RepositoryRestResource(exported = false)
public interface FacilityAdminRepository extends JpaRepository<FacilityAdmin, String> {
	Optional<FacilityAdmin> findByUserID(String userID);
}
