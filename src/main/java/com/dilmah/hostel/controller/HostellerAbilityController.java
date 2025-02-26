package com.dilmah.hostel.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.FoodOrder;
import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.service.FoodOrderService;
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

    @GetMapping("/foodorder")
    public ResponseEntity<?> getFoodOrdersByUsername(@RequestHeader("Authorization") String token) {
        try {
            // Validate if token is provided
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Authorization token is missing or empty.");
            }

            // Extract username from token
            String hostellerId = jwtTokenUtil.extractUsername(token);

            if (hostellerId == null || hostellerId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid or expired token.");
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

    @PostMapping("/foodorder/{orderType}")
    public ResponseEntity<FoodOrder> createFoodOrder(@RequestHeader("Authorization") String token,  @PathVariable String orderType, @RequestBody Map<String, LocalDateTime> requestBody) {
        try {
            String hostellerId = jwtTokenUtil.extractUsername(token);
            Hosteller hosteller = userService.findHostellerByUsername(hostellerId);
            FoodOrder foodOrder = new FoodOrder();
            foodOrder.setOrderType(orderType);
            foodOrder.setOrderedDate(requestBody.get("orderedDate"));
            foodOrder.setUser(hosteller);
            FoodOrder createdFoodOrder = foodOrderService.saveFoodOrder(foodOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFoodOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Update FoodOrder by ID
    @PutMapping("/foodorder/{orderId}")
    public ResponseEntity<FoodOrder> updateFoodOrder(@RequestHeader("Authorization") String token, @PathVariable long orderId, @RequestBody Map<String, Object> requestBody) {
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
    public ResponseEntity<String> deleteFoodOrder(@RequestHeader("Authorization") String token, @PathVariable long orderId) {
        try {
            String hostellerId = jwtTokenUtil.extractUsername(token);

            // Ensure the food order exists before trying to delete it
            FoodOrder foodOrder = foodOrderService.getFoodOrderById(orderId).orElse(null);
            if (foodOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Food order not found for the specified ID.");
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
