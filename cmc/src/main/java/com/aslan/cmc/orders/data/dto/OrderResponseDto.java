package com.aslan.cmc.orders.data.dto;

import com.aslan.cmc.orders.repository.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 응답")
public class OrderResponseDto {

    @Schema(description = "주문 ID")
    private String orderId;

    @Schema(description = "주문 상태")
    private OrderStatus orderStatus;

    @Schema(description = "상품 금액 합계")
    private int totalProductAmount;

    @Schema(description = "배송비")
    private int shippingAmount;

    @Schema(description = "최종 결제 금액")
    private int paymentAmount;

    @Schema(description = "결제 마감 시간")
    private LocalDateTime paymentDueDate;

    @Schema(description = "카카오페이 결제 준비 URL")
    private String kakaopayReadyUrl;

    @Schema(description = "주문 상품 목록")
    private List<OrderItemResponseDto> orderItems;

    @Schema(description = "배송지 정보")
    private ShippingInfoDto shippingInfo;

    @Schema(description = "주문 일시")
    private LocalDateTime orderDate;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "주문 상품 응답")
    public static class OrderItemResponseDto {
        @Schema(description = "상품 ID")
        private Long productId;

        @Schema(description = "상품명")
        private String productName;

        @Schema(description = "상품 가격")
        private int price;

        @Schema(description = "주문 수량")
        private int quantity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "배송지 정보")
    public static class ShippingInfoDto {
        @Schema(description = "수령인 이름")
        private String receiverName;

        @Schema(description = "수령인 전화번호")
        private String receiverPhone;

        @Schema(description = "배송지 우편번호")
        private String shippingZipcode;

        @Schema(description = "배송지 기본 주소")
        private String shippingAddress;

        @Schema(description = "배송지 상세 주소")
        private String shippingAddressDetail;

        @Schema(description = "배송 메모")
        private String memo;
    }
} 