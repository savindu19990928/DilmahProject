package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.Service;

@RepositoryRestResource(exported = false)
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
