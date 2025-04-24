package com.aslan.cmc.products.service;

import com.aslan.cmc.products.data.SortDirection;

public interface ProductsServices {

    public void getProductsList(String keyword, int start, int length, int min_price, int max_price, SortDirection sort);

}
