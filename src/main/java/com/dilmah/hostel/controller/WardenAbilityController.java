package com.dilmah.hostel.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.InOut;
import com.dilmah.hostel.model.Room;
import com.dilmah.hostel.model.Warden;
import com.dilmah.hostel.request.UserRegistrationRequest;
import com.dilmah.hostel.service.FoodOrderService;
import com.dilmah.hostel.service.InOutService;
import com.dilmah.hostel.service.RoomDetailsService;
import com.dilmah.hostel.service.UserDetailsService;
import com.dilmah.hostel.util.JwtTokenUtil;

@RestController
@RequestMapping("/warden")
public class WardenAbilityController {

	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private InOutService inOutService;

	@Autowired
	private RoomDetailsService roomService;

	@Autowired
	private FoodOrderService foodService;

	@PutMapping("/update")
	public String updateWarden(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> requestBody) {
		try {
			String username = jwtTokenUtil.extractUsername(token);
			String fullName = requestBody.get("fullName");
			String contactDetails = requestBody.get("contactDetails");
			String inChargeName = requestBody.get("inChargeName");
			String inChargeMobile = requestBody.get("inChargeMobile");
			String password = requestBody.get("password");

			Warden existingWarden = userService.findWardenByUsername(username);

			if (existingWarden == null) {
				return "Warden not found with userID: " + username;
			}
			if (fullName != null && !fullName.trim().isEmpty()) {
				existingWarden.setFullName(fullName);
			}
			if (contactDetails != null && !contactDetails.trim().isEmpty()) {
				existingWarden.setContactDetails(contactDetails);
			}
			if ((inChargeName != null && !inChargeName.trim().isEmpty()) && (inChargeMobile != null && !inChargeMobile.trim().isEmpty())) {
				existingWarden.setInChargeName(inChargeName);
				existingWarden.setInChargeMobile(inChargeMobile);
			} else {
				existingWarden.setInChargeName(null);
				existingWarden.setInChargeMobile(null);
			}
			if (password != null && !password.trim().isEmpty()) {
	            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	            password = passwordEncoder.encode(password);
				existingWarden.setPassword(password);
			}
			userService.updateUser(username, existingWarden, "warden");
			return "Warden updated successfully!";
		} catch (Exception e) {
			return "Update failed: " + e.getMessage();
		}
	}

	@PostMapping("/createhosteller")
	public ResponseEntity<String> createHosteller(@RequestBody UserRegistrationRequest request) {
		try {
			Room room = roomService.findRoomByRoomNumber(request.roomNumber);

			if (room == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Error: Room number " + request.roomNumber + " does not exist!");
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Registration failed: " + e.getMessage());
		}
	}

	@PutMapping("/updatehosteller")
	public String updateHosteller(@RequestBody Map<String, String> requestBody) {
		try {
			String username = requestBody.get("userID");
			String fullName = requestBody.get("fullName");
			String contactDetails = requestBody.get("contactDetails");
			String roomId = requestBody.get("room");
			String salaryStr = requestBody.get("salary");

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

			userService.updateUser(username, existingHosteller, "hosteller");
			return "Hosteller updated successfully!";
		} catch (Exception e) {
			return "Update failed: " + e.getMessage();
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

	@GetMapping("/foodreport")
	public ResponseEntity<?> getFoodReport() {

		try {
			return ResponseEntity.ok(foodService.getFoodOrdersByDate(LocalDateTime.now()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("No food order found: " + e.getMessage());

		}

	}

	@GetMapping("/inout")
	public ResponseEntity<List<InOut>> findAll() {
		return ResponseEntity.ok(inOutService.findAll());
	}

	@PutMapping("/inout/{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody Map<String, LocalDateTime> updatedFields) {
		try {
			Optional<InOut> existingRecord = inOutService.findById(id);
			InOut inOut = existingRecord.get();
			inOut.setApproved(updatedFields.get("approved"));
			return ResponseEntity.ok(inOutService.saveOrUpdate(inOut));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("No food order found: " + e.getMessage());
		}
	}
}
