package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dilmah.hostel.model.Offense;

@RepositoryRestResource(path = "offense")
@PreAuthorize("hasRole('WARDEN')")
public interface OffenseRepository extends JpaRepository<Offense, Integer> {

}
