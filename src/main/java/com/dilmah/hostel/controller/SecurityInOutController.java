package com.dilmah.hostel.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.InOut;
import com.dilmah.hostel.service.InOutService;

@RestController
@RequestMapping("/security")
public class SecurityInOutController {

	@Autowired
	private InOutService inOutService;

	// Find by otpIn
	@PatchMapping("/otp/{otpIn}")
	public ResponseEntity<?> findByOtpIn(@PathVariable String otpIn, @RequestBody LocalDateTime dateTime) {
		try {
			Optional<InOut> result = inOutService.findByOtpIn(otpIn);

			if (result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No record found for otpIn: " + otpIn);
			}

			InOut inOut = result.get();
			inOut.setLeavedTime(dateTime);

			return ResponseEntity.ok(inOutService.saveOrUpdate(inOut));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating returnedTime: " + e.getMessage());
		}
	}

	@PutMapping("/otp/{otpOut}")
	public ResponseEntity<?> findByOtpOut(@PathVariable String otpOut, @RequestBody LocalDateTime dateTime) {
		try {
			Optional<InOut> result = inOutService.findByOtpOut(otpOut);

			if (result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No record found for otpOut: " + otpOut);
			}

			InOut inOut = result.get();
			inOut.setReturnedTime(dateTime);

			return ResponseEntity.ok(inOutService.saveOrUpdate(inOut));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating returnedTime: " + e.getMessage());
		}
	}

}
