package com.dilmah.hostel.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dilmah.hostel.model.Guardian;
import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.SuperAdmin;
import com.dilmah.hostel.model.User;
import com.dilmah.hostel.service.GuardianService;
import com.dilmah.hostel.service.UserDetailsService;
import com.dilmah.hostel.util.ClientIpExtractor;
import com.dilmah.hostel.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import com.dilmah.hostel.request.UserRegistrationRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtUtil;

	@Autowired
	private UserDetailsService userService;

	@Autowired
	private GuardianService guardianService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
		try {
			String username = requestBody.get("username");
			String password = requestBody.get("password");

			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			String role = authenticate.getAuthorities().iterator().next().getAuthority();
			String ipAddress = ClientIpExtractor.getClientIp(request);
			String token = jwtUtil.generateToken(username, role, ipAddress);

			// Fetch user details
			User user = userService.findUserByUsername(username);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
			}

			Map<String, Object> response = new HashMap<>();
			response.put("token", token);
			response.put("role", role);
			response.put("user", user);

			// If the user is a Hosteller, fetch their latest guardian
			if (user instanceof Hosteller) {
				Guardian guardian = guardianService.getLatestGuardianByHostellerUserId(username);
				response.put("guardian", guardian);
			}

			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

	@PostMapping("/register")
	public String register(@RequestBody UserRegistrationRequest request) {
		try {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(request.pass);
			SuperAdmin superAdmin = new SuperAdmin();
			superAdmin.setUserID(request.userID);
			superAdmin.setFullName(request.fullName);
			superAdmin.setPassword(hashedPassword);
			superAdmin.setContactDetails(request.contactDetails);
			userService.registerUser(superAdmin);
			return "User registered successfully!";
		} catch (Exception e) {
			return "Registration failed: " + e.getMessage();
		}
	}
}
