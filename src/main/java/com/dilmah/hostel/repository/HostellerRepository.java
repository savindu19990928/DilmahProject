package com.dilmah.hostel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.Hosteller;

@RepositoryRestResource(exported = false)
public interface HostellerRepository extends JpaRepository<Hosteller, String> {
	Optional<Hosteller> findByUserID(String userID);
	List<Hosteller> findAll();
}
