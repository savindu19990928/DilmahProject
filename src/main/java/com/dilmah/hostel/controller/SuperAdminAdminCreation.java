package com.dilmah.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.Room;
import com.dilmah.hostel.model.SecurityPersonal;
import com.dilmah.hostel.model.SuperAdmin;
import com.dilmah.hostel.model.Warden;
import com.dilmah.hostel.request.UserRegistrationRequest;
import com.dilmah.hostel.service.RoomDetailsService;
import com.dilmah.hostel.service.UserDetailsService;

@RestController
@RequestMapping("/superadmin")
public class SuperAdminAdminCreation {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private RoomDetailsService roomService;

    @PostMapping("/createwarden")
    public ResponseEntity<String> createWarden(@RequestBody UserRegistrationRequest request) {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(request.pass);
            Warden warden = new Warden();
            warden.setUserID(request.userID);
            warden.setFullName(request.fullName);
            warden.setPassword(hashedPassword);
            warden.setContactDetails(request.contactDetails);
            userService.registerUser(warden);
            return ResponseEntity.status(HttpStatus.CREATED).body("Warden registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/createsecurity")
    public ResponseEntity<String> createSecurity(@RequestBody UserRegistrationRequest request) {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(request.pass);
            SecurityPersonal security = new SecurityPersonal();
            security.setUserID(request.userID);
            security.setFullName(request.fullName);
            security.setPassword(hashedPassword);
            security.setContactDetails(request.contactDetails);
            userService.registerUser(security);
            return ResponseEntity.status(HttpStatus.CREATED).body("Security personnel registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/createsuperadmin")
    public ResponseEntity<String> createSuperAdmin(@RequestBody UserRegistrationRequest request) {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(request.pass);
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setUserID(request.userID);
            superAdmin.setFullName(request.fullName);
            superAdmin.setPassword(hashedPassword);
            superAdmin.setContactDetails(request.contactDetails);
            userService.registerUser(superAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body("Super admin registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/createhosteller")
    public ResponseEntity<String> createHosteller(@RequestBody UserRegistrationRequest request) {
        try {
            Room room = roomService.findRoomByRoomNumber(request.roomNumber);

            if (room == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Room number " + request.roomNumber + " does not exist!");
            }

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(request.pass);
            Hosteller hosteller = new Hosteller();
            hosteller.setUserID(request.userID);
            hosteller.setFullName(request.fullName);
            hosteller.setPassword(hashedPassword);
            hosteller.setContactDetails(request.contactDetails);
            hosteller.setSalary(request.salary);
            hosteller.setRoom(room);
            userService.registerUser(hosteller);
            room.setAvailability(room.getAvailability() - 1);
            roomService.saveRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body("Hosteller registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
}
