package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.SuperAdmin;

@RepositoryRestResource(exported = false)
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, String> {
	Optional<SuperAdmin> findByUserID(String userID);
}
