package com.dilmah.hostel.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
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
    
	@PostMapping("/updatehosteller")
	public String updateHosteller(@RequestBody Map<String, String> requestBody) {
	    try {
	        String username = requestBody.get("userID");
	        String fullName = requestBody.get("fullName");
	        String contactDetails = requestBody.get("contactDetails");
	        String roomId = requestBody.get("room");
	        String salaryStr = requestBody.get("salary");
	        String isSickStr = requestBody.get("isSick");

	        Hosteller existingHosteller = userService.findHostellerByUsername(username);

	        if (existingHosteller == null) {
	            return "Hosteller not found with userID: " + username;
	        }

	        if (fullName != null) {
	            existingHosteller.setFullName(fullName);
	        }
	        if (contactDetails != null) {
	            existingHosteller.setContactDetails(contactDetails);
	        }
	        
	        if (roomId != null) {
	        	Long roomNumber = Long.parseLong(roomId);
	            Room room = roomService.findRoomByRoomNumber(roomNumber);
	            if (room == null) {
	                return "Room not found with roomID: " + roomId;
	            }
	            Room oldRoom = existingHosteller.getRoom();
	            oldRoom.setAvailability(oldRoom.getAvailability() + 1);
	            roomService.saveRoom(oldRoom);
	            room.setAvailability(room.getAvailability() - 1);
	            roomService.saveRoom(room);
	            existingHosteller.setRoom(room);
	        }

	        if (salaryStr != null) {
	            try {
	                BigDecimal salary = new BigDecimal(salaryStr);
	                existingHosteller.setSalary(salary);
	            } catch (NumberFormatException e) {
	                return "Invalid salary value provided.";
	            }
	        }

	        if (isSickStr != null) {
	            existingHosteller.setIsSick(Boolean.parseBoolean(isSickStr));
	        }

	        userService.updateUser(username, existingHosteller, "hosteller");
	        return "Hosteller updated successfully!";
	    } catch (Exception e) {
	        return "Update failed: " + e.getMessage();
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
    
    @PostMapping("/all/hostellers")
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
