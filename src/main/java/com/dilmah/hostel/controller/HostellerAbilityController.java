package com.dilmah.hostel.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.FoodOrder;
import com.dilmah.hostel.model.Guardian;
import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.Room;
import com.dilmah.hostel.model.Warden;
import com.dilmah.hostel.service.FoodOrderService;
import com.dilmah.hostel.service.GuardianService;
import com.dilmah.hostel.service.RoomDetailsService;
import com.dilmah.hostel.service.UserDetailsService;
import com.dilmah.hostel.util.JwtTokenUtil;

@RestController
@RequestMapping("/hosteller")
public class HostellerAbilityController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userService;

	@Autowired
	private FoodOrderService foodOrderService;

	@Autowired
	private RoomDetailsService roomService;

	@Autowired
	private GuardianService guardianService;

	@GetMapping("/warden")
	public ResponseEntity<?> getWarden() {
		try {
			Warden warden = userService.findFirstWarden();
			if (warden != null) {
				String mobile = warden.getInChargeMobile();
				if (mobile == null || mobile == "") {
					return ResponseEntity.ok(warden.getContactDetails());
				} else {
					return ResponseEntity.ok(warden.getInChargeMobile());
				}
			} else {
				return ResponseEntity.status(404).body("Warden not found!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error fetching Warden: " + e.getMessage());
		}
	}

	@GetMapping("/foodorder")
	public ResponseEntity<?> getFoodOrdersByUsername(@RequestHeader("Authorization") String token) {
		try {
			// Validate if token is provided
			if (token == null || token.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization token is missing or empty.");
			}

			// Extract username from token
			String hostellerId = jwtTokenUtil.extractUsername(token);

			if (hostellerId == null || hostellerId.isEmpty()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
			}

			// Fetch food orders by username
			List<FoodOrder> foodOrders = foodOrderService.getFoodOrdersByUsername(hostellerId);
			if (foodOrders.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No food orders found for user: " + hostellerId);
			}

			return ResponseEntity.ok(foodOrders);

		} catch (Exception e) {
			// Catch any other unexpected exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

	@GetMapping("/todayfood")
	public ResponseEntity<List<FoodOrder>> getFoodOrdersByDate(@RequestHeader("Authorization") String token,
			@RequestParam String orderedDate) {
		try {
			// Validate if token is provided
			if (token == null || token.isEmpty()) {
				return ResponseEntity.badRequest().build();
			}

			// Extract user ID from token
			String hostellerId = jwtTokenUtil.extractUsername(token);
			if (hostellerId == null || hostellerId.isEmpty()) {
				return ResponseEntity.badRequest().build();
			}
			System.out.print("saaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			// Convert orderedDate to LocalDateTime (ensuring date format)
			LocalDateTime orderedDateTime = LocalDateTime.parse(orderedDate);
			System.out.print("saaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			System.out.print(orderedDateTime);
			// Retrieve food orders for the user on the specified date
			List<FoodOrder> foodOrders = foodOrderService.getFoodOrdersByDateAndUser(hostellerId, orderedDateTime);

			// Check if any orders exist
			if (foodOrders.isEmpty()) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(foodOrders);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/foodorder")
	public ResponseEntity<FoodOrder> createFoodOrder(@RequestHeader("Authorization") String token,
			@RequestBody Map<String, Object> requestBody) {
		try {
			// Validate if token is provided
			if (token == null || token.isEmpty()) {
				return ResponseEntity.badRequest().build();
			}

			// Extract user ID from token
			String hostellerId = jwtTokenUtil.extractUsername(token);
			if (hostellerId == null || hostellerId.isEmpty()) {
				return ResponseEntity.badRequest().build();
			}

			Hosteller hosteller = userService.findHostellerByUsername(hostellerId);
			
			// Extract orderType and orderedDate from requestBody
			String orderType = (String) requestBody.get("orderType");
			
			LocalDateTime orderedDate = LocalDateTime.parse((String) requestBody.get("orderedDate"));
			orderedDate = orderedDate.with(LocalTime.of(0, 0, 1));
			FoodOrder foodOrder = new FoodOrder();
			foodOrder.setOrderType(orderType);
			foodOrder.setOrderedDate(orderedDate);
			foodOrder.setUser(hosteller);

			FoodOrder createdFoodOrder = foodOrderService.saveFoodOrder(foodOrder);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdFoodOrder);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage())).badRequest()
					.build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PutMapping("/update")
	public String updateHosteller(@RequestHeader("Authorization") String token,
			@RequestBody Map<String, Object> requestBody) {
		try {
			String username = jwtTokenUtil.extractUsername(token);

			String fullName = (String) requestBody.get("fullName");
			String contactDetails = (String) requestBody.get("contactDetails");
			String roomId = (String) requestBody.get("room");
			String salaryStr = (String) requestBody.get("salary");
			String photoStr = (String) requestBody.get("photo");
			String password = (String) requestBody.get("password");

			// Guardian details
			String guardianName = (String) requestBody.get("guardianName");
			String guardianContact = (String) requestBody.get("guardianContact");

			Hosteller existingHosteller = userService.findHostellerByUsername(username);

			if (existingHosteller == null) {
				return "Hosteller not found with userID: " + username;
			}

			if (fullName != null && !fullName.trim().isEmpty()) {
				existingHosteller.setFullName(fullName);
			}

			if (contactDetails != null && !contactDetails.trim().isEmpty()) {
				existingHosteller.setContactDetails(contactDetails);
			}

			if (password != null && !password.trim().isEmpty()) {
	            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	            password = passwordEncoder.encode(password);
				existingHosteller.setPassword(password);
			}

			if (roomId != null && !roomId.trim().isEmpty()) {
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

			if (salaryStr != null && !salaryStr.trim().isEmpty()) {
				try {
					BigDecimal salary = new BigDecimal(salaryStr);
					existingHosteller.setSalary(salary);
				} catch (NumberFormatException e) {
					return "Invalid salary value provided.";
				}
			}

			if (photoStr != null && !photoStr.trim().isEmpty()) {
				existingHosteller.setPhoto(photoStr);
			}

			if (guardianName != null && !guardianName.trim().isEmpty() && guardianContact != null
					&& !guardianContact.trim().isEmpty()) {

				// Fetch the last assigned guardian for this hosteller
				Guardian lastGuardian = guardianService.getLatestGuardianByHostellerUserId(username);

				// Check if a new guardian needs to be created
				if (lastGuardian == null || !lastGuardian.getName().equals(guardianName)
						|| !lastGuardian.getMobile().equals(guardianContact)) {

					Guardian newGuardian = new Guardian();
					newGuardian.setName(guardianName);
					newGuardian.setMobile(guardianContact);
					newGuardian.setHosteller(existingHosteller);
					guardianService.saveGuardian(newGuardian);
				}
			}
			userService.updateUser(username, existingHosteller, "hosteller");
			return "Hosteller updated successfully with a new guardian!";
		} catch (Exception e) {
			return "Update failed: " + e.getMessage();
		}
	}

	// Update FoodOrder by ID
	@PutMapping("/foodorder/{orderId}")
	public ResponseEntity<FoodOrder> updateFoodOrder(@RequestHeader("Authorization") String token,
			@PathVariable long orderId, @RequestBody Map<String, Object> requestBody) {
		try {
			String hostellerId = jwtTokenUtil.extractUsername(token);

			FoodOrder existingFoodOrder = foodOrderService.getFoodOrderById(orderId).orElse(null);
			if (existingFoodOrder == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			if (!existingFoodOrder.getUser().getUserID().equals(hostellerId)) {
				return (ResponseEntity<FoodOrder>) ResponseEntity.status(HttpStatus.FORBIDDEN);
			}

			// Update fields if necessary (orderType, orderedDate)
			String orderType = (String) requestBody.get("orderType");
			LocalDateTime orderedDate = (LocalDateTime) requestBody.get("orderedDate");
			if (orderType != null) {
				existingFoodOrder.setOrderType(orderType);
			}
			if (orderedDate != null) {
				existingFoodOrder.setOrderedDate(orderedDate);
			}

			FoodOrder updatedFoodOrder = foodOrderService.saveFoodOrder(existingFoodOrder);
			return ResponseEntity.ok(updatedFoodOrder);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// Delete FoodOrder by ID
	@DeleteMapping("/foodorder/{orderId}")
	public ResponseEntity<String> deleteFoodOrder(@RequestHeader("Authorization") String token,
			@PathVariable long orderId) {
		try {
			String hostellerId = jwtTokenUtil.extractUsername(token);

			// Ensure the food order exists before trying to delete it
			FoodOrder foodOrder = foodOrderService.getFoodOrderById(orderId).orElse(null);
			if (foodOrder == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food order not found for the specified ID.");
			}

			if (!foodOrder.getUser().getUserID().equals(hostellerId)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("You are not authorized to delete this food order.");
			}

			foodOrderService.deleteFoodOrder(orderId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Food order deleted successfully.");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}
}
