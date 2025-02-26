package com.dilmah.hostel.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.BorrowedItem;
import com.dilmah.hostel.model.Facility;
import com.dilmah.hostel.model.FacilityAdmin;
import com.dilmah.hostel.model.FacilityItem;
import com.dilmah.hostel.model.HostellerFacilityProfile;
import com.dilmah.hostel.service.FacilityItemService;
import com.dilmah.hostel.service.FacilityService;
import com.dilmah.hostel.service.HostellerFacilityProfileService;
import com.dilmah.hostel.service.UserDetailsService;
import com.dilmah.hostel.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/facilityadmin")
public class FacilityAdminAbilityController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private FacilityService facilityService;

	@Autowired
	private FacilityItemService facilityItemService;

	@Autowired
	private UserDetailsService userService;

	@Autowired
	private HostellerFacilityProfileService hostellerFacilityProfileService;

	private boolean isFacilityAdminAuthorized(HttpServletRequest request, int facilityId) {
		String token = request.getHeader("Authorization");
		String facilityAdminId = jwtTokenUtil.extractUsername(token);
		FacilityAdmin fa = userService.findFacilityAdminByUsername(facilityAdminId);
		return fa.getFacility().getFacilityID() == facilityId;
	}

	@GetMapping("/my-facility")
	public ResponseEntity<Facility> getFacilityByAdmin(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String facilityAdminId = jwtTokenUtil.extractUsername(token);

		FacilityAdmin fa = userService.findFacilityAdminByUsername(facilityAdminId);

		if (fa == null || fa.getFacility() == null) {
			return ResponseEntity.status(404).body(null);
		}

		return ResponseEntity.ok(fa.getFacility());
	}

	// Edit Facility Profile
	@PutMapping("/{id}")
	public ResponseEntity<Facility> updateFacility(HttpServletRequest request, @PathVariable int id,
			@RequestBody Facility facility) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}
		try {
			Facility updatedFacility = facilityService.updateFacility(id, facility);
			return ResponseEntity.ok(updatedFacility);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// View Inventory Details
	@GetMapping("/{id}/inventory")
	public ResponseEntity<List<FacilityItem>> getFacilityInventory(HttpServletRequest request, @PathVariable int id) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}
		List<FacilityItem> inventory = facilityItemService.getAllFacilityItems();
		if (inventory.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(inventory);
	}

	// Edit Inventory Details
	@PutMapping("/{id}/inventory/{inventoryId}")
	public ResponseEntity<FacilityItem> updateInventory(HttpServletRequest request, @PathVariable int id,
			@PathVariable int inventoryId, @RequestBody FacilityItem updatedItem) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}
		try {
			FacilityItem updatedInventory = facilityItemService.updateFacilityItem(inventoryId, updatedItem);
			return ResponseEntity.ok(updatedInventory);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// Approve Hosteller to Facility
	@PostMapping("/{id}/approve-hosteller")
	public ResponseEntity<String> approveHosteller(HttpServletRequest request, @PathVariable int id,
			@RequestBody int profileID) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403)
					.body("Unauthorized: You can only approve hostellers for your own facility.");
		}

		try {
			// Fetch facility
			Optional<Facility> facilityOpt = facilityService.getFacilityById(id);
			if (facilityOpt.isEmpty()) {
				return ResponseEntity.status(404).body("Facility not found.");
			}

			// Create Hosteller Facility Profile
			HostellerFacilityProfile profile = (HostellerFacilityProfile) hostellerFacilityProfileService
					.getProfilesByFacilityId(profileID);
			profile.setStatus("APPROVED");
			profile.setDescription("Hosteller just approved to facility.");

			hostellerFacilityProfileService.saveHostellerFacilityProfile(profile);

			return ResponseEntity.ok("Hosteller approved to facility with ID: " + id);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error approving hosteller: " + e.getMessage());
		}
	}

	// Remove Hosteller from Facility
	@PostMapping("/{id}/ban/{hostellerId}")
	public ResponseEntity<String> removeHosteller(HttpServletRequest request, @PathVariable int id,
			@PathVariable String hostellerId) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}
		Optional<HostellerFacilityProfile> hfp = hostellerFacilityProfileService
				.getProfileByFacilityAndHosteller(hostellerId, id);
		if (hfp.isPresent()) {
			hfp.get().setStatus("BANNED");
			return ResponseEntity.ok("Hosteller with ID: " + hostellerId + " removed from facility with ID: " + id);
		}
		// Return 404 with an error message
		return ResponseEntity.status(404)
				.body("Hosteller with ID: " + hostellerId + " not found in facility with ID: " + id);
	}

	// View Borrowed Items by Hosteller User ID
	@GetMapping("/{id}/borrowed-items/{userId}")
	public ResponseEntity<List<BorrowedItem>> getBorrowedItemsByHosteller(HttpServletRequest request,
			@PathVariable int id, @PathVariable String userId) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}
		List<BorrowedItem> borrowedItems = facilityItemService.getBorrowedItemsByHostellerUserId(userId, id);
		if (borrowedItems.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(borrowedItems);
	}

	// Approve Borrowing of an Item
	@PostMapping("/{id}/borrow-item")
	public ResponseEntity<BorrowedItem> borrowItem(HttpServletRequest request, @PathVariable int id,
			@RequestBody BorrowedItem borrowedItem) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}
		try {
			// Find the facility item being borrowed
			Optional<FacilityItem> bitem = facilityItemService.getFacilityItemById(borrowedItem.getBorrowedID());
			FacilityItem item = bitem.get();
			if (item == null || item.getQuantity() <= 0) {
				return ResponseEntity.status(400).body(null); // Not enough stock
			}

			// Reduce the stock by 1
			item.setQuantity(item.getQuantity() - 1);
			facilityItemService.updateFacilityItem(item.getItemID(), item);

			// Save the borrowed item record
			BorrowedItem savedItem = facilityItemService.saveBorrowedItem(borrowedItem);
			return ResponseEntity.ok(savedItem);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	// Return a Borrowed Item
	@PutMapping("/{id}/return-item/{borrowedItemId}")
	public ResponseEntity<BorrowedItem> returnBorrowedItem(HttpServletRequest request, @PathVariable int id,
			@PathVariable int borrowedItemId, @RequestBody BorrowedItem borrowedItem) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}
		try {
			// Find the borrowed item
			Optional<BorrowedItem> existingBorrowedItemopt = facilityItemService.getBorrowedItemById(borrowedItemId);
			BorrowedItem existingBorrowedItem = existingBorrowedItemopt.get();
			if (existingBorrowedItem == null) {
				return ResponseEntity.notFound().build();
			}

			// Find the corresponding facility item
			Optional<FacilityItem> itemOpt = facilityItemService
					.getFacilityItemById(existingBorrowedItem.getBorrowedID());
			FacilityItem item = itemOpt.get();
			if (item == null) {
				return ResponseEntity.status(400).build();
			}

			// Increase the stock by 1
			item.setQuantity(item.getQuantity() + 1);
			facilityItemService.updateFacilityItem(item.getItemID(), item);

			// Mark the borrowed item as returned
			existingBorrowedItem.setStatus("returned");
			BorrowedItem updatedBorrowedItem = facilityItemService.updateBorrowedItem(borrowedItemId,
					existingBorrowedItem);
			return ResponseEntity.ok(updatedBorrowedItem);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}/pending-hostellers")
	public ResponseEntity<List<HostellerFacilityProfile>> getPendingHostellers(HttpServletRequest request,
			@PathVariable int id) {
		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}

		List<HostellerFacilityProfile> pendingProfiles = hostellerFacilityProfileService.getProfilesWithNullStatus();
		if (pendingProfiles.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(pendingProfiles);
	}

	@PutMapping("/{id}/hosteller-profile/{profileId}")
	public ResponseEntity<HostellerFacilityProfile> updateHostellerFacilityProfile(HttpServletRequest request,
			@PathVariable int id, @PathVariable int profileId, @RequestBody HostellerFacilityProfile updatedProfile) {

		if (!isFacilityAdminAuthorized(request, id)) {
			return ResponseEntity.status(403).build();
		}

		try {
			Optional<HostellerFacilityProfile> existingProfileOpt = hostellerFacilityProfileService
					.getHostellerFacilityProfileById(profileId);
			if (existingProfileOpt.isEmpty()) {
				return ResponseEntity.notFound().build();
			}

			HostellerFacilityProfile existingProfile = existingProfileOpt.get();

			// Update fields based on the request body
			existingProfile.setStatus(updatedProfile.getStatus());
			existingProfile.setDescription(updatedProfile.getDescription());

			HostellerFacilityProfile savedProfile = hostellerFacilityProfileService
					.saveHostellerFacilityProfile(existingProfile);
			return ResponseEntity.ok(savedProfile);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

}
