package com.dilmah.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.SecurityPersonal;
import com.dilmah.hostel.model.SuperAdmin;
import com.dilmah.hostel.model.Warden;
import com.dilmah.hostel.service.UserDetailsService;

@RestController
@RequestMapping("/superadmin")
public class AdminReadController {
	
	@Autowired
    private UserDetailsService userService;
	
    @PostMapping("/warden/{userID}")
    public ResponseEntity<?> getWarden(@PathVariable String userID) {
        try {
            Warden warden = userService.findWardenByUsername(userID);
            if (warden != null) {
                return ResponseEntity.ok(warden);
            } else {
                return ResponseEntity.status(404).body("Warden not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching Warden: " + e.getMessage());
        }
    }

    @PostMapping("/security/{userID}")
    public ResponseEntity<?> getSecurity(@PathVariable String userID) {
        try {
            SecurityPersonal security = userService.findSecurityPersonalByUsername(userID);
            if (security != null) {
                return ResponseEntity.ok(security);
            } else {
                return ResponseEntity.status(404).body("Security personnel not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching Security Personnel: " + e.getMessage());
        }
    }
    
    @PostMapping("/hosteller/{userID}")
    public ResponseEntity<?> getHosteller(@PathVariable String userID) {
        try {
            Hosteller hosteller = userService.findHostellerByUsername(userID);
            if (hosteller != null) {
                return ResponseEntity.ok(hosteller);
            } else {
                return ResponseEntity.status(404).body("Hosteller not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching Hosteller: " + e.getMessage());
        }
    }
    
    @PostMapping("/superadmin/{userID}")
    public ResponseEntity<?> getSuperAdmin(@PathVariable String userID) {
        try {
            SuperAdmin superAdmin = userService.findSuperAdminByUsername(userID);
            if (superAdmin != null) {
                return ResponseEntity.ok(superAdmin);
            } else {
                return ResponseEntity.status(404).body("Super Admin not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching Super Admin: " + e.getMessage());
        }
    }
}
