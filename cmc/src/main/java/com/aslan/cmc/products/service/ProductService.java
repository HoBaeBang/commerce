package com.aslan.cmc.products.service;

import com.aslan.cmc.products.data.SortDirection;
import com.aslan.cmc.products.data.dto.ProductListResponseDto;

public interface ProductService {
    /**
     * 상품 목록을 조회하고 검색합니다.
     *
     * @param keyword  검색 키워드 (선택적)
     * @param start    시작 행 번호
     * @param length   페이지당 상품 수 (1-20)
     * @param minPrice 최소 가격 (선택적)
     * @param maxPrice 최대 가격 (선택적)
     * @param sort     정렬 방식 (asc, desc)
     * @return 상품 목록 응답 DTO
     */
    ProductListResponseDto getProductsList(String keyword, Integer start, Integer length, Integer minPrice, Integer maxPrice, SortDirection sort);

}
