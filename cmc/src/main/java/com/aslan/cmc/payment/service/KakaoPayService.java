package com.aslan.cmc.payment.service;

import com.aslan.cmc.payment.data.dto.KakaoPayApproveResponseDto;
import com.aslan.cmc.payment.data.dto.KakaoPayReadyResponseDto;

public interface KakaoPayService {
    /**
     * 카카오페이 결제 준비를 요청합니다.
     *
     * @param orderId 주문 ID
     * @param itemName 상품명
     * @param totalAmount 총 결제 금액
     * @return 카카오페이 결제 준비 응답
     */
    KakaoPayReadyResponseDto readyPayment(String orderId, String itemName, int totalAmount);

    /**
     * 카카오페이 결제를 승인합니다.
     *
     * @param orderId 주문 ID
     * @param pgToken PG 토큰
     * @return 카카오페이 결제 승인 응답
     */
    KakaoPayApproveResponseDto approvePayment(String orderId, String pgToken);

    /**
     * 카카오페이 결제를 취소합니다.
     *
     * @param orderId 주문 ID
     * @param cancelAmount 취소 금액
     */
    void cancelPayment(String orderId, int cancelAmount);
} 