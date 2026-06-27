package com.noobdevs.day7_test2.mapper;

import com.noobdevs.day7_test2.dto.UserProfileRequestDTO;
import com.noobdevs.day7_test2.dto.UserProfileResponseDTO;
import com.noobdevs.day7_test2.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileResponseDTO toResponseDTO(UserProfile entity) {
        if (entity == null) return null;
        UserProfileResponseDTO dto = new UserProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setBio(entity.getBio());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return dto;
    }

    public UserProfile toEntity(UserProfileRequestDTO dto) {
        UserProfile entity = new UserProfile();
        entity.setBio(dto.getBio());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}
