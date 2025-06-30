package com.aslan.cmc.orders.repository;

import com.aslan.cmc.orders.repository.entity.Payment;
import com.aslan.cmc.orders.repository.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    /**
     * 주문 ID로 결제 정보를 조회합니다.
     */
    Optional<Payment> findByOrderId(String orderId);

    /**
     * 결제 상태로 결제 목록을 조회합니다.
     */
    List<Payment> findByStatus(PaymentStatus status);

    /**
     * PG 토큰으로 결제 정보를 조회합니다.
     */
    Optional<Payment> findByPgToken(String pgToken);
} 