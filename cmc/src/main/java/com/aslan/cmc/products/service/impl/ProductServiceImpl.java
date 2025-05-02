package com.aslan.cmc.products.service.impl;

import com.aslan.cmc.products.data.SortDirection;
import com.aslan.cmc.products.data.dto.ProductListResponseDto;
import com.aslan.cmc.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Override
    public ProductListResponseDto getProductsList(String keyword, Integer start, Integer length, Integer minPrice, Integer maxPrice, SortDirection sort) {
        //TODO : 서비스 구성 필요
    }
}
