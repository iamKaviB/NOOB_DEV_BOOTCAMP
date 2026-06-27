package com.noobdevs.day7_test2.service.impl;

import com.noobdevs.day7_test2.dto.ProductRequestDTO;
import com.noobdevs.day7_test2.dto.ProductResponseDTO;
import com.noobdevs.day7_test2.mapper.ProductMapper;
import com.noobdevs.day7_test2.model.Product;
import com.noobdevs.day7_test2.repository.ProductRepository;
import com.noobdevs.day7_test2.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        return productMapper.toResponseDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        return productMapper.toResponseDTO(productRepository.save(existing));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.deleteById(id);
    }
}
