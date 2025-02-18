package com.dilmah.hostel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.SecurityPersonal;
import com.dilmah.hostel.model.SuperAdmin;
import com.dilmah.hostel.model.Warden;
import com.dilmah.hostel.service.UserDetailsService;

@RestController
@RequestMapping("/superadmin")
public class SuperAdminAdminRead {
	
	@Autowired
    private UserDetailsService userService;
	
    @GetMapping("/warden/{userID}")
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

    @GetMapping("/security/{userID}")
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
    
    @GetMapping("/hosteller/{userID}")
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
    
    @GetMapping("/superadmin/{userID}")
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
    
    @GetMapping("/all/hostellers")
    public ResponseEntity<?> getAllHostellers() {
        try {
            List<Map<String, Object>> hostellers = userService.findAllHostellers();
            if (hostellers != null && !hostellers.isEmpty()) {
                return ResponseEntity.ok(hostellers);
            } else {
                return ResponseEntity.status(404).body("No hostellers found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching hostellers: " + e.getMessage());
        }
    }
}
