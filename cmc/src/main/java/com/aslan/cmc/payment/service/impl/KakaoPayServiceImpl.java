package com.aslan.cmc.payment.service.impl;

import com.aslan.cmc.orders.repository.OrderRepository;
import com.aslan.cmc.orders.repository.PaymentRepository;
import com.aslan.cmc.orders.repository.entity.Order;
import com.aslan.cmc.orders.repository.entity.OrderStatus;
import com.aslan.cmc.orders.repository.entity.Payment;
import com.aslan.cmc.orders.repository.entity.PaymentMethod;
import com.aslan.cmc.orders.repository.entity.PaymentStatus;
import com.aslan.cmc.payment.config.KakaoPayConfig;
import com.aslan.cmc.payment.data.dto.KakaoPayApproveResponseDto;
import com.aslan.cmc.payment.data.dto.KakaoPayReadyResponseDto;
import com.aslan.cmc.payment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap; // JSON 요청을 위해 HashMap 사용
import java.util.Map; // JSON 요청을 위해 Map 사용
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayServiceImpl implements KakaoPayService {

    private final KakaoPayConfig kakaoPayConfig;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public KakaoPayReadyResponseDto readyPayment(String orderId, String itemName, int totalAmount) {
        log.info("카카오페이 결제 준비 시작 - 주문ID: {}, 상품명: {}, 금액: {}", orderId, itemName, totalAmount);

        try {
            // API 정의서에 따라 JSON 요청을 위한 Map 사용
            Map<String, Object> parameters = new HashMap<>(); // Object 타입으로 변경하여 String 외의 값도 담을 수 있도록 함
            parameters.put("cid", kakaoPayConfig.getCid());
            parameters.put("partner_order_id", orderId);
            parameters.put("partner_user_id", "test_user"); // 실제 사용자 ID로 변경 필요
            parameters.put("item_name", itemName);
            parameters.put("quantity", 1); // int 타입으로 변경
            parameters.put("total_amount", totalAmount); // int 타입으로 변경
            parameters.put("vat_amount", (int)(totalAmount * 0.1)); // 부가세 10%
            parameters.put("tax_free_amount", 0);
            parameters.put("approval_url", kakaoPayConfig.getApprovalUrl());
            parameters.put("cancel_url", kakaoPayConfig.getCancelUrl());
            parameters.put("fail_url", kakaoPayConfig.getFailUrl());

            log.info("approval_url: {}", kakaoPayConfig.getApprovalUrl());
            log.info("cancel_url: {}", kakaoPayConfig.getCancelUrl());
            log.info("fail_url: {}", kakaoPayConfig.getFailUrl());


            // 파라미터, 헤더 (API 정의서에 따라 JSON 형식으로 헤더 설정)
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, getHeadersForSecretKey());
            log.info("requestEntity: {}", requestEntity);
            log.info("requestEntity.getBody(): {}", requestEntity.getBody());
            log.info("requestEntity.getHeaders(): {}", requestEntity.getHeaders());

            log.info("카카오페이 API 요청 URL: {}", kakaoPayConfig.getReadyUrl());
            log.info("카카오페이 API 요청 파라미터: {}", parameters);
            log.info("카카오페이 API SECRET_KEY: {}", kakaoPayConfig.getAdminKey()); // AdminKey 대신 SecretKey 로깅

            // 외부에 보낼 url
            ResponseEntity<KakaoPayReadyResponseDto> response = restTemplate.postForEntity(
                    kakaoPayConfig.getReadyUrl(), // URL이 open-api.kakaopay.com/online/v1/payment/ready 인지 확인
                    requestEntity,
                    KakaoPayReadyResponseDto.class
            );
            log.info("response: {}", response.getBody());

            log.info("카카오페이 API 응답 상태: {}", response.getStatusCode());
            log.info("카카오페이 API 응답: {}", response.getBody());

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("카카오페이 결제 준비 요청 실패: " + response.getStatusCode());
            }

            KakaoPayReadyResponseDto responseBody = response.getBody();

            // Payment 엔티티 조회 또는 생성
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다: " + orderId));

            // 기존 Payment가 있는지 확인
            Payment payment = paymentRepository.findByOrderId(orderId)
                    .orElse(null);

            if (payment == null) {
                // 새로운 Payment 생성
                payment = Payment.builder()
                        .id(UUID.randomUUID().toString())
                        .order(order)
                        .status(PaymentStatus.PENDING)
                        .paymentMethod(PaymentMethod.KAKAO_PAY)
                        .amount(totalAmount)
                        .kakaoTid(responseBody.getTid())
                        .build();

                paymentRepository.save(payment);
                log.info("Payment 엔티티 생성 완료 - PaymentID: {}, TID: {}", payment.getId(), responseBody.getTid());
            } else {
                // 기존 Payment 업데이트
                payment.setKakaoTid(responseBody.getTid());
                payment.setAmount(totalAmount);
                payment.setStatus(PaymentStatus.PENDING);
                paymentRepository.save(payment);
                log.info("기존 Payment 엔티티 업데이트 완료 - PaymentID: {}, TID: {}", payment.getId(), responseBody.getTid());
            }

            return responseBody;
        } catch (Exception e) {
            log.error("카카오페이 결제 준비 실패: {}", e.getMessage());
            throw new RuntimeException("결제 준비에 실패했습니다: " + e.getMessage());
        }
    }

    @Override
    public KakaoPayApproveResponseDto approvePayment(String orderId, String pgToken) {
        log.info("카카오페이 결제 승인 시작 - 주문ID: {}, PG Token: {}", orderId, pgToken);

        try {
            // DB에서 주문 및 결제 정보 조회
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다: " + orderId));

            Payment payment = paymentRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("결제 정보를 찾을 수 없습니다: " + orderId));

            String tid = payment.getKakaoTid();
            String partnerUserId = "test_user"; // 실제 사용자 ID로 변경 필요

            log.info("결제 승인 처리 - OrderID: {}, PaymentID: {}, TID: {}", orderId, payment.getId(), tid);

            // API 정의서에 따라 JSON 요청을 위한 Map 사용
            Map<String, String> parameters = new HashMap<>();
            parameters.put("cid", kakaoPayConfig.getCid());
            parameters.put("tid", tid);
            parameters.put("partner_order_id", orderId);
            parameters.put("partner_user_id", partnerUserId);
            parameters.put("pg_token", pgToken);

            // 파라미터, 헤더 (API 정의서에 따라 JSON 형식으로 헤더 설정)
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, getHeadersForSecretKey());

            log.info("카카오페이 결제 승인 요청 URL: {}", kakaoPayConfig.getApproveUrl());
            log.info("카카오페이 결제 승인 요청 파라미터: {}", parameters);
            log.info("카카오페이 API SECRET_KEY: {}", kakaoPayConfig.getAdminKey()); // AdminKey 대신 SecretKey 로깅

            // 외부에 보낼 url
            ResponseEntity<KakaoPayApproveResponseDto> response = restTemplate.postForEntity(
                    kakaoPayConfig.getApproveUrl(), // URL이 open-api.kakaopay.com/online/v1/payment/approve 인지 확인
                    requestEntity,
                    KakaoPayApproveResponseDto.class
            );

            log.info("카카오페이 결제 승인 응답 상태: {}", response.getStatusCode());
            log.info("카카오페이 결제 승인 응답: {}", response.getBody());

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("카카오페이 결제 승인 요청 실패: " + response.getStatusCode());
            }

            KakaoPayApproveResponseDto responseBody = response.getBody();

            // 결제 및 주문 상태 업데이트
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            payment.setPgToken(pgToken);
            paymentRepository.save(payment);

            order.setOrderStatus(OrderStatus.PAID);
            orderRepository.save(order);

            log.info("결제 승인 완료 - OrderID: {}, PaymentStatus: {}, OrderStatus: {}",
                    orderId, PaymentStatus.SUCCESS, OrderStatus.PAID);

            return responseBody;
        } catch (Exception e) {
            log.error("카카오페이 결제 승인 실패: {}", e.getMessage());
            throw new RuntimeException("결제 승인에 실패했습니다: " + e.getMessage());
        }
    }

    @Override
    public void cancelPayment(String orderId, int cancelAmount) {
        log.info("카카오페이 결제 취소 시작 - 주문ID: {}, 취소금액: {}", orderId, cancelAmount);

        try {
            // DB에서 주문 및 결제 정보 조회
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다: " + orderId));

            Payment payment = paymentRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("결제 정보를 찾을 수 없습니다: " + orderId));

            String tid = payment.getKakaoTid();

            log.info("결제 취소 처리 - OrderID: {}, PaymentID: {}, TID: {}", orderId, payment.getId(), tid);

            // API 정의서에 따라 JSON 요청을 위한 Map 사용
            Map<String, Object> parameters = new HashMap<>(); // Object 타입으로 변경
            parameters.put("cid", kakaoPayConfig.getCid());
            parameters.put("tid", tid);
            parameters.put("cancel_amount", cancelAmount); // int 타입으로 변경
            parameters.put("cancel_tax_free_amount", 0); // int 타입으로 변경
            parameters.put("cancel_vat_amount", (int)(cancelAmount * 0.1)); // int 타입으로 변경

            // 파라미터, 헤더 (API 정의서에 따라 JSON 형식으로 헤더 설정)
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, getHeadersForSecretKey());

            log.info("카카오페이 결제 취소 요청 URL: {}", kakaoPayConfig.getCancelApiUrl());
            log.info("카카오페이 결제 취소 요청 파라미터: {}", parameters);
            log.info("카카오페이 API SECRET_KEY: {}", kakaoPayConfig.getAdminKey()); // AdminKey 대신 SecretKey 로깅

            // 외부에 보낼 url
            ResponseEntity<String> response = restTemplate.postForEntity(
                    kakaoPayConfig.getCancelApiUrl(), // URL이 open-api.kakaopay.com/online/v1/payment/cancel 인지 확인
                    requestEntity,
                    String.class // 취소 응답 DTO가 있다면 해당 DTO로 변경
            );

            log.info("카카오페이 결제 취소 응답 상태: {}", response.getStatusCode());
            log.info("카카오페이 결제 취소 응답: {}", response.getBody());

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("카카오페이 결제 취소 요청 실패: " + response.getStatusCode());
            }

            // 결제 및 주문 상태 업데이트
            payment.setStatus(PaymentStatus.CANCELED);
            paymentRepository.save(payment);

            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            log.info("결제 취소 완료 - OrderID: {}, PaymentStatus: {}, OrderStatus: {}",
                    orderId, PaymentStatus.CANCELED, OrderStatus.CANCELLED);
        } catch (Exception e) {
            log.error("카카오페이 결제 취소 실패: {}", e.getMessage());
            throw new RuntimeException("결제 취소에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * API 정의서에 따라 Authorization: SECRET_KEY와 Content-Type: application/json 헤더를 생성합니다.
     * @return HTTP 헤더 객체
     */
    private HttpHeaders getHeadersForSecretKey() {
        HttpHeaders headers = new HttpHeaders();
        // Authorization 헤더를 "SECRET_KEY {SECRET_KEY}" 형식으로 설정합니다.
        // kakaoPayConfig에 getSecretKey() 메소드가 있다고 가정합니다.
        headers.add("Authorization", "SECRET_KEY " + kakaoPayConfig.getAdminKey());
        // Content-Type 헤더를 "application/json;charset=utf-8"으로 설정합니다.
        headers.add("Content-type", MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8");

        return headers;
    }

    // 기존 getHeaders() 메소드는 API 정의서에 맞지 않으므로 사용하지 않거나 삭제합니다.
    /*
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = "KakaoAK " + kakaoPayConfig.getAdminKey();
        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return httpHeaders;
    }
    */
}
