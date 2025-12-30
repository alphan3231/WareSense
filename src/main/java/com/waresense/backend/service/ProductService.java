package com.waresense.backend.service;

import com.waresense.backend.dto.ProductDto;
import com.waresense.backend.entity.Category;
import com.waresense.backend.entity.Product;
import com.waresense.backend.repository.CategoryRepository;
import com.waresense.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDto createProduct(ProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .sku(dto.getSku())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(category)
                .volumePerUnit(dto.getVolumePerUnit())
                .minStockLevel(dto.getMinStockLevel())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToDto(savedProduct);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDto(product);
    }

    private ProductDto mapToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .volumePerUnit(product.getVolumePerUnit())
                .minStockLevel(product.getMinStockLevel())
                .build();
    }
}
