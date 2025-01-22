package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Guardian;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
}
