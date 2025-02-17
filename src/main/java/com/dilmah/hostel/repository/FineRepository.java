package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.Fine;

@RepositoryRestResource(exported = false)
public interface FineRepository extends JpaRepository<Fine, Long> {

}
