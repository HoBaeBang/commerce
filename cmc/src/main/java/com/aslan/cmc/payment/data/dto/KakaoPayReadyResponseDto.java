package com.aslan.cmc.payment.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayReadyResponseDto {
    private String tid;                    // 결제 고유 번호
    private String next_redirect_pc_url;   // PC 웹 결제 페이지 URL
    private String created_at;             // 결제 준비 요청 시간
} 