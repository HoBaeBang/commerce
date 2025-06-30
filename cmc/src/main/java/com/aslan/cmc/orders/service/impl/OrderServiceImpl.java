package com.aslan.cmc.orders.service.impl;

import com.aslan.cmc.orders.data.dto.OrderRequestDto;
import com.aslan.cmc.orders.data.dto.OrderResponseDto;
import com.aslan.cmc.orders.exception.OrderNotFoundException;
import com.aslan.cmc.orders.repository.OrderRepository;
import com.aslan.cmc.orders.repository.entity.Order;
import com.aslan.cmc.orders.repository.entity.OrderItem;
import com.aslan.cmc.orders.repository.entity.OrderStatus;
import com.aslan.cmc.orders.service.OrderService;
import com.aslan.cmc.payment.data.dto.KakaoPayReadyResponseDto;
import com.aslan.cmc.payment.service.KakaoPayService;
import com.aslan.cmc.products.repository.ProductRepository;
import com.aslan.cmc.products.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final KakaoPayService kakaoPayService;
    private static final int SHIPPING_FEE = 3000; // 배송비 고정 3000원

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        // 상품 정보 조회 및 재고 확인
        List<Product> products = validateAndGetProducts(orderRequest.getOrderItems());
        
        // 주문 생성
        Order order = createOrderEntity(orderRequest, products);
        
        // 주문 저장
        Order savedOrder = orderRepository.save(order);
        
        // 결제 준비 요청
        try {
            String itemName = generateItemName(orderRequest.getOrderItems(), products);
            KakaoPayReadyResponseDto paymentReady = kakaoPayService.readyPayment(
                    savedOrder.getId(), 
                    itemName, 
                    savedOrder.getPaymentAmount()
            );
            
            // 결제 준비 URL 저장
            savedOrder.setKakaopayReadyUrl(paymentReady.getNext_redirect_pc_url());
            orderRepository.save(savedOrder);
            
            log.info("결제 준비 완료 - 주문ID: {}, 결제URL: {}", savedOrder.getId(), paymentReady.getNext_redirect_pc_url());
            
        } catch (Exception e) {
            log.error("결제 준비 실패 - 주문ID: {}", savedOrder.getId(), e);
            // 결제 준비 실패 시 주문 상태를 PAYMENT_FAILED로 변경
            savedOrder.setOrderStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.save(savedOrder);
            throw new RuntimeException("결제 준비에 실패했습니다: " + e.getMessage());
        }
        
        log.info("주문이 생성되었습니다. 주문 ID: {}", savedOrder.getId());
        
        return convertToOrderResponseDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(String orderId) {
        Order order = orderRepository.findByIdWithOrderItems(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        return convertToOrderResponseDto(order);
    }

    @Override
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("취소할 수 없는 주문 상태입니다: " + order.getOrderStatus());
        }
        
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        
        // 재고 복구
        restoreStock(order);
        
        log.info("주문이 취소되었습니다. 주문 ID: {}", orderId);
    }

    @Override
    public void processExpiredOrders() {
        List<Order> expiredOrders = orderRepository.findExpiredOrders(OrderStatus.PENDING, LocalDateTime.now());
        
        for (Order order : expiredOrders) {
            order.setOrderStatus(OrderStatus.EXPIRED);
            orderRepository.save(order);
            
            // 재고 복구
            restoreStock(order);
            
            log.info("만료된 주문이 처리되었습니다. 주문 ID: {}", order.getId());
        }
    }

    /**
     * 결제 승인 처리
     */
    public void approvePayment(String orderId, String pgToken) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("결제 승인할 수 없는 주문 상태입니다: " + order.getOrderStatus());
        }
        
        try {
            // 카카오페이 결제 승인
            kakaoPayService.approvePayment(orderId, pgToken);
            
            // 주문 상태를 PAID로 변경
            order.setOrderStatus(OrderStatus.PAID);
            orderRepository.save(order);
            
            log.info("결제가 승인되었습니다. 주문 ID: {}", orderId);
            
        } catch (Exception e) {
            log.error("결제 승인 실패 - 주문ID: {}", orderId, e);
            order.setOrderStatus(OrderStatus.PAYMENT_ERROR);
            orderRepository.save(order);
            throw new RuntimeException("결제 승인에 실패했습니다: " + e.getMessage());
        }
    }

    private List<Product> validateAndGetProducts(List<OrderRequestDto.OrderItemRequestDto> orderItems) {
        return orderItems.stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + item.getProductId()));
                    
                    if (product.getStockQuantity() < item.getQuantity()) {
                        throw new IllegalArgumentException("재고가 부족합니다. 상품: " + product.getName());
                    }
                    
                    // 임시 재고 차감
                    product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                    
                    return product;
                })
                .collect(Collectors.toList());
    }

    private Order createOrderEntity(OrderRequestDto orderRequest, List<Product> products) {
        // 주문 상품 생성
        List<OrderItem> orderItems = createOrderItems(orderRequest.getOrderItems(), products);
        
        // 금액 계산
        int totalProductAmount = orderItems.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
        int paymentAmount = totalProductAmount + SHIPPING_FEE;
        
        // 주문 생성
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .totalProductAmount(totalProductAmount)
                .shippingAmount(SHIPPING_FEE)
                .paymentAmount(paymentAmount)
                .paymentDueDate(LocalDateTime.now().plusMinutes(30)) // 30분 후 만료
                .shippingZipcode(orderRequest.getShippingZipcode())
                .shippingAddress(orderRequest.getShippingAddress())
                .shippingAddressDetail(orderRequest.getShippingAddressDetail())
                .receiverName(orderRequest.getReceiverName())
                .receiverPhone(orderRequest.getReceiverPhone())
                .memo(orderRequest.getMemo())
                .orderItems(orderItems)
                .build();
        
        // 주문 상품에 주문 참조 설정
        orderItems.forEach(item -> item.setOrder(order));
        
        return order;
    }

    private List<OrderItem> createOrderItems(List<OrderRequestDto.OrderItemRequestDto> orderItemRequests, List<Product> products) {
        return orderItemRequests.stream()
                .map(itemRequest -> {
                    Product product = products.stream()
                            .filter(p -> p.getId().equals(itemRequest.getProductId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + itemRequest.getProductId()));
                    
                    return OrderItem.builder()
                            .productId(product.getId())
                            .productName(product.getName())
                            .price(product.getPrice())
                            .quantity(itemRequest.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private void restoreStock(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + orderItem.getProductId()));
            
            product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
            productRepository.save(product);
        }
    }

    private String generateItemName(List<OrderRequestDto.OrderItemRequestDto> orderItems, List<Product> products) {
        if (orderItems.size() == 1) {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(orderItems.get(0).getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));
            return product.getName();
        } else {
            return orderItems.size() + "개 상품";
        }
    }

    private OrderResponseDto convertToOrderResponseDto(Order order) {
        List<OrderResponseDto.OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                .map(item -> OrderResponseDto.OrderItemResponseDto.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        OrderResponseDto.ShippingInfoDto shippingInfo = OrderResponseDto.ShippingInfoDto.builder()
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .shippingZipcode(order.getShippingZipcode())
                .shippingAddress(order.getShippingAddress())
                .shippingAddressDetail(order.getShippingAddressDetail())
                .memo(order.getMemo())
                .build();

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalProductAmount(order.getTotalProductAmount())
                .shippingAmount(order.getShippingAmount())
                .paymentAmount(order.getPaymentAmount())
                .paymentDueDate(order.getPaymentDueDate())
                .kakaopayReadyUrl(order.getKakaopayReadyUrl())
                .orderItems(orderItemDtos)
                .shippingInfo(shippingInfo)
                .orderDate(order.getOrderDate())
                .build();
    }
} 