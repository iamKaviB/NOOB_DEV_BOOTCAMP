package com.noobdevs.day10_maven.service.impl;

import com.noobdevs.day10_maven.dto.AdminDTO;
import com.noobdevs.day10_maven.model.Admin;
import com.noobdevs.day10_maven.repository.AdminRepository;
import com.noobdevs.day10_maven.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        String hashedPassword = passwordEncoder.encode(adminDTO.getPassword());
        Admin admin = new Admin(adminDTO.getName(), adminDTO.getEmail(), hashedPassword, adminDTO.getDepartment());
        return toDTO(adminRepository.save(admin));
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public AdminDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found: " + id));
        return toDTO(admin);
    }

    private AdminDTO toDTO(Admin admin) {
        return new AdminDTO(admin.getId(), admin.getName(), admin.getEmail(), "ADMIN", null, admin.getDepartment());
    }
}
