package com.dilmah.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.dilmah.hostel.util.ClientIpExtractor;
import com.dilmah.hostel.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestBody HttpServletRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String role = authenticate.getAuthorities().iterator().next().getAuthority();
        String ipAddress = null;
		try {
			ipAddress = ClientIpExtractor.getClientIp(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return jwtUtil.generateToken(username, role, ipAddress);
    }
}

