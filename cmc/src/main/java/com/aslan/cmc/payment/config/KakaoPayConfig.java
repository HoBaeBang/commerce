package com.aslan.cmc.payment.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kakao.pay")
public class KakaoPayConfig {
    
    // 가맹점 코드 (테스트용: TC0ONETIME, 실제: 실제 가맹점 코드)
    private String cid = "TC0ONETIME";
    
    // 어드민 키 (admin-key로 매핑)
    private String adminKey;
    
    // 결제 성공 시 리다이렉트 URL (approval-url로 매핑)
    private String approvalUrl;
    
    // 결제 취소 시 리다이렉트 URL (cancel-url로 매핑)
    private String cancelUrl;
    
    // 결제 실패 시 리다이렉트 URL (fail-url로 매핑)
    private String failUrl;
    
    // 카카오페이 API URL들
    private String readyUrl = "https://open-api.kakaopay.com/online/v1/payment/ready";           // 결제 준비 API URL
    private String approveUrl = "https://open-api.kakaopay.com/online/v1/payment/approve";       // 결제 승인 API URL
    private String cancelApiUrl = "https://open-api.kakaopay.com/online/v1/payment/cancel";      // 결제 취소 API URL
} 