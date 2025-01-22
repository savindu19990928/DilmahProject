package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long> {

}
