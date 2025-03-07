package com.dilmah.hostel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.Facility;

@RepositoryRestResource(exported = false)
public interface FacilityRepository extends JpaRepository<Facility, Integer> {
	List<Facility> findAllByName(String name);
}
