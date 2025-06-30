package com.aslan.cmc.products.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //상품 고유 ID

    private String name;    // 상품명

    private int price;      // 상품 가격

    @Column(name = "image_url")
    private String imageUrl;    // 이미지 URL

    @Column(name = "stock_quantity")
    private int stockQuantity;      // 상품 재고

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;    // 등록일자

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;    // 수정일자
}
