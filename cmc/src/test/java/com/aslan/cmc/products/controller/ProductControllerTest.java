package com.aslan.cmc.products.controller;

import com.aslan.cmc.products.data.dto.ProductDetailDto;
import com.aslan.cmc.products.data.dto.ProductListResponseDto;
import com.aslan.cmc.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getProductsList_성공() throws Exception {
        // given
        List<ProductDetailDto> products = Arrays.asList(
                ProductDetailDto.builder()
                        .id(1L)
                        .name("테스트 상품 1")
                        .price(10000)
                        .imageUrl("http://example.com/image1.jpg")
                        .stockQuantity(10)
                        .build(),
                ProductDetailDto.builder()
                        .id(2L)
                        .name("테스트 상품 2")
                        .price(20000)
                        .imageUrl("http://example.com/image2.jpg")
                        .stockQuantity(5)
                        .build()
        );

        ProductListResponseDto response = ProductListResponseDto.builder()
                .totalItems(2)
                .totalPages(1)
                .currentPage(1)
                .products(products)
                .build();

        when(productService.getProductsList(anyString(), anyInt(), anyInt(), any(), any(), any()))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/v1/products")
                        .param("keyword", "테스트")
                        .param("start", "1")
                        .param("length", "10")
                        .param("sort", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(2))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products[0].name").value("테스트 상품 1"));
    }

    @Test
    void getProductDetail_성공() throws Exception {
        // given
        ProductDetailDto product = ProductDetailDto.builder()
                .id(1L)
                .name("테스트 상품")
                .price(10000)
                .imageUrl("http://example.com/image.jpg")
                .stockQuantity(10)
                .build();

        when(productService.getProductDetail(1L)).thenReturn(product);

        // when & then
        mockMvc.perform(get("/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("테스트 상품"))
                .andExpect(jsonPath("$.price").value(10000));
    }

    @Test
    void getProductsList_잘못된_키워드_길이() throws Exception {
        // when & then
        mockMvc.perform(get("/v1/products")
                        .param("keyword", "a".repeat(256))) // 255자 초과
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductsList_잘못된_가격_범위() throws Exception {
        // when & then
        mockMvc.perform(get("/v1/products")
                        .param("minPrice", "10000")
                        .param("maxPrice", "5000")) // 최소가격이 최대가격보다 큼
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductsList_기본_파라미터() throws Exception {
        // given
        List<ProductDetailDto> products = Arrays.asList(
                ProductDetailDto.builder()
                        .id(1L)
                        .name("기본 상품")
                        .price(10000)
                        .imageUrl("http://example.com/image.jpg")
                        .stockQuantity(10)
                        .build()
        );

        ProductListResponseDto response = ProductListResponseDto.builder()
                .totalItems(1)
                .totalPages(1)
                .currentPage(1)
                .products(products)
                .build();

        when(productService.getProductsList(anyString(), anyInt(), anyInt(), any(), any(), any()))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(1));
    }

    @Test
    void getProductsList_가격_범위_필터링() throws Exception {
        // given
        List<ProductDetailDto> products = Arrays.asList(
                ProductDetailDto.builder()
                        .id(1L)
                        .name("가격 범위 상품")
                        .price(15000)
                        .imageUrl("http://example.com/image.jpg")
                        .stockQuantity(10)
                        .build()
        );

        ProductListResponseDto response = ProductListResponseDto.builder()
                .totalItems(1)
                .totalPages(1)
                .currentPage(1)
                .products(products)
                .build();

        when(productService.getProductsList(anyString(), anyInt(), anyInt(), any(), any(), any()))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/v1/products")
                        .param("minPrice", "10000")
                        .param("maxPrice", "20000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(1));
    }
} 