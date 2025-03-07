package com.dilmah.hostel.service;

import com.dilmah.hostel.model.Facility;
import com.dilmah.hostel.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {

	@Autowired
	private FacilityRepository facilityRepository;

	public List<Facility> getAllFacilities() {
		return facilityRepository.findAll();
	}

	public Optional<Facility> getFacilityById(int id) {
		return facilityRepository.findById(id);
	}

	public Facility createFacility(Facility facility) {
		return facilityRepository.save(facility);
	}

	public Facility updateFacility(int id, Facility updatedFacility) {
		return facilityRepository.findById(id).map(facility -> {
			facility.setName(updatedFacility.getName());
			facility.setDescription(updatedFacility.getDescription());
			return facilityRepository.save(facility);
		}).orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));
	}

	public void deleteFacility(int id) {
		facilityRepository.deleteById(id);
	}

	public void deleteFacilitiesByName(String name) {
		List<Facility> facilities = facilityRepository.findAllByName(name);
		if (facilities.isEmpty()) {
			throw new RuntimeException("No facilities found with name: " + name);
		}
		facilityRepository.deleteAll(facilities);
	}
}
