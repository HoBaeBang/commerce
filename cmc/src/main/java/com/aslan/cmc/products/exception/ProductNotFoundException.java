package com.aslan.cmc.products.exception;

import lombok.Getter;

/**
 * 상품을 찾을 수 없을 때 발생하는 예외
 */
@Getter
public class ProductNotFoundException extends RuntimeException {
    private final Long productId;

    public ProductNotFoundException(Long productId) {
        super("상품을 찾을 수 없습니다: " + productId);
        this.productId = productId;
    }
}
