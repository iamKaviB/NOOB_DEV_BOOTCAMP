package com.noobdevs.day10_maven.service.impl;

import com.noobdevs.day10_maven.dto.UserDTO;
import com.noobdevs.day10_maven.model.Admin;
import com.noobdevs.day10_maven.model.Client;
import com.noobdevs.day10_maven.model.User;
import com.noobdevs.day10_maven.repository.UserRepository;
import com.noobdevs.day10_maven.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return toDTO(findUserOrThrow(id));
    }

    @Override
    public void deleteUser(Long id) {
        findUserOrThrow(id);
        userRepository.deleteById(id);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
    }

    private UserDTO toDTO(User user) {
        String userType = user instanceof Admin ? "ADMIN" : user instanceof Client ? "CLIENT" : "USER";
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), userType);
    }
}
