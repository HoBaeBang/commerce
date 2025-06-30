package com.aslan.cmc.products.controller;

import com.aslan.cmc.products.data.SortDirection;
import com.aslan.cmc.products.data.dto.ProductDetailDto;
import com.aslan.cmc.products.data.dto.ProductListResponseDto;
import com.aslan.cmc.products.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "상품", description = "상품 관련 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 리스트 조회 및 검색")
    @GetMapping
    public ResponseEntity<ProductListResponseDto> getProductsList(
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size,
            @Parameter(description = "정렬 방향") @RequestParam(defaultValue = "desc") String direction,
            @Parameter(description = "검색 키워드") @RequestParam(required = false) String keyword,
            @Parameter(description = "최소 가격") @RequestParam(required = false) Integer minPrice,
            @Parameter(description = "최대 가격") @RequestParam(required = false) Integer maxPrice) {

        // 페이징을 위한 start 계산 (페이지는 1부터 시작)
        Integer start = (page - 1) * size + 1;
        
        // SortDirection enum 변환
        SortDirection sortDirection = SortDirection.fromString(direction);
        
        ProductListResponseDto response = productService.getProductsList(
            keyword, start, size, minPrice, maxPrice, sortDirection);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "상품 상세 조회")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetail(
            @Parameter(description = "조회할 상품 ID") @PathVariable Long productId) {
        
        ProductDetailDto product = productService.getProductDetail(productId);
        return ResponseEntity.ok(product);
    }
}
