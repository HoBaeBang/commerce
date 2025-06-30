# ERD (Entity Relationship Diagram)

## 데이터베이스 구조

```mermaid
erDiagram
    PRODUCTS ||--o{ ORDER_ITEMS : "contains"
    ORDERS ||--o{ ORDER_ITEMS : "includes"
    ORDERS ||--o| PAYMENTS : "has"

    PRODUCTS {
        bigint id PK "상품 고유 ID (AUTO_INCREMENT)"
        varchar(255) name "상품명"
        int price "상품 가격"
        varchar(512) image_url "이미지 URL"
        int stock_quantity "재고 수량"
        timestamp created_at "생성일시"
        timestamp updated_at "수정일시"
    }

    ORDERS {
        varchar(36) id PK "주문 고유 ID (UUID)"
        timestamp order_date "주문 일시"
        varchar(20) order_status "주문 상태"
        int total_product_amount "상품 금액 합계"
        int shipping_amount "배송비"
        int payment_amount "최종 결제 금액"
        timestamp payment_due_date "결제 마감 시간"
        varchar(512) kakaopay_ready_url "카카오페이 결제 URL"
        varchar(10) shipping_zipcode "우편번호"
        varchar(255) shipping_address "기본 주소"
        varchar(255) shipping_address_detail "상세 주소"
        varchar(50) receiver_name "수령인 이름"
        varchar(20) receiver_phone "수령인 전화번호"
        text memo "배송 메모"
        timestamp created_at "생성일시"
        timestamp updated_at "수정일시"
    }

    ORDER_ITEMS {
        bigint id PK "주문 상품 ID (AUTO_INCREMENT)"
        varchar(36) order_id FK "주문 ID"
        bigint product_id FK "상품 ID"
        int price "주문 당시 가격"
        int quantity "주문 수량"
        timestamp created_at "생성일시"
        timestamp updated_at "수정일시"
    }

    PAYMENTS {
        varchar(36) id PK "결제 고유 ID (UUID)"
        varchar(36) order_id FK "주문 ID (UNIQUE)"
        varchar(20) status "결제 상태"
        varchar(20) payment_method "결제 수단"
        int amount "결제 금액"
        timestamp paid_at "결제 완료 시간"
        varchar(255) pg_token "게이트웨이 토큰"
        varchar(255) kakao_tid "카카오페이 거래 ID"
        timestamp created_at "생성일시"
        timestamp updated_at "수정일시"
    }
```

## 인덱스 정보

### PRODUCTS 테이블
- `idx_product_name`: 상품명 검색을 위한 인덱스
- `idx_product_price`: 가격 범위 검색을 위한 인덱스

### ORDERS 테이블
- `idx_order_status`: 주문 상태별 조회를 위한 인덱스
- `idx_order_date`: 주문 일시별 조회를 위한 인덱스
- `idx_payment_due_date`: 결제 마감 시간별 조회를 위한 인덱스

### ORDER_ITEMS 테이블
- `idx_order_id`: 주문별 상품 조회를 위한 인덱스
- `idx_product_id`: 상품별 주문 조회를 위한 인덱스

### PAYMENTS 테이블
- `idx_payment_status`: 결제 상태별 조회를 위한 인덱스
- `unq_order_id`: 주문당 하나의 결제만 허용하는 유니크 인덱스

## 관계 설명

1. **PRODUCTS ↔ ORDER_ITEMS**: 일대다 관계
   - 하나의 상품은 여러 주문에 포함될 수 있음
   - 주문 상품 삭제 시 상품은 삭제되지 않음 (RESTRICT)

2. **ORDERS ↔ ORDER_ITEMS**: 일대다 관계
   - 하나의 주문은 여러 상품을 포함할 수 있음
   - 주문 삭제 시 관련 주문 상품도 함께 삭제됨 (CASCADE)

3. **ORDERS ↔ PAYMENTS**: 일대일 관계
   - 하나의 주문은 하나의 결제만 가질 수 있음 (UNIQUE 제약)
   - 주문 삭제 시 결제는 삭제되지 않음 (RESTRICT)

## 주문 상태 설명

- **PENDING**: 주문 생성, 결제 대기 상태
- **PAID**: 결제 완료 상태
- **CANCELLED**: 주문 취소 상태
- **PAYMENT_FAILED**: 결제 실패 상태
- **PAYMENT_ERROR**: 결제 검증 실패 상태
- **EXPIRED**: 주문 만료 상태 (30분 내 미결제)

## 결제 상태 설명

- **SUCCESS**: 결제 성공
- **FAILED**: 결제 실패
- **PENDING**: 결제 대기
- **CANCELED**: 결제 취소
