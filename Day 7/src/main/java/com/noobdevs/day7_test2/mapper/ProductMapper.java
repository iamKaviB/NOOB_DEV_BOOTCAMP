package com.noobdevs.day7_test2.mapper;

import com.noobdevs.day7_test2.dto.ProductRequestDTO;
import com.noobdevs.day7_test2.dto.ProductResponseDTO;
import com.noobdevs.day7_test2.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponseDTO(Product entity) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public Product toEntity(ProductRequestDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}
