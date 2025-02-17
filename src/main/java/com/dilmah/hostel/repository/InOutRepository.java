package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.InOut;

@RepositoryRestResource(exported = false)
public interface InOutRepository extends JpaRepository<InOut, Long> {

}
