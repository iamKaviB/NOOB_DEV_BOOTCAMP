package com.noobdevs.day7_test2.service;

import com.noobdevs.day7_test2.dto.UserProfileRequestDTO;
import com.noobdevs.day7_test2.dto.UserProfileResponseDTO;

import java.util.List;

public interface UserProfileService {
    List<UserProfileResponseDTO> getAllProfiles();
    UserProfileResponseDTO getProfileById(Long id);
    UserProfileResponseDTO createProfile(UserProfileRequestDTO dto);
    UserProfileResponseDTO updateProfile(Long id, UserProfileRequestDTO dto);
    void deleteProfile(Long id);
}
