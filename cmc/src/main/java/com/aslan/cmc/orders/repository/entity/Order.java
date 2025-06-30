package com.aslan.cmc.orders.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;    // 주문 고유 ID (UUID)

    @Column(name = "order_date")
    private LocalDateTime orderDate;    // 주문 일시

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;    // 주문 상태

    @Column(name = "total_product_amount")
    private int totalProductAmount;    // 상품 금액 합계

    @Column(name = "shipping_amount")
    private int shippingAmount;    // 배송비 합계

    @Column(name = "payment_amount")
    private int paymentAmount;    // 최종 결제 금액

    @Column(name = "payment_due_date")
    private LocalDateTime paymentDueDate;    // 결제 마감 시간

    @Column(name = "kakaopay_ready_url")
    private String kakaopayReadyUrl;    // 카카오페이 결제 준비 URL

    // 배송지 정보
    @Column(name = "shipping_zipcode")
    private String shippingZipcode;    // 배송지 우편번호

    @Column(name = "shipping_address")
    private String shippingAddress;    // 배송지 기본 주소

    @Column(name = "shipping_address_detail")
    private String shippingAddressDetail;    // 배송지 상세 주소

    // 수령인 정보
    @Column(name = "receiver_name")
    private String receiverName;    // 수령인 이름

    @Column(name = "receiver_phone")
    private String receiverPhone;    // 수령인 전화번호

    @Column(columnDefinition = "TEXT")
    private String memo;    // 배송 메모

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;    // 생성일시

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;    // 수정일시

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;
} 