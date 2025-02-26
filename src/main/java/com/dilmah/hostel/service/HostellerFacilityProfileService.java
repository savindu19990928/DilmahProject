package com.dilmah.hostel.service;

import com.dilmah.hostel.model.HostellerFacilityProfile;
import com.dilmah.hostel.repository.HostellerFacilityProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostellerFacilityProfileService {

	@Autowired
	private HostellerFacilityProfileRepository hostellerFacilityProfileRepository;

	public List<HostellerFacilityProfile> getAllHostellerFacilityProfiles() {
		return hostellerFacilityProfileRepository.findAll();
	}

	public Optional<HostellerFacilityProfile> getHostellerFacilityProfileById(int id) {
		return hostellerFacilityProfileRepository.findById(id);
	}

	public List<HostellerFacilityProfile> getProfilesByFacilityId(int facilityId) {
		return hostellerFacilityProfileRepository.findByFacility_FacilityID(facilityId);
	}

	public List<HostellerFacilityProfile> getProfilesByHostellerId(String hostellerId) {
		return hostellerFacilityProfileRepository.findByHosteller_UserID(hostellerId);
	}

	public HostellerFacilityProfile saveHostellerFacilityProfile(HostellerFacilityProfile profile) {
		return hostellerFacilityProfileRepository.save(profile);
	}

	public Optional<HostellerFacilityProfile> getProfileByFacilityAndHosteller(String hostellerId, int facilityId) {
		return hostellerFacilityProfileRepository.findByFacility_FacilityIDAndHosteller_UserID(facilityId, hostellerId);
	}

	public void deleteHostellerFacilityProfile(int id) {
		hostellerFacilityProfileRepository.deleteById(id);
	}

	public List<HostellerFacilityProfile> getProfilesWithNullStatus() {
		return hostellerFacilityProfileRepository.findByStatusIsNull();
	}
}
