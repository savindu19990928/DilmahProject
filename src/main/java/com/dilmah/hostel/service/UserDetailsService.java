package com.dilmah.hostel.service;

import com.dilmah.hostel.model.*;
import com.dilmah.hostel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

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

    private User findUserByUsername(String username) {
        Optional<? extends User> user = wardenRepository.findByUserID(username);
        if (user.isPresent()) return user.get();

        user = superAdminRepository.findByUserID(username);
        if (user.isPresent()) return user.get();

        user = facilityAdminRepository.findByUserID(username);
        if (user.isPresent()) return user.get();

        user = securityPersonalRepository.findByUserID(username);
        if (user.isPresent()) return user.get();

        user = hostellerRepository.findByUserID(username);
        if (user.isPresent()) return user.get();

        throw new UsernameNotFoundException("User not found with username: " + username);
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
    
    private void deleteUserFromRepository(User user) {
        if (user instanceof Warden) {
            wardenRepository.delete((Warden) user);
        } else if (user instanceof SuperAdmin) {
            superAdminRepository.delete((SuperAdmin) user);
        } else if (user instanceof FacilityAdmin) {
            facilityAdminRepository.delete((FacilityAdmin) user);
        } else if (user instanceof SecurityPersonal) {
            securityPersonalRepository.delete((SecurityPersonal) user);
        } else if (user instanceof Hosteller) {
            hostellerRepository.delete((Hosteller) user);
        } else {
            throw new IllegalArgumentException("Cannot delete user of unknown type: " + user.getClass().getSimpleName());
        }
    }
    
    public String deleteUser(String userID) {
        User existingUser = findUserByUsername(userID);

        deleteUserFromRepository(existingUser);
        return "User deleted successfully!";
    }
    
    public String updateUser(String userID, User updatedUser) {
        User existingUser = findUserByUsername(userID);
        
        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getContactDetails() != null) {
            existingUser.setContactDetails(updatedUser.getContactDetails());
        }
        
        
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
        } else if (existingUser instanceof SuperAdmin && updatedUser instanceof SuperAdmin) {
            SuperAdmin existingSuperAdmin = (SuperAdmin) existingUser;
            superAdminRepository.save(existingSuperAdmin);
        } else if (existingUser instanceof FacilityAdmin && updatedUser instanceof FacilityAdmin) {
            FacilityAdmin existingFacilityAdmin = (FacilityAdmin) existingUser;
            FacilityAdmin updatedFacilityAdmin = (FacilityAdmin) updatedUser;

            if (updatedFacilityAdmin.getFacility() != null) {
                existingFacilityAdmin.setFacility(updatedFacilityAdmin.getFacility());
            }
            facilityAdminRepository.save(existingFacilityAdmin);
        } else if (existingUser instanceof Hosteller && updatedUser instanceof Hosteller) {
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
        } else if (existingUser instanceof SecurityPersonal && updatedUser instanceof SecurityPersonal) {
            SecurityPersonal existingSecurityPersonal = (SecurityPersonal) existingUser;
            securityPersonalRepository.save(existingSecurityPersonal);
        } else {
            throw new IllegalArgumentException("Unknown or mismatched user type for update.");
        }

        return existingUser.getClass().getSimpleName() + " updated successfully!";
    }
}
