package com.noobdevs.day7_test2.mapper;

import com.noobdevs.day7_test2.dto.OrderSummaryDTO;
import com.noobdevs.day7_test2.dto.UserRequestDTO;
import com.noobdevs.day7_test2.dto.UserResponseDTO;
import com.noobdevs.day7_test2.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final UserProfileMapper userProfileMapper;

    public UserMapper(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    public UserResponseDTO toResponseDTO(User entity) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setUserProfile(userProfileMapper.toResponseDTO(entity.getUserProfile()));
        dto.setOrders(entity.getOrders() != null
                ? entity.getOrders().stream().map(order -> {
                    OrderSummaryDTO summary = new OrderSummaryDTO();
                    summary.setId(order.getId());
                    summary.setTrackingNumber(order.getTrackingNumber());
                    return summary;
                }).collect(Collectors.toList())
                : Collections.emptyList());
//        dto.setOrders(entity.getOrders());
        return dto;
    }

    public User toEntity(UserRequestDTO dto) {
        User entity = new User();
        entity.setUsername(dto.getUsername());
        if (dto.getUserProfile() != null) {
            entity.setUserProfile(userProfileMapper.toEntity(dto.getUserProfile()));
        }
        return entity;
    }
}
