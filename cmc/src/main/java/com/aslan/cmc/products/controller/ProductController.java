package com.aslan.cmc.products.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController("/v1/products")
public class ProductController {

    @Operation(summary = "상품 리스트 조회 및 검색")
    @GetMapping
    public ResponseEntity getProductsList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer start,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(20) Integer length,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false, defaultValue = "asc") String sort){

        throw new NotImplementedException();
    }
}
