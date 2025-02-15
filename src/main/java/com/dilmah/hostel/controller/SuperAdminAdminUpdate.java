package com.dilmah.hostel.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.Room;
import com.dilmah.hostel.model.SecurityPersonal;
import com.dilmah.hostel.model.SuperAdmin;
import com.dilmah.hostel.model.Warden;
import com.dilmah.hostel.service.RoomDetailsService;
import com.dilmah.hostel.service.UserDetailsService;

@RestController
@RequestMapping("/superadmin")
public class SuperAdminAdminUpdate {
	
    @Autowired
    private UserDetailsService userService;
    
    @Autowired
    private RoomDetailsService roomService;
	
    @PostMapping("/updatesuperadmin")
    public String updateSuperAdmin(@RequestBody Map<String, String> requestBody) {
        try {
            String username = requestBody.get("userID");
            String fullName = requestBody.get("fullName");
            String contactDetails = requestBody.get("contactDetails");
            
            SuperAdmin existingSuperAdmin = userService.findSuperAdminByUsername(username);

            if (existingSuperAdmin == null) {
                return "SuperAdmin not found with userID: " + username;
            }
            
            if (fullName != null) {
                existingSuperAdmin.setFullName(fullName);
            }
            if (contactDetails != null) {
                existingSuperAdmin.setContactDetails(contactDetails);
            }
            
            userService.updateUser(username, existingSuperAdmin, "superAdmin");
            return "SuperAdmin updated successfully!";
        } catch (Exception e) {
            return "Update failed: " + e.getMessage();
        }
    }
    
	@PostMapping("/updatewarden")
	public String updateWarden(@RequestBody Map<String, String> requestBody) {
	    try {
            String username = requestBody.get("userID");
            String fullName = requestBody.get("fullName");
            String contactDetails = requestBody.get("contactDetails");
            String inChargeName = requestBody.get("inChargeName");
            String inChargeMobile = requestBody.get("inChargeMobile");
            
	        Warden existingWarden = userService.findWardenByUsername(username);
	        
	        if (existingWarden == null) {
	            return "Warden not found with userID: " + username;
	        }
	        
	        if (fullName != null) {
	            existingWarden.setFullName(fullName);
	        }
	        if (contactDetails != null) {
	            existingWarden.setContactDetails(contactDetails);
	        }
	        if (inChargeName != null) {
	            existingWarden.setInChargeName(inChargeName);
	        }
	        if (inChargeMobile != null) {
	            existingWarden.setInChargeMobile(inChargeMobile);
	        }

	        userService.updateUser(username,existingWarden,"warden");
	        return "Warden updated successfully!";
	    } catch (Exception e) {
	        return "Update failed: " + e.getMessage();
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
	
	@PostMapping("/updatesecurity")
	public String updateSecurityPersonal(@RequestBody Map<String, String> requestBody) {
	    try {
	        String username = requestBody.get("userID");
	        String fullName = requestBody.get("fullName");
	        String contactDetails = requestBody.get("contactDetails");
	        
	        SecurityPersonal existingSecurityAdmin = userService.findSecurityPersonalByUsername(username);

	        if (existingSecurityAdmin == null) {
	            return "Security Personal not found with userID: " + username;
	        }
	        
	        if (fullName != null) {
	            existingSecurityAdmin.setFullName(fullName);
	        }
	        if (contactDetails != null) {
	            existingSecurityAdmin.setContactDetails(contactDetails);
	        }
	        
	        userService.updateUser(username, existingSecurityAdmin, "securitypersonal");
	        return "Security Personal updated successfully!";
	    } catch (Exception e) {
	        return "Update failed: " + e.getMessage();
	    }
	}

}
