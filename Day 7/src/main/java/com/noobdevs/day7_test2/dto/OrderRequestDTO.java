package com.noobdevs.day7_test2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "At least one product ID is required")
    private List<Long> productIds;
}
