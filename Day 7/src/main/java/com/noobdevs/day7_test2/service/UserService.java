package com.noobdevs.day7_test2.service;

import com.noobdevs.day7_test2.dto.UserRequestDTO;
import com.noobdevs.day7_test2.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO updateUser(Long id, UserRequestDTO dto);
    void deleteUser(Long id);
}
