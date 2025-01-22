package com.dilmah.hostel.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/superadmin")
public class HellowController {

    @PostMapping("/login")
    public String login() {
        System.out.println("Hellow endpoint hit!");
        return "Hello";
    }
}