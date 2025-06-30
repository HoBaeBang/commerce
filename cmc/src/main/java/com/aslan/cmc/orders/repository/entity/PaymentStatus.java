package com.aslan.cmc.orders.repository.entity;

public enum PaymentStatus {
    SUCCESS,    // 결제 성공
    FAILED,     // 결제 실패
    PENDING,    // 결제 대기
    CANCELED    // 결제 취소
} 