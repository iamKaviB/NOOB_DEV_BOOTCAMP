package com.noobdevs.day7_test2.service.impl;

import com.noobdevs.day7_test2.dto.UserProfileRequestDTO;
import com.noobdevs.day7_test2.dto.UserProfileResponseDTO;
import com.noobdevs.day7_test2.mapper.UserProfileMapper;
import com.noobdevs.day7_test2.model.UserProfile;
import com.noobdevs.day7_test2.repository.UserProfileRepository;
import com.noobdevs.day7_test2.service.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public List<UserProfileResponseDTO> getAllProfiles() {
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileResponseDTO getProfileById(Long id) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + id));
        return userProfileMapper.toResponseDTO(profile);
    }

    @Override
    public UserProfileResponseDTO createProfile(UserProfileRequestDTO dto) {
        UserProfile profile = userProfileMapper.toEntity(dto);
        return userProfileMapper.toResponseDTO(userProfileRepository.save(profile));
    }

    @Override
    public UserProfileResponseDTO updateProfile(Long id, UserProfileRequestDTO dto) {
        UserProfile existing = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + id));
        existing.setBio(dto.getBio());
        existing.setPhoneNumber(dto.getPhoneNumber());
        return userProfileMapper.toResponseDTO(userProfileRepository.save(existing));
    }

    @Override
    public void deleteProfile(Long id) {
        userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + id));
        userProfileRepository.deleteById(id);
    }
}
