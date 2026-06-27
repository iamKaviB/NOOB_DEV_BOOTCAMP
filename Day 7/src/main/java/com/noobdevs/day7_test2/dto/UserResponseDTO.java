package com.noobdevs.day7_test2.dto;

import com.noobdevs.day7_test2.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private UserProfileResponseDTO userProfile;
    private List<OrderSummaryDTO> orders;

}
