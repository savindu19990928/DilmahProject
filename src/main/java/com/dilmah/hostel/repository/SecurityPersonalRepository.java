package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.SecurityPersonal;

@RepositoryRestResource(exported = false)
public interface SecurityPersonalRepository extends JpaRepository<SecurityPersonal, String> {
	Optional<SecurityPersonal> findByUserID(String userID);
}
