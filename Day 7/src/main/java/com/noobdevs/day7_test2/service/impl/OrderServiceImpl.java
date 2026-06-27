package com.noobdevs.day7_test2.service.impl;

import com.noobdevs.day7_test2.dto.OrderRequestDTO;
import com.noobdevs.day7_test2.dto.OrderResponseDTO;
import com.noobdevs.day7_test2.mapper.OrderMapper;
import com.noobdevs.day7_test2.model.Order;
import com.noobdevs.day7_test2.model.Product;
import com.noobdevs.day7_test2.model.User;
import com.noobdevs.day7_test2.repository.OrderRepository;
import com.noobdevs.day7_test2.repository.ProductRepository;
import com.noobdevs.day7_test2.repository.UserRepository;
import com.noobdevs.day7_test2.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return orderMapper.toResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        List<Product> products = productRepository.findAllById(dto.getProductIds());
        Order order = orderMapper.toEntity(dto, user, products);
        return orderMapper.toResponseDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        List<Product> products = productRepository.findAllById(dto.getProductIds());
        existing.setTrackingNumber(dto.getTrackingNumber());
        existing.setUser(user);
        existing.setProducts(products);
        return orderMapper.toResponseDTO(orderRepository.save(existing));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.deleteById(id);
    }
}
