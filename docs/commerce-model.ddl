-- 쇼핑몰 API 데이터베이스 DDL

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS commerce_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE commerce_db;

-- 상품 테이블
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT '상품명',
    price INT NOT NULL COMMENT '상품 가격',
    image_url VARCHAR(512) COMMENT '이미지 URL',
    stock_quantity INT NOT NULL DEFAULT 0 COMMENT '재고 수량',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    -- 상품명과 가격 범위를 밭탕르 조회를 많이할 것 같아서 인덱스 설정을 해봤습니다.
    INDEX idx_product_name (name),
    INDEX idx_product_price (price)
    ) COMMENT '상품 정보';

-- 주문 테이블
CREATE TABLE IF NOT EXISTS orders (
    id VARCHAR(36) PRIMARY KEY COMMENT '주문 ID (UUID)',
    order_date TIMESTAMP NOT NULL COMMENT '주문 일시',
    order_status VARCHAR(20) NOT NULL COMMENT '주문 상태 (PENDING, PAID, CANCELLED, PAYMENT_FAILED, PAYMENT_ERROR, EXPIRED)',
    total_product_amount INT NOT NULL COMMENT '상품 금액 합계',
    shipping_amount INT NOT NULL COMMENT '배송비',
    payment_amount INT NOT NULL COMMENT '최종 결제 금액',
    payment_due_date TIMESTAMP NOT NULL COMMENT '결제 마감 시간',
    kakaopay_ready_url VARCHAR(512) COMMENT '카카오페이 결제 준비 URL',

    -- 배송지 정보
    shipping_zipcode VARCHAR(10) NOT NULL COMMENT '배송지 우편번호',
    shipping_address VARCHAR(255) NOT NULL COMMENT '배송지 기본 주소',
    shipping_address_detail VARCHAR(255) COMMENT '배송지 상세 주소',

    -- 수령인 정보
    receiver_name VARCHAR(50) NOT NULL COMMENT '수령인 이름',
    receiver_phone VARCHAR(20) NOT NULL COMMENT '수령인 전화번호',

    memo TEXT COMMENT '배송 메모',
    -- 기본값 설정
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    -- 검색을한다면 주문 상태, 주문 일시, 결제 마감기한에 대해서 검색할것 같아 인덱스를 설정하였습니다.
    INDEX idx_order_status (order_status),
    INDEX idx_order_date (order_date)
    ) COMMENT '주문 정보';

-- 주문 상품 테이블
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL COMMENT '주문 ID',
    product_id BIGINT NOT NULL COMMENT '상품 ID',
    price INT NOT NULL COMMENT '주문 당시 상품 가격',
    quantity INT NOT NULL COMMENT '주문 수량',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,

    --외래키에 대한 검색이 자주 일어날것 같아 추가해보았습니다.
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
    ) COMMENT '주문 상품 정보';

-- 결제 테이블
CREATE TABLE IF NOT EXISTS payments (
    id VARCHAR(36) PRIMARY KEY COMMENT '결제 ID (UUID)',
    order_id VARCHAR(36) NOT NULL COMMENT '주문 ID',
    status VARCHAR(20) NOT NULL COMMENT '결제 상태 (SUCCESS, FAILED, PENDING, CANCELED)',
    payment_method VARCHAR(20) NOT NULL DEFAULT 'KAKAO_PAY' COMMENT '결제 수단',
    amount INT NOT NULL COMMENT '결제 금액',
    paid_at TIMESTAMP NULL COMMENT '결제 완료 시간',
    pg_token VARCHAR(255) COMMENT '결제 게이트웨이 토큰',
    kakao_tid VARCHAR(255) COMMENT '카카오페이 거래 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE RESTRICT,

    INDEX idx_payment_status (status),
    INDEX idx_payment_order_id (order_id),
    UNIQUE INDEX unq_order_id (order_id)    --혹시나 중복이 발생할까봐 유니크 설정을 추가해 두었습니다.
    ) COMMENT '결제 정보';
