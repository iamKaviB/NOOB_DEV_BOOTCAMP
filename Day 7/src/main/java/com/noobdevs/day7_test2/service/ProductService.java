package com.noobdevs.day7_test2.service;

import com.noobdevs.day7_test2.dto.ProductRequestDTO;
import com.noobdevs.day7_test2.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO createProduct(ProductRequestDTO dto);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);
    void deleteProduct(Long id);
}
