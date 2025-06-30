# 제안된 데이터베이스 구조 변경안 (ERD)

회원 시스템, 사용자 상품 등록, 배송지 관리 기능을 추가함에 따라 데이터베이스 구조는 다음과 같이 변경될 것을 제안합니다.

## 변경 요약

1.  **`USERS` 테이블**: 닉네임(`nickname`) 컬럼 추가.
2.  **`ADDRESSES` 테이블 신규 추가**: 각 유저가 최대 5개의 배송지를 저장할 수 있도록 설계. 배송지 닉네임, 기본 배송지 여부 포함.
3.  **`PRODUCTS`/`ORDERS` 테이블**: 기존과 동일하게 유저와의 관계 유지.

## 제안 ERD

```mermaid
erDiagram
    USERS ||--o{ PRODUCTS : "registers"
    USERS ||--o{ ORDERS : "places"
    USERS ||--o{ ADDRESSES : "has"
    PRODUCTS ||--o{ ORDER_ITEMS : "contains"
    ORDERS ||--o{ ORDER_ITEMS : "includes"
    ORDERS ||--o| PAYMENTS : "has"

    USERS {
        bigint id PK "사용자 고유 ID (AUTO_INCREMENT)"
        varchar(255) email "이메일 (UNIQUE, NOT NULL)"
        varchar(255) name "이름 (NOT NULL)"
        varchar(50) nickname "닉네임 (NOT NULL, UNIQUE)"
        enum provider "소셜 로그인 제공자 (GOOGLE, KAKAO, NAVER)"
        varchar(255) provider_id "소셜 제공자별 고유 ID"
        enum role "사용자 권한 (USER, SELLER, ADMIN)"
        timestamp created_at "가입일시"
        timestamp updated_at "정보 수정일시"
    }

    ADDRESSES {
        bigint id PK "배송지 고유 ID (AUTO_INCREMENT)"
        bigint user_id FK "사용자 ID (USERS 참조)"
        varchar(30) address_nickname "배송지 닉네임"
        varchar(10) zipcode "우편번호"
        varchar(255) address "기본 주소"
        varchar(255) address_detail "상세 주소"
        boolean is_default "기본 배송지 여부"
        timestamp created_at "생성일시"
        timestamp updated_at "수정일시"
    }

    PRODUCTS {
        bigint id PK
        bigint user_id FK
        varchar(255) name
        int price
        varchar(512) image_url
        int stock_quantity
        timestamp created_at
        timestamp updated_at
    }

    ORDERS {
        varchar(36) id PK
        bigint user_id FK
        timestamp order_date
        varchar(20) order_status
        int total_product_amount
        int shipping_amount
        int payment_amount
        timestamp payment_due_date
        varchar(512) kakaopay_ready_url
        varchar(10) shipping_zipcode
        varchar(255) shipping_address
        varchar(255) shipping_address_detail
        varchar(50) receiver_name
        varchar(20) receiver_phone
        text memo
        timestamp created_at
        timestamp updated_at
    }

    ORDER_ITEMS {
        bigint id PK
        varchar(36) order_id FK
        bigint product_id FK
        int price
        int quantity
        timestamp created_at
        timestamp updated_at
    }

    PAYMENTS {
        varchar(36) id PK
        varchar(36) order_id FK
        varchar(20) status
        varchar(20) payment_method
        int amount
        timestamp paid_at
        varchar(255) pg_token
        varchar(255) kakao_tid
        timestamp created_at
        timestamp updated_at
    }
```

## 관계 설명
- USERS ↔ ADDRESSES (1:N): 한 유저는 최대 5개의 배송지를 가질 수 있음. 각 배송지는 닉네임을 가짐. 한 배송지만 기본 배송지로 설정 가능.
- USERS ↔ PRODUCTS/ORDERS: 기존과 동일. 