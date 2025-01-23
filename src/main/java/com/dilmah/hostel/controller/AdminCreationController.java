package com.dilmah.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.SecurityPersonal;
import com.dilmah.hostel.model.Warden;
import com.dilmah.hostel.request.UserRegistrationRequest;
import com.dilmah.hostel.service.UserDetailsService;

@RestController
@RequestMapping("/superadmin")
public class AdminCreationController {
	
    @Autowired
    private UserDetailsService userService;
    
	@PostMapping("/createwarden")
    public String createWarden(@RequestBody UserRegistrationRequest request) {
		try {
        	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(request.pass);
        	Warden Warden = new Warden();
        	Warden.setUserID(request.userID);
            Warden.setFullName(request.fullName);
            Warden.setPassword(hashedPassword);
            Warden.setContactDetails(request.contactDetails);
            userService.registerUser(Warden);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }
	
	@PostMapping("/createsecurity")
    public String createSecurity(@RequestBody UserRegistrationRequest request) {
		try {
        	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(request.pass);
            SecurityPersonal security = new SecurityPersonal();
        	security.setUserID(request.userID);
            security.setFullName(request.fullName);
            security.setPassword(hashedPassword);
            security.setContactDetails(request.contactDetails);
            userService.registerUser(security);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }
}
