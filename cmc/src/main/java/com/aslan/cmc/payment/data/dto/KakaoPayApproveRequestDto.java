package com.aslan.cmc.payment.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayApproveRequestDto {
    private String cid;                    // 가맹점 코드
    private String tid;                    // 결제 고유 번호
    private String partner_order_id;       // 주문번호
    private String partner_user_id;        // 회원 ID
    private String pg_token;               // 결제승인 요청을 인증하는 토큰
} 