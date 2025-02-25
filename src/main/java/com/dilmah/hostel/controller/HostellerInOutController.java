package com.dilmah.hostel.controller;

import com.dilmah.hostel.model.InOut;
import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.service.InOutService;
import com.dilmah.hostel.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dilmah.hostel.util.JwtTokenUtil;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/inout")
public class HostellerInOutController {

	@Autowired
	private InOutService inOutService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService hostellerService;

	private String generateOtp() {
		SecureRandom random = new SecureRandom();
		int otp = 10000 + random.nextInt(90000); // Generate a number between 10000 and 99999
		return String.valueOf(otp);
	}

	// Create an InOut record
	@PostMapping
	public ResponseEntity<?> create(@RequestHeader("Authorization") String token,
			@RequestBody Map<String, LocalDateTime> requestBody) {
		try {
			LocalDateTime outTime = (LocalDateTime) requestBody.get("outTime");
			LocalDateTime inTime = (LocalDateTime) requestBody.get("inTime");

			String hostellerId = jwtTokenUtil.extractUsername(token);

			Optional<Hosteller> hosteller = Optional.ofNullable(hostellerService.findHostellerByUsername(hostellerId));

			// Check if a record for this hosteller already exists
			Optional<List<InOut>> existingInOut = Optional.ofNullable(inOutService.findByHosteller(hosteller));

			if (existingInOut.isPresent() && !existingInOut.get().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("A record already exists for this hosteller.");
			}

			String otp1 = generateOtp();
			String otp2 = generateOtp();

			// Check if generated OTPs already exist in the system
			Optional<InOut> result1 = inOutService.findByOtpIn(otp1);
			Optional<InOut> result2 = inOutService.findByOtpOut(otp2);

			if (hosteller.isPresent() && result1.isEmpty() && result2.isEmpty()) {
				InOut inOut = new InOut();
				inOut.setHosteller(hosteller.get());
				inOut.setOtp1(otp1);
				inOut.setOtp2(otp2);
				inOut.setOutTime(outTime);
				inOut.setInTime(inTime);

				return ResponseEntity.ok(inOutService.saveOrUpdate(inOut));
			} else {
				return ResponseEntity.badRequest()
						.body("Hosteller not found with ID: " + hostellerId + " or OTP conflicts.");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error creating record: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		String hostellerId = jwtTokenUtil.extractUsername(token);
		Optional<InOut> result = inOutService.findById(id);

		if (result.isPresent() && result.get().getHosteller().getUserID().equals(hostellerId)) {
			return ResponseEntity.ok(result.get());
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You can only view your own records.");
	}

	// Update specific fields of an InOut record
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @PathVariable long id,
			@RequestBody Map<String, LocalDateTime> updatedFields) {
		String hostellerId = jwtTokenUtil.extractUsername(token);
		Optional<InOut> existingRecord = inOutService.findById(id);

		if (existingRecord.isPresent() && existingRecord.get().getHosteller().getUserID().equals(hostellerId)) {
			InOut inOut = existingRecord.get();

			if (updatedFields.containsKey("outTime")) {
				inOut.setOutTime(updatedFields.get("outTime"));
			}
			if (updatedFields.containsKey("inTime")) {
				inOut.setInTime(updatedFields.get("inTime"));
			}
			if (updatedFields.containsKey("approved")) {
				inOut.setApproved(updatedFields.get("approved"));
			}

			return ResponseEntity.ok(inOutService.saveOrUpdate(inOut));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You can only update your own records.");
	}

	// Delete by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		String hostellerId = jwtTokenUtil.extractUsername(token);
		Optional<InOut> result = inOutService.findById(id);

		if (result.isPresent() && result.get().getHosteller().getUserID().equals(hostellerId)) {
			inOutService.deleteById(id);
			return ResponseEntity.ok("Record deleted successfully.");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You can only delete your own records.");
	}
}
