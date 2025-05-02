package com.aslan.cmc.products.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 상세 정보")
public class ProductDetailDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
}
