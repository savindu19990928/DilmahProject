package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
