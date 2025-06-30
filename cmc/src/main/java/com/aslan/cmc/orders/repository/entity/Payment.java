package com.aslan.cmc.orders.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    private String id;    // 결제 고유 ID (UUID)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;    // 주문

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;    // 결제 상태

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;    // 결제 수단

    private int amount;    // 결제 금액

    @Column(name = "paid_at")
    private LocalDateTime paidAt;    // 결제 완료 시간

    @Column(name = "pg_token")
    private String pgToken;    // 결제 게이트웨이 토큰
    
    @Column(name = "kakao_tid")
    private String kakaoTid;    // 카카오페이 거래 ID

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;    // 생성일시

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;    // 수정일시
} 