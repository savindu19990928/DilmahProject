package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Guardian;

public interface GurdianRepository extends JpaRepository<Guardian, Long> {
}
