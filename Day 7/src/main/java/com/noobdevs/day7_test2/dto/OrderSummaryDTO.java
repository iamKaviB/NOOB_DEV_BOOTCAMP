package com.noobdevs.day7_test2.dto;

import lombok.Data;

// Lightweight order info embedded in UserResponseDTO to avoid User ↔ Order circular dependency
@Data
public class OrderSummaryDTO {
    private Long id;
    private String trackingNumber;
}
