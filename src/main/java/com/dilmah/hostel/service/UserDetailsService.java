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
    
    public String registerUser(SuperAdmin user) {
    	superAdminRepository.save((SuperAdmin) user);
    	return "SuperAdmin registered successfully!";
    }
}
