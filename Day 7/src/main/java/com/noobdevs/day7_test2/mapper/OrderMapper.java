package com.noobdevs.day7_test2.mapper;

import com.noobdevs.day7_test2.dto.OrderRequestDTO;
import com.noobdevs.day7_test2.dto.OrderResponseDTO;
import com.noobdevs.day7_test2.model.Order;
import com.noobdevs.day7_test2.model.Product;
import com.noobdevs.day7_test2.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public OrderResponseDTO toResponseDTO(Order entity) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(entity.getId());
        dto.setTrackingNumber(entity.getTrackingNumber());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setProducts(entity.getProducts() != null
                ? entity.getProducts().stream().map(productMapper::toResponseDTO).collect(Collectors.toList())
                : Collections.emptyList());
        return dto;
    }

    public Order toEntity(OrderRequestDTO dto, User user, List<Product> products) {
        Order entity = new Order();
        entity.setTrackingNumber(dto.getTrackingNumber());
        entity.setUser(user);
        entity.setProducts(products);
        return entity;
    }
}
