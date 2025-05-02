package com.aslan.cmc.products.controller;

import com.aslan.cmc.products.service.ProductService;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductListResponseDto productListResponse;
    private ProductDetailDto productDetail;

    @BeforeEach
    void setUp() {
        // MockMvc 설정
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        // 테스트 데이터 설정
        ProductSummaryDto product1 = ProductSummaryDto.builder()
                .id(1L)
                .name("상품 1")
                .price(10000)
                .stockQuantity(100)
                .build();

        ProductSummaryDto product2 = ProductSummaryDto.builder()
                .id(2L)
                .name("상품 2")
                .price(20000)
                .stockQuantity(50)
                .build();

        productListResponse = ProductListResponseDto.builder()
                .totalItems(2)
                .totalPages(1)
                .currentPage(1)
                .products(Arrays.asList(product1, product2))
                .build();

        productDetail = ProductDetailDto.builder()
                .id(1L)
                .name("상품 1")
                .price(10000)
                .stockQuantity(100)
                .build();
    }

    // RED CASE

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 과도하게 긴 단어 255 이상")
    void getProductsWithTooLongKeyWord() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 유효하지 않은 페이지 크기 (1미만)")
    void getProductsWithInvalidLengthBelowMin() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 유효하지 않은 페이지 크기 (20 초과)")
    void getProductsWithInvalidLengthAboveMax() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 유효하지 않은 최소 가격 (음수)")
    void getProductsWithNegativeMinPrice() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 유효하지 않은 최대 가격 (음수)")
    void getProductsWithNegativeMaxPrice() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 잘못된 가격 범위")
    void getProductsWithInvalidPriceRange() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 유효하지 않은 정렬 파라미터")
    void getProductsWithInvalidSort() throws Exception {
        throw new NotImplementedException();
    }

    // GREEN CASE

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 기본 파라미터")
    void getProductsWithDefaultParams() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 검색어 포함")
    void getProductsWithKeyword() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 가격 범위 필터링")
    void getProductsWithPriceRange() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 페이징")
    void getProductsWithPaging() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    @Disabled
    @DisplayName("상품 목록 조회 - 정렬")
    void getProductsWithSorting() throws Exception {
        throw new NotImplementedException();
    }

}
