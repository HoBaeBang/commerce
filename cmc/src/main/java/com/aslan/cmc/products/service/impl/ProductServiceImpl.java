package com.aslan.cmc.products.service.impl;

import com.aslan.cmc.products.data.SortDirection;
import com.aslan.cmc.products.data.dto.ProductDetailDto;
import com.aslan.cmc.products.data.dto.ProductListResponseDto;
import com.aslan.cmc.products.repository.ProductRepository;
import com.aslan.cmc.products.repository.entity.Product;
import com.aslan.cmc.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductListResponseDto getProductsList(String keyword, Integer start, Integer length, Integer minPrice, Integer maxPrice, SortDirection sort) {
        // 페이징 설정 (start는 1부터 시작하므로 0부터 시작하는 인덱스로 변환)
        int page = (start - 1) / length;
        int size = length;
        
        // 정렬 설정
        Sort sortObj = Sort.by(sort == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, "price");
        Pageable pageable = PageRequest.of(page, size, sortObj);
        
        Page<Product> productPage;
        
        // 검색 조건에 따른 쿼리 분기
        if (keyword != null && !keyword.trim().isEmpty()) {
            if (minPrice != null && maxPrice != null) {
                // 키워드 + 가격 범위 검색
                productPage = productRepository.findByNameContainingAndPriceBetween(keyword.trim(), minPrice, maxPrice, pageable);
            } else if (minPrice != null) {
                // 키워드 + 최소 가격 검색
                productPage = productRepository.findByNameContainingAndPriceBetween(keyword.trim(), minPrice, Integer.MAX_VALUE, pageable);
            } else if (maxPrice != null) {
                // 키워드 + 최대 가격 검색
                productPage = productRepository.findByNameContainingAndPriceBetween(keyword.trim(), 0, maxPrice, pageable);
            } else {
                // 키워드만 검색
                productPage = productRepository.findByNameContaining(keyword.trim(), pageable);
            }
        } else {
            if (minPrice != null && maxPrice != null) {
                // 가격 범위만 검색
                productPage = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
            } else if (minPrice != null) {
                // 최소 가격만 검색
                productPage = productRepository.findByPriceBetween(minPrice, Integer.MAX_VALUE, pageable);
            } else if (maxPrice != null) {
                // 최대 가격만 검색
                productPage = productRepository.findByPriceBetween(0, maxPrice, pageable);
            } else {
                // 전체 상품 조회
                productPage = productRepository.findAll(pageable);
            }
        }
        
        // DTO 변환
        List<ProductDetailDto> productDtos = productPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return ProductListResponseDto.builder()
                .totalItems((int) productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .currentPage(page + 1)
                .products(productDtos)
                .build();
    }

    @Override
    public ProductDetailDto getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new com.aslan.cmc.products.exception.ProductNotFoundException(productId));
        
        return convertToDto(product);
    }

    @Override
    public Page<ProductDetailDto> getProductsList(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        
        return productPage.map(this::convertToDto);
    }
    
    private ProductDetailDto convertToDto(Product product) {
        return ProductDetailDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stockQuantity(product.getStockQuantity())
                .build();
    }
}
