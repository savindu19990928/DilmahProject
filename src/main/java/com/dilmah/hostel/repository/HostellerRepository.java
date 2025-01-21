package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Hosteller;

public interface HostellerRepository extends JpaRepository<Hosteller, String> {
}
