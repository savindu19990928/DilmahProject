package com.dilmah.hostel.service;

import com.dilmah.hostel.model.*;
import com.dilmah.hostel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WardenRepository wardenRepository;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private FacilityAdminRepository facilityAdminRepository;

    @Autowired
    private SecurityPersonalRepository securityPersonalRepository;

    @Autowired
    private HostellerRepository hostellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUserID(),
                user.getPassword(),
                getUserRoles(user)
        );
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUserID(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    
    public SuperAdmin findSuperAdminByUsername(String username) {
        return superAdminRepository.findByUserID(username)
                .orElseThrow(() -> new UsernameNotFoundException("SuperAdmin not found with username: " + username));
    }

    public Warden findWardenByUsername(String username) {
        return wardenRepository.findByUserID(username)
                .orElseThrow(() -> new UsernameNotFoundException("Warden not found with username: " + username));
    }

    public FacilityAdmin findFacilityAdminByUsername(String username) {
        return facilityAdminRepository.findByUserID(username)
                .orElseThrow(() -> new UsernameNotFoundException("FacilityAdmin not found with username: " + username));
    }

    public SecurityPersonal findSecurityPersonalByUsername(String username) {
        return securityPersonalRepository.findByUserID(username)
                .orElseThrow(() -> new UsernameNotFoundException("SecurityPersonal not found with username: " + username));
    }

    public Hosteller findHostellerByUsername(String username) {
        return hostellerRepository.findByUserID(username)
                .orElseThrow(() -> new UsernameNotFoundException("Hosteller not found with username: " + username));
    }


    private List<GrantedAuthority> getUserRoles(User user) {
        String role = null;
        if (user instanceof Warden) {
            role = "ROLE_WARDEN";
        } else if (user instanceof SuperAdmin) {
            role = "ROLE_SUPERADMIN";
        } else if (user instanceof FacilityAdmin) {
            role = "ROLE_FACILITYADMIN";
        } else if (user instanceof SecurityPersonal) {
            role = "ROLE_SECURITYPERSONAL";
        } else if (user instanceof Hosteller) {
            role = "ROLE_HOSTELLER";
        }
        return List.of(new SimpleGrantedAuthority(role));
    }
    
    public String registerUser(User user) {
        if (isUserIdExists(user.getUserID())) {
            throw new IllegalArgumentException("User with ID " + user.getUserID() + " already exists.");
        }
        
        saveUserToRepository(user);

        return user.getClass().getSimpleName() + " registered successfully!";
    }

    private boolean isUserIdExists(String userID) {
        return wardenRepository.existsById(userID)
                || superAdminRepository.existsById(userID)
                || facilityAdminRepository.existsById(userID)
                || securityPersonalRepository.existsById(userID)
                || hostellerRepository.existsById(userID);
    }

    private void saveUserToRepository(User user) {
        if (user instanceof Warden) {
            wardenRepository.save((Warden) user);
        } else if (user instanceof SuperAdmin) {
            superAdminRepository.save((SuperAdmin) user);
        } else if (user instanceof FacilityAdmin) {
            facilityAdminRepository.save((FacilityAdmin) user);
        } else if (user instanceof SecurityPersonal) {
            securityPersonalRepository.save((SecurityPersonal) user);
        } else if (user instanceof Hosteller) {
            hostellerRepository.save((Hosteller) user);
        } else {
            throw new IllegalArgumentException("Cannot register user of unknown type: " + user.getClass().getSimpleName());
        }
    }
    
    public String deleteUser(String userID, String userType) {
        switch (userType.toLowerCase()) {
            case "superadmin":
                SuperAdmin superAdmin = findSuperAdminByUsername(userID);
                superAdminRepository.delete(superAdmin);
                break;

            case "warden":
                Warden warden = findWardenByUsername(userID);
                wardenRepository.delete(warden);
                break;

            case "facilityadmin":
                FacilityAdmin facilityAdmin = findFacilityAdminByUsername(userID);
                facilityAdminRepository.delete(facilityAdmin);
                break;

            case "securitypersonal":
                SecurityPersonal securityPersonal = findSecurityPersonalByUsername(userID);
                securityPersonalRepository.delete(securityPersonal);
                break;

            case "hosteller":
                Hosteller hosteller = findHostellerByUsername(userID);
                hostellerRepository.delete(hosteller);
                break;

            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        return userType + " with ID " + userID + " deleted successfully!";
    }

    public String updateUser(String userID, User updatedUser, String userType) {
        User existingUser;
        
        switch (userType.toLowerCase()) {
            case "warden":
                existingUser = findWardenByUsername(userID);
                break;
            case "superadmin":
                existingUser = findSuperAdminByUsername(userID);
                break;
            case "facilityadmin":
                existingUser = findFacilityAdminByUsername(userID);
                break;
            case "hosteller":
                existingUser = findHostellerByUsername(userID);
                break;
            case "securitypersonal":
                existingUser = findSecurityPersonalByUsername(userID);
                break;
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
        
        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }
        
        if (updatedUser.getContactDetails() != null) {
            existingUser.setContactDetails(updatedUser.getContactDetails());
        }
        
        switch (userType.toLowerCase()) {
            case "warden":
                if (existingUser instanceof Warden && updatedUser instanceof Warden) {
                    Warden existingWarden = (Warden) existingUser;
                    Warden updatedWarden = (Warden) updatedUser;
                    if (updatedWarden.getInChargeName() != null) {
                        existingWarden.setInChargeName(updatedWarden.getInChargeName());
                    }
                    if (updatedWarden.getInChargeMobile() != null) {
                        existingWarden.setInChargeMobile(updatedWarden.getInChargeMobile());
                    }
                    wardenRepository.save(existingWarden);
                }
                break;
            case "facilityadmin":
                if (existingUser instanceof FacilityAdmin && updatedUser instanceof FacilityAdmin) {
                    FacilityAdmin existingFacilityAdmin = (FacilityAdmin) existingUser;
                    FacilityAdmin updatedFacilityAdmin = (FacilityAdmin) updatedUser;
                    if (updatedFacilityAdmin.getFacility() != null) {
                        existingFacilityAdmin.setFacility(updatedFacilityAdmin.getFacility());
                    }
                    facilityAdminRepository.save(existingFacilityAdmin);
                }
                break;
            case "hosteller":
                if (existingUser instanceof Hosteller && updatedUser instanceof Hosteller) {
                    Hosteller existingHosteller = (Hosteller) existingUser;
                    Hosteller updatedHosteller = (Hosteller) updatedUser;
                    if (updatedHosteller.getRoom() != null) {
                        existingHosteller.setRoom(updatedHosteller.getRoom());
                    }
                    if (updatedHosteller.getSalary() != null) {
                        existingHosteller.setSalary(updatedHosteller.getSalary());
                    }
                    if (updatedHosteller.getIsSick() != null) {
                        existingHosteller.setIsSick(updatedHosteller.getIsSick());
                    }
                    hostellerRepository.save(existingHosteller);
                }
                break;
            case "securitypersonal":
                if (existingUser instanceof SecurityPersonal && updatedUser instanceof SecurityPersonal) {
                    SecurityPersonal existingSecurityPersonal = (SecurityPersonal) existingUser;
                    securityPersonalRepository.save(existingSecurityPersonal);
                }
                break;
            case "superadmin":
                if (existingUser instanceof SuperAdmin && updatedUser instanceof SuperAdmin) {
                    SuperAdmin existingSuperAdmin = (SuperAdmin) existingUser;
                    superAdminRepository.save(existingSuperAdmin);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }

        return existingUser.getClass().getSimpleName() + " updated successfully!";
    }
    
    public List<Map<String, Object>> findAllHostellers() {
        List<Hosteller> hostellers = hostellerRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Hosteller hosteller : hostellers) {
            Map<String, Object> hostellerData = new HashMap<>();
            hostellerData.put("data", hosteller);
            hostellerData.put("roomNumber", hosteller.getRoom() != null ? hosteller.getRoom().getRoomNumber() : null);
            result.add(hostellerData);
        }
        return result;
    }
}
