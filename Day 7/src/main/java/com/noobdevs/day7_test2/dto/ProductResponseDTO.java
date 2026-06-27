package com.noobdevs.day7_test2.dto;

import lombok.Data;

// No Order reference here — breaks Product ↔ Order circular dependency
@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double price;
}
