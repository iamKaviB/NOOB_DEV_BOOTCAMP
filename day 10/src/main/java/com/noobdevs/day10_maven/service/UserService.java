package com.noobdevs.day10_maven.service;

import com.noobdevs.day10_maven.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    void deleteUser(Long id);
}
