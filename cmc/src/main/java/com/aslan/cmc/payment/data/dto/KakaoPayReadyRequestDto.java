package com.aslan.cmc.payment.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayReadyRequestDto {
    private String cid;                    // 가맹점 코드
    private String partner_order_id;       // 주문번호
    private String partner_user_id;        // 회원 ID
    private String item_name;              // 상품명
    private int quantity;                  // 상품 수량
    private int total_amount;              // 총 금액
    private int tax_free_amount;           // 비과세 금액
    private String approval_url;           // 결제 성공 시 리다이렉트 URL
    private String cancel_url;             // 결제 취소 시 리다이렉트 URL
    private String fail_url;               // 결제 실패 시 리다이렉트 URL
} 