package com.dilmah.hostel.controller;

import com.dilmah.hostel.model.Fine;
import com.dilmah.hostel.model.User;
import com.dilmah.hostel.service.FineService;
import com.dilmah.hostel.service.UserDetailsService;
import com.dilmah.hostel.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fine")
public class FineController {

	@Autowired
	private FineService fineService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userService;

	// Add a new fine to a hosteller
	@PostMapping("/add")
	public ResponseEntity<Fine> addFine(HttpServletRequest request, @RequestBody Fine fine) {
		String token = request.getHeader("Authorization");
		String userId = jwtTokenUtil.extractUsername(token);
		User user = userService.findUserByUsername(userId);
		fine.setCreator(user);
		Fine createdFine = fineService.createFine(fine);
		return ResponseEntity.ok(createdFine);
	}

	// Get fines for a specific hosteller (Hosteller can only get their own fines)
	@GetMapping("/hosteller")
	public ResponseEntity<List<Fine>> getFinesByHosteller(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String userId = jwtTokenUtil.extractUsername(token);

		List<Fine> fines = fineService.getFinesByHostellerId(userId);
		return ResponseEntity.ok(fines);
	}

	// Get a specific fine (Only creator or fined hosteller can view)
	@GetMapping("/{fineId}")
	public ResponseEntity<Fine> getFineById(HttpServletRequest request, @PathVariable long fineId) {
		String token = request.getHeader("Authorization");
		String userId = jwtTokenUtil.extractUsername(token);

		Optional<Fine> fineOptional = fineService.getFineById(fineId);
		if (fineOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Fine fine = fineOptional.get();

		// Allow access if the user is either the creator or the fined hosteller
		if (!fine.getCreator().getUserID().equals(userId) && !fine.getHosteller().getUserID().equals(userId)) {
			return ResponseEntity.status(403).build(); // Forbidden
		}

		return ResponseEntity.ok(fine);
	}

	// Update fine details (Only creator can update)
	@PutMapping("/{fineId}")
	public ResponseEntity<Fine> updateFine(HttpServletRequest request, @PathVariable long fineId,
			@RequestBody Fine fineDetails) {
		String token = request.getHeader("Authorization");
		String userId = jwtTokenUtil.extractUsername(token);

		Optional<Fine> fineOptional = fineService.getFineById(fineId);
		if (fineOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Fine fine = fineOptional.get();
		if (!fine.getCreator().getUserID().equals(userId)) {
			return ResponseEntity.status(403).build(); // Forbidden
		}

		Fine updatedFine = fineService.updateFine(fineId, fineDetails);
		return ResponseEntity.ok(updatedFine);
	}

	// Mark fine as paid (Only creator or fined hosteller can do this)
	@PutMapping("/{fineId}/pay")
	public ResponseEntity<Fine> markFineAsPaid(HttpServletRequest request, @PathVariable long fineId) {
		String token = request.getHeader("Authorization");
		String userId = jwtTokenUtil.extractUsername(token);

		Optional<Fine> fineOptional = fineService.getFineById(fineId);
		if (fineOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Fine fine = fineOptional.get();

		// Check if the user is either the creator or the fined hosteller
		if (!fine.getCreator().getUserID().equals(userId)) {
			return ResponseEntity.status(403).build(); // Forbidden
		}

		Fine updatedFine = fineService.markFineAsPaid(fineId);
		return ResponseEntity.ok(updatedFine);
	}

	// Delete a fine (Only creator can delete)
	@DeleteMapping("/{fineId}")
	public ResponseEntity<Void> deleteFine(HttpServletRequest request, @PathVariable long fineId) {
		String token = request.getHeader("Authorization");
		String userId = jwtTokenUtil.extractUsername(token);

		Optional<Fine> fineOptional = fineService.getFineById(fineId);
		if (fineOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Fine fine = fineOptional.get();
		if (!fine.getCreator().getUserID().equals(userId)) {
			return ResponseEntity.status(403).build(); // Forbidden
		}

		fineService.deleteFine(fineId);
		return ResponseEntity.noContent().build();
	}
}
