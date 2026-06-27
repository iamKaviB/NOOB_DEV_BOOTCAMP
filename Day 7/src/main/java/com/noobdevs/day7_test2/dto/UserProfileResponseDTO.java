package com.noobdevs.day7_test2.dto;

import lombok.Data;

// No User reference here — breaks UserProfile ↔ User circular dependency
@Data
public class UserProfileResponseDTO {
    private Long id;
    private String bio;
    private String phoneNumber;
}
