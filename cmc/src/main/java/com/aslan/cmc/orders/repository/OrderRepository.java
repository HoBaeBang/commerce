package com.aslan.cmc.orders.repository;

import com.aslan.cmc.orders.repository.entity.Order;
import com.aslan.cmc.orders.repository.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    /**
     * 주문 상태로 주문 목록을 조회합니다.
     */
    List<Order> findByOrderStatus(OrderStatus orderStatus);

    /**
     * 결제 마감 시간이 지난 PENDING 상태의 주문들을 조회합니다.
     */
    @Query("SELECT o FROM Order o WHERE o.orderStatus = :status AND o.paymentDueDate < :now")
    List<Order> findExpiredOrders(@Param("status") OrderStatus status, @Param("now") LocalDateTime now);

    /**
     * 주문 ID로 주문과 주문 상품들을 함께 조회합니다.
     */
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = :orderId")
    Optional<Order> findByIdWithOrderItems(@Param("orderId") String orderId);
} 