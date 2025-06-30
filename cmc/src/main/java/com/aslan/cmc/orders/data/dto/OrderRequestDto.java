package com.aslan.cmc.orders.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 요청")
public class OrderRequestDto {

    @NotEmpty(message = "주문 상품 목록은 필수입니다.")
    @Schema(description = "주문 상품 목록")
    private List<OrderItemRequestDto> orderItems;

    @NotBlank(message = "수령인 이름은 필수입니다.")
    @Schema(description = "수령인 이름")
    private String receiverName;

    @NotBlank(message = "수령인 전화번호는 필수입니다.")
    @Schema(description = "수령인 전화번호")
    private String receiverPhone;

    @NotBlank(message = "배송지 우편번호는 필수입니다.")
    @Schema(description = "배송지 우편번호")
    private String shippingZipcode;

    @NotBlank(message = "배송지 기본 주소는 필수입니다.")
    @Schema(description = "배송지 기본 주소")
    private String shippingAddress;

    @NotBlank(message = "배송지 상세 주소는 필수입니다.")
    @Schema(description = "배송지 상세 주소")
    private String shippingAddressDetail;

    @Schema(description = "배송 메모")
    private String memo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "주문 상품 요청")
    public static class OrderItemRequestDto {
        @NotNull(message = "상품 ID는 필수입니다.")
        @Schema(description = "상품 ID")
        private Long productId;

        @NotNull(message = "주문 수량은 필수입니다.")
        @Min(value = 1, message = "주문 수량은 1 이상이어야 합니다.")
        @Schema(description = "주문 수량")
        private Integer quantity;
    }
} 