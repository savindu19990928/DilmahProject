package com.dilmah.hostel.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dilmah.hostel.service.UserDetailsService;

@RestController
@RequestMapping("/superadmin")
public class AdminDeletionController {
	
	@Autowired
    private UserDetailsService userService;
    
	@PostMapping("/deleteadmin")
    public String deleteWarden(@RequestBody Map<String, String> requestBody) {
        String userID = requestBody.get("userID");
        String userType = requestBody.get("userType");
		try {
            return userService.deleteUser(userID,userType);
        } catch (Exception e) {
            return "Deletion failed: " + e.getMessage();
        }
    }
}
