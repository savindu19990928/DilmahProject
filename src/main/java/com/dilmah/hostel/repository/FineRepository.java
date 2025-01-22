package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Fine;

public interface FineRepository extends JpaRepository<Fine, Long> {

}
