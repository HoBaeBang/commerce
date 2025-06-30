package com.aslan.cmc.payment.controller;

import com.aslan.cmc.payment.data.dto.KakaoPayApproveResponseDto;
import com.aslan.cmc.payment.data.dto.KakaoPayReadyResponseDto;
import com.aslan.cmc.payment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    /**
     * 결제 준비 요청
     */
    @PostMapping("/ready")
    public ResponseEntity<KakaoPayReadyResponseDto> readyPayment(
            @RequestParam String orderId,
            @RequestParam String itemName,
            @RequestParam int totalAmount) {
        
        log.info("결제 준비 요청 - 주문ID: {}, 상품명: {}, 금액: {}", orderId, itemName, totalAmount);
        
        KakaoPayReadyResponseDto response = kakaoPayService.readyPayment(orderId, itemName, totalAmount);
        return ResponseEntity.ok(response);
    }

    /**
     * 결제 승인 요청
     */
    @PostMapping("/approve")
    public ResponseEntity<KakaoPayApproveResponseDto> approvePayment(
            @RequestParam String orderId,
            @RequestParam String pgToken) {
        
        log.info("결제 승인 요청 - 주문ID: {}, PG토큰: {}", orderId, pgToken);
        
        KakaoPayApproveResponseDto response = kakaoPayService.approvePayment(orderId, pgToken);
        return ResponseEntity.ok(response);
    }

    /**
     * 결제 취소 요청
     */
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPayment(
            @RequestParam String orderId,
            @RequestParam int cancelAmount) {
        
        log.info("결제 취소 요청 - 주문ID: {}, 취소금액: {}", orderId, cancelAmount);
        
        kakaoPayService.cancelPayment(orderId, cancelAmount);
        return ResponseEntity.ok("결제가 취소되었습니다.");
    }

    /**
     * 결제 성공 페이지 (카카오페이에서 리다이렉트)
     */
    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess(
            @RequestParam("pg_token") String pgToken,
            @RequestParam("partner_order_id") String orderId) {
        
        log.info("결제 성공 페이지 접근 - 주문ID: {}, PG토큰: {}", orderId, pgToken);
        
        try {
            KakaoPayApproveResponseDto response = kakaoPayService.approvePayment(orderId, pgToken);
            log.info("결제 승인 완료: {}", response);
            return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            log.error("결제 승인 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("결제 승인에 실패했습니다.");
        }
    }

    /**
     * 결제 취소 페이지 (카카오페이에서 리다이렉트)
     */
    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        log.info("결제 취소 페이지 접근");
        return ResponseEntity.ok("결제가 취소되었습니다.");
    }

    /**
     * 결제 실패 페이지 (카카오페이에서 리다이렉트)
     */
    @GetMapping("/fail")
    public ResponseEntity<String> paymentFail() {
        log.info("결제 실패 페이지 접근");
        return ResponseEntity.ok("결제에 실패했습니다.");
    }
} 