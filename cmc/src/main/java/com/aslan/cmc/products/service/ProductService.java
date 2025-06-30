package com.aslan.cmc.products.service;

import com.aslan.cmc.products.data.SortDirection;
import com.aslan.cmc.products.data.dto.ProductDetailDto;
import com.aslan.cmc.products.data.dto.ProductListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /**
     * 상품 목록을 페이지네이션으로 조회합니다.
     *
     * @param pageable 페이지 정보
     * @return 상품 목록 페이지
     */
    Page<ProductDetailDto> getProductsList(Pageable pageable);

    /**
     * 상품 상세 정보를 조회합니다.
     *
     * @param productId 상품 ID
     * @return 상품 상세 정보 DTO
     */
    ProductDetailDto getProductDetail(Long productId);
}
