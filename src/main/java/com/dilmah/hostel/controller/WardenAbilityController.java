package com.dilmah.hostel.controller;

import java.util.Map;

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
import com.dilmah.hostel.request.UserRegistrationRequest;
import com.dilmah.hostel.service.RoomDetailsService;
import com.dilmah.hostel.service.UserDetailsService;

@RestController
@RequestMapping("/warden")
public class WardenAbilityController {
	
    @Autowired
    private UserDetailsService userService;
	
    @Autowired
    private RoomDetailsService roomService;
    
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
    
    @PostMapping("/createroom")
    public ResponseEntity<String> createRoom(@RequestBody Map<String, String> request) {
    	long roomNumber = Long.parseLong(request.get("roomNumber"));
    	int availability = Integer.parseInt(request.get("availability"));
        try {
        	roomService.createRoom(roomNumber, availability);
            return ResponseEntity.status(HttpStatus.CREATED).body("Room created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Room creation failed: " + e.getMessage());
        }
    }
}
