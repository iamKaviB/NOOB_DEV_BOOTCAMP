package com.noobdevs.day7_test2.service.impl;

import com.noobdevs.day7_test2.dto.UserRequestDTO;
import com.noobdevs.day7_test2.dto.UserResponseDTO;
import com.noobdevs.day7_test2.mapper.UserMapper;
import com.noobdevs.day7_test2.model.User;
import com.noobdevs.day7_test2.model.UserProfile;
import com.noobdevs.day7_test2.repository.UserRepository;
import com.noobdevs.day7_test2.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existing.setUsername(dto.getUsername());
        if (dto.getUserProfile() != null) {
            if (existing.getUserProfile() != null) {
                existing.getUserProfile().setBio(dto.getUserProfile().getBio());
                existing.getUserProfile().setPhoneNumber(dto.getUserProfile().getPhoneNumber());
            } else {
                UserProfile newProfile = new UserProfile();
                newProfile.setBio(dto.getUserProfile().getBio());
                newProfile.setPhoneNumber(dto.getUserProfile().getPhoneNumber());
                existing.setUserProfile(newProfile);
            }
        }
        return userMapper.toResponseDTO(userRepository.save(existing));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.deleteById(id);
    }
}
