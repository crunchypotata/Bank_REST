package com.example.bankcards.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AdminController {

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasRole('USER')")
    public String userProfile() {
        return "user-profile";
    }

}