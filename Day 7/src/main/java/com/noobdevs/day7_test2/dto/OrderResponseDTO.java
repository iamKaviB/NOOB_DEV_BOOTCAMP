package com.noobdevs.day7_test2.dto;

import lombok.Data;

import java.util.List;

// Uses userId (Long) instead of full User object — breaks Order ↔ User circular dependency
@Data
public class OrderResponseDTO {
    private Long id;
    private String trackingNumber;
    private Long userId;
    private List<ProductResponseDTO> products;
}
