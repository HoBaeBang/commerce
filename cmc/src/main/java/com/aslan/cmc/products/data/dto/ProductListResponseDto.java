package com.aslan.cmc.products.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 목록 응답")
public class ProductListResponseDto {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<ProductDetailDto> products;
}
