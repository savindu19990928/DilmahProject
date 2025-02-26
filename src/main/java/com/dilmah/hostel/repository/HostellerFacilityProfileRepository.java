package com.dilmah.hostel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.HostellerFacilityProfile;

@RepositoryRestResource(exported = false)
public interface HostellerFacilityProfileRepository extends JpaRepository<HostellerFacilityProfile, Integer> {
    List<HostellerFacilityProfile> findByFacility_FacilityID(int facilityId);
    List<HostellerFacilityProfile> findByHosteller_UserID(String hostellerId);
    Optional<HostellerFacilityProfile> findByFacility_FacilityIDAndHosteller_UserID(int facilityId, String hostellerId);
    List<HostellerFacilityProfile> findByStatusIsNull();
}
