package com.noobdevs.day7_test2.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @Valid
    private UserProfileRequestDTO userProfile;
}
