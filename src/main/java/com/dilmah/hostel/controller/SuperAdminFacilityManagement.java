package com.dilmah.hostel.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.model.Facility;
import com.dilmah.hostel.model.FacilityAdmin;
import com.dilmah.hostel.model.Subscription;
import com.dilmah.hostel.model.SubscriptionFeatures;
import com.dilmah.hostel.request.FacilityRequest;
import com.dilmah.hostel.service.FacilityService;
import com.dilmah.hostel.service.SubscriptionService;
import com.dilmah.hostel.service.UserDetailsService;

@RestController
@RequestMapping("/superadmin")
public class SuperAdminFacilityManagement {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private FacilityService facilityService;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    // Create Facility
    @PostMapping("/createfacility")
    public ResponseEntity<String> createSecurity(@RequestBody FacilityRequest request) {
        try {
            // Create Facility
            Facility facility = new Facility();
            facility.setName(request.facilityName);
            facility.setDescription(request.description);
            facilityService.createFacility(facility);
            
            // Create Facility Admin
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String hashedPassword = passwordEncoder.encode(request.pass);
//            FacilityAdmin facilityAdmin = new FacilityAdmin();
//            facilityAdmin.setFacility(facility);
//            facilityAdmin.setUserID(request.userID);
//            facilityAdmin.setFullName(request.fullName);
//            facilityAdmin.setPassword(hashedPassword);
//            facilityAdmin.setContactDetails(request.contactDetails);
//            userService.registerUser(facilityAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body("Facility registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    // Create Subscription
    @PostMapping("/createsubscription")
    public ResponseEntity<?> createSubscription(@RequestBody Subscription subscription) {
        try {
            Subscription createdSubscription = subscriptionService.createSubscription(subscription);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating subscription: " + e.getMessage());
        }
    }

    // Create Subscription Features
    @PostMapping("/createfeature")
    public ResponseEntity<?> createSubscriptionFeatures(@RequestBody List<SubscriptionFeatures> features) {
        try {
            for (SubscriptionFeatures feature : features) {
                Subscription subscription = subscriptionService.getSubscriptionById(feature.getSubscription().getSubscriptionID())
                        .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + feature.getSubscription().getSubscriptionID()));
                feature.setSubscription(subscription);
            }

            List<SubscriptionFeatures> createdFeatures = features.stream()
                    .map(subscriptionService::createService)
                    .toList();

            return ResponseEntity.ok(createdFeatures);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating subscription features: " + e.getMessage());
        }
    }

    // Read Facilities
    @GetMapping("/facilities")
    public ResponseEntity<?> getAllFacilities() {
        try {
            List<Facility> facilities = facilityService.getAllFacilities();
            return ResponseEntity.ok(facilities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching facilities: " + e.getMessage());
        }
    }

    @GetMapping("/facility/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable int id) {
        try {
            Optional<Facility> facility = facilityService.getFacilityById(id);
            if (facility != null) {
                return ResponseEntity.ok(facility);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Facility not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching facility: " + e.getMessage());
        }
    }
    
    // Read Facility Admin by Facility ID
    @GetMapping("/facility-admin/{facilityId}")
    public ResponseEntity<?> getFacilityAdminByFacilityId(@PathVariable int facilityId) {
        try {
            // Get Facility by ID
            Facility facility = facilityService.getFacilityById(facilityId)
                    .orElseThrow(() -> new RuntimeException("Facility not found with ID: " + facilityId));

            // Get Facility Admin for that Facility
            FacilityAdmin facilityAdmin = userService.getFacilityAdminByFacility(facility);
            if (facilityAdmin != null) {
                return ResponseEntity.ok(facilityAdmin);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Facility Admin not found for Facility ID: " + facilityId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching Facility Admin: " + e.getMessage());
        }
    }
    
    // Update Facility Admin by Facility ID
    @PutMapping("/facility-admin/{facilityId}")
    public ResponseEntity<?> updateFacilityAdminByFacilityId(@PathVariable int facilityId, @RequestBody FacilityAdmin updatedFacilityAdmin) {
        try {
            // Get Facility by ID
            Facility facility = facilityService.getFacilityById(facilityId)
                    .orElseThrow(() -> new RuntimeException("Facility not found with ID: " + facilityId));

            // Update Facility Admin for that Facility
            FacilityAdmin facilityAdmin = userService.getFacilityAdminByFacility(facility);
            if (facilityAdmin != null) {
                facilityAdmin.setFullName(updatedFacilityAdmin.getFullName());
                facilityAdmin.setContactDetails(updatedFacilityAdmin.getContactDetails());
                facilityAdmin.setPassword(updatedFacilityAdmin.getPassword()); // Assuming password update
                FacilityAdmin updatedAdmin = userService.updateFacilityAdmin(facilityAdmin);
                return ResponseEntity.ok(updatedAdmin);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Facility Admin not found for Facility ID: " + facilityId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating Facility Admin: " + e.getMessage());
        }
    }


    // Read Subscriptions
    @GetMapping("/subscriptions")
    public ResponseEntity<?> getAllSubscriptions() {
        try {
            List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
            return ResponseEntity.ok(subscriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching subscriptions: " + e.getMessage());
        }
    }

    @GetMapping("/subscription/{id}")
    public ResponseEntity<?> getSubscriptionById(@PathVariable int id) {
        try {
            Subscription subscription = subscriptionService.getSubscriptionById(id)
                    .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + id));
            return ResponseEntity.ok(subscription);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching subscription: " + e.getMessage());
        }
    }

    // Read Subscription Features
    @GetMapping("/subscription/{subscriptionId}/features")
    public ResponseEntity<?> getSubscriptionFeaturesBySubscriptionId(@PathVariable int subscriptionId) {
        try {
            // Fetch the subscription by its ID
            subscriptionService.getSubscriptionById(subscriptionId)
                    .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));
            
            // Get all features associated with the specific subscription
            List<SubscriptionFeatures> features = subscriptionService.getAllServices().stream()
                    .filter(feature -> feature.getSubscription().getSubscriptionID() == subscriptionId)
                    .toList();
            
            if (features.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No features found for subscription with id: " + subscriptionId);
            }
            
            return ResponseEntity.ok(features);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching subscription features: " + e.getMessage());
        }
    }


    @GetMapping("/subscription-feature/{id}")
    public ResponseEntity<?> getSubscriptionFeatureById(@PathVariable int id) {
        try {
            SubscriptionFeatures feature = subscriptionService.getServiceById(id)
                    .orElseThrow(() -> new RuntimeException("Subscription feature not found with id: " + id));
            return ResponseEntity.ok(feature);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching subscription feature: " + e.getMessage());
        }
    }
    
    // Update Facility by ID
    @PutMapping("/facility/{id}")
    public ResponseEntity<?> updateFacility(@PathVariable int id, @RequestBody Facility facility) {
        try {
            Facility updatedFacility = facilityService.updateFacility(id, facility);
            return ResponseEntity.ok(updatedFacility);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating facility: " + e.getMessage());
        }
    }

    // Delete Facility by ID
    @DeleteMapping("/facility/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable int id) {
        try {
            facilityService.deleteFacility(id);
            return ResponseEntity.status(HttpStatus.OK).body("Facility deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting facility: " + e.getMessage());
        }
    }

    // Update Subscription by ID
    @PutMapping("/subscription/{id}")
    public ResponseEntity<?> updateSubscription(@PathVariable int id, @RequestBody Subscription updatedSubscription) {
        try {
            Subscription updated = subscriptionService.updateSubscription(id, updatedSubscription);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating subscription: " + e.getMessage());
        }
    }

    // Delete Subscription by ID
    @DeleteMapping("/subscription/{id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable int id) {
        try {
            subscriptionService.deleteSubscription(id);
            return ResponseEntity.status(HttpStatus.OK).body("Subscription deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting subscription: " + e.getMessage());
        }
    }

    // Update Subscription Feature by ID
    @PutMapping("/subscription-feature/{id}")
    public ResponseEntity<?> updateSubscriptionFeature(@PathVariable int id, @RequestBody SubscriptionFeatures updatedFeature) {
        try {
            SubscriptionFeatures updated = subscriptionService.updateService(id, updatedFeature);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating subscription feature: " + e.getMessage());
        }
    }

    // Delete Subscription Feature by ID
    @DeleteMapping("/subscription-feature/{id}")
    public ResponseEntity<String> deleteSubscriptionFeature(@PathVariable int id) {
        try {
            subscriptionService.deleteService(id);
            return ResponseEntity.status(HttpStatus.OK).body("Subscription feature deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting subscription feature: " + e.getMessage());
        }
    }

}
