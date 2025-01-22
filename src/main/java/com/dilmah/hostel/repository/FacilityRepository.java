package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilmah.hostel.model.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {

}
