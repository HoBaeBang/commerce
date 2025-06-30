package com.aslan.cmc.orders.service;

import com.aslan.cmc.orders.data.dto.OrderRequestDto;
import com.aslan.cmc.orders.data.dto.OrderResponseDto;

public interface OrderService {
    /**
     * 주문을 생성합니다.
     *
     * @param orderRequest 주문 요청 정보
     * @return 주문 응답 정보
     */
    OrderResponseDto createOrder(OrderRequestDto orderRequest);

    /**
     * 주문 정보를 조회합니다.
     *
     * @param orderId 주문 ID
     * @return 주문 응답 정보
     */
    OrderResponseDto getOrder(String orderId);

    /**
     * 주문을 취소합니다.
     *
     * @param orderId 주문 ID
     */
    void cancelOrder(String orderId);

    /**
     * 만료된 주문들을 처리합니다.
     */
    void processExpiredOrders();

    /**
     * 결제를 승인합니다.
     *
     * @param orderId 주문 ID
     * @param pgToken PG 토큰
     */
    void approvePayment(String orderId, String pgToken);
} 