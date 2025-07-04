package com.aslan.cmc.payment.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayApproveResponseDto {
    private String aid;                    // 요청 고유 번호
    private String tid;                    // 결제 고유 번호
    private String cid;                    // 가맹점 코드
    private String partner_order_id;       // 주문번호
    private String partner_user_id;        // 회원 ID
    private Amount amount;                 // 결제 금액 정보
    private String payment_method_type;    // 결제 수단
    private String item_name;              // 상품명
    private String item_code;              // 상품 코드
    private int quantity;                  // 상품 수량
    private String created_at;             // 결제 준비 요청 시간
    private String approved_at;            // 결제 승인 시간
    private String payload;                // 결제 승인 요청에 대해 저장한 값

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Amount {
        private int total;                 // 전체 결제 금액
        private int tax_free;              // 비과세 금액
        private int vat;                   // 부가세 금액
        private int point;                 // 사용한 포인트 금액
        private int discount;              // 할인 금액
    }
} 