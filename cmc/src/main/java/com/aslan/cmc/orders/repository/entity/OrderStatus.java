package com.aslan.cmc.orders.repository.entity;

public enum OrderStatus {
    PENDING,           // 주문 생성, 결제 대기
    PAID,              // 결제 완료
    CANCELLED,         // 주문 취소
    PAYMENT_FAILED,    // 결제 실패
    PAYMENT_ERROR,     // 결제 검증 실패
    EXPIRED            // 주문 만료 (30분 내 미결제)
} 