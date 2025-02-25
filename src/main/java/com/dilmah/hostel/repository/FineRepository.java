package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dilmah.hostel.model.Fine;

@RepositoryRestResource(path = "fine")
@PreAuthorize("hasRole('WARDEN')")
public interface FineRepository extends JpaRepository<Fine, Long> {

}
