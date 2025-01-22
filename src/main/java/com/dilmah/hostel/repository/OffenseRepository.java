package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Offense;

public interface OffenseRepository extends JpaRepository<Offense, Integer> {

}
