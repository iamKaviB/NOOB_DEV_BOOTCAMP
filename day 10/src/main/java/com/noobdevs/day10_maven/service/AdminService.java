package com.noobdevs.day10_maven.service;

import com.noobdevs.day10_maven.dto.AdminDTO;

import java.util.List;

public interface AdminService {

    AdminDTO createAdmin(AdminDTO adminDTO);

    List<AdminDTO> getAllAdmins();

    AdminDTO getAdminById(Long id);
}
