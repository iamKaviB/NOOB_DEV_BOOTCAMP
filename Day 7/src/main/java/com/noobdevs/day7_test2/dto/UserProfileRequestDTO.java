package com.noobdevs.day7_test2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserProfileRequestDTO {

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number must be 7–15 digits, optionally starting with +")
    private String phoneNumber;
}
