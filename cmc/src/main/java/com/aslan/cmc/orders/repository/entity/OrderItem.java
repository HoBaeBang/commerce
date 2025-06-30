package com.aslan.cmc.orders.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 주문 상품 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;    // 주문

    @Column(name = "product_id")
    private Long productId;    // 상품 ID

    @Column(name = "product_name")
    private String productName;    // 주문 시점의 상품명 (이력 보존)

    private int price;    // 주문 시점의 상품 가격 (이력 보존)

    private int quantity;    // 주문 수량

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;    // 생성일시
} 