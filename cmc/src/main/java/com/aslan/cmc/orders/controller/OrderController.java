package com.aslan.cmc.orders.controller;

import com.aslan.cmc.orders.data.dto.OrderRequestDto;
import com.aslan.cmc.orders.data.dto.OrderResponseDto;
import com.aslan.cmc.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "주문&결제", description = "주문 및 결제 관련 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Parameter(description = "주문 요청 정보") @Valid @RequestBody OrderRequestDto orderRequest) {
        
        OrderResponseDto response = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "주문 조회")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(
            @Parameter(description = "주문 ID") @PathVariable String orderId) {
        
        OrderResponseDto response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "주문 취소")
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @Parameter(description = "주문 ID") @PathVariable String orderId) {
        
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
} 