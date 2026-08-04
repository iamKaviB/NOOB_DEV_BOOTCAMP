package com.noobdevs.day10_maven.controller;

import com.noobdevs.day10_maven.dto.AdminDTO;
import com.noobdevs.day10_maven.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Admin-only endpoints — SecurityConfig maps "/api/admins/**" to
 * hasRole("ADMIN"), so a CLIENT-role JWT gets 403 here. createAdmin lets an
 * already-authenticated admin create another one; the very first admin
 * comes from {@link com.noobdevs.day10_maven.config.AdminSeeder} on startup.
 */
@RestController
@RequestMapping("/api/admins")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public AdminDTO createAdmin(@RequestBody AdminDTO adminDTO) {
        return adminService.createAdmin(adminDTO);
    }

    @GetMapping
    public List<AdminDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminDTO getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }
}
