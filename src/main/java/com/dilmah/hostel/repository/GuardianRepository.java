package com.dilmah.hostel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.Guardian;

@RepositoryRestResource(exported = false)
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
	List<Guardian> findByHostellerUserIDOrderByAssignedDesc(String username);
}
