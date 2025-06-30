package com.aslan.cmc.products.repository;

import com.aslan.cmc.products.repository.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * 상품명에 포함된 특정 문자열을 기준으로 상품을 조회합니다.
     * @param name 검색할 상품명
     * @param pageable 페이징 정보
     * @return 페이징된 상품 목록
     */
    Page<Product> findByNameContaining(String name, Pageable pageable);

    /**
     * 특정 가격 범위 내의 상품을 조회합니다.
     *
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @param pageable 페이징 정보
     * @return 페이징된 상품 목록
     */
    Page<Product> findByPriceBetween(int minPrice, int maxPrice, Pageable pageable);

    /**
     * 상품 명에 특정 문자열이 포함되고 특정 가격 범위 내의 상품을 조회합니다.
     * @param name 검색할 상품명
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @param pageable 페이징 정보
     * @return 페이징된 상품 목록
     */
    Page<Product> findByNameContainingAndPriceBetween(String name, int minPrice, int maxPrice, Pageable pageable);
}
