package com.noobdevs.day7_test2.service;

import com.noobdevs.day7_test2.dto.OrderRequestDTO;
import com.noobdevs.day7_test2.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long id);
    List<OrderResponseDTO> getOrdersByUserId(Long userId);
    OrderResponseDTO createOrder(OrderRequestDTO dto);
    OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto);
    void deleteOrder(Long id);
}
