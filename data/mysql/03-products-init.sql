-- 데이터베이스 선택
USE commerce;

SET NAMES utf8mb4;

-- 기존 상품 데이터 삽입 (실제 상품)
INSERT INTO products (id, name, price, stock_quantity, created_at, updated_at) VALUES
(768848, '[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종', 21000, 45, NOW(), NOW()),
(748943, '디오디너리 데일리 세트 (Daily set)', 19000, 89, NOW(), NOW()),
(779989, '버드와이저 HOME DJing 굿즈 세트', 35000, 43, NOW(), NOW()),
(779943, 'Fabrik Pottery Flat Cup & Saucer - Mint', 24900, 89, NOW(), NOW()),
(768110, '네페라 손 세정제 대용량 500ml 이더블유지', 7000, 79, NOW(), NOW()),
(517643, '에어팟프로 AirPods PRO 블루투스 이어폰(MWP22KH/A)', 260800, 26, NOW(), NOW()),
(706803, 'ZEROVITY™ Flip Flop Cream 2.0 (Z-FF-CRAJ-)', 38000, 81, NOW(), NOW()),
(759928, '마스크 스트랩 분실방지 오염방지 목걸이', 2800, 85, NOW(), NOW()),
(213341, '20SS 오픈 카라/투 버튼 피케 티셔츠 (6color)', 33250, 99, NOW(), NOW()),
(377169, '[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)', 24900, 60, NOW(), NOW()),
(744775, 'SHUT UP [TK00112]', 28000, 35, NOW(), NOW()),
(779049, '[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)', 10000, 64, NOW(), NOW()),
(611019, '플루크 new 피그먼트 오버핏 반팔티셔츠 FST701 / 7color', 19800, 7, NOW(), NOW()),
(628066, '무설탕 프로틴 초콜릿 틴볼스', 12900, 8, NOW(), NOW()),
(502480, '[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)', 24900, 41, NOW(), NOW()),
(782858, '폴로 랄프로렌 남성 수영복반바지 컬렉션 (51color)', 39500, 50, NOW(), NOW()),
(760709, '파버카스텔 연필1자루', 200, 70, NOW(), NOW()),
(778422, '캠핑덕 우드롤테이블', 45000, 7, NOW(), NOW()),
(648418, 'BS 02-2A DAYPACK 26 (BLACK)', 23800, 5, NOW(), NOW());

-- 테스트용 전자제품 데이터 삽입 (API 테스트용)
INSERT INTO products (name, price, image_url, stock_quantity, created_at, updated_at) VALUES
('iPhone 15 Pro Max', 1800000, 'https://example.com/iphone15-pro-max.jpg', 50, NOW(), NOW()),
('iPhone 15 Pro', 1500000, 'https://example.com/iphone15-pro.jpg', 100, NOW(), NOW()),
('iPhone 15', 1300000, 'https://example.com/iphone15.jpg', 150, NOW(), NOW()),
('iPhone 15 Plus', 1400000, 'https://example.com/iphone15-plus.jpg', 80, NOW(), NOW()),
('AirPods Pro 2세대', 350000, 'https://example.com/airpods-pro-2.jpg', 75, NOW(), NOW()),
('AirPods 3세대', 250000, 'https://example.com/airpods-3.jpg', 120, NOW(), NOW()),
('AirPods 2세대', 200000, 'https://example.com/airpods-2.jpg', 90, NOW(), NOW()),
('MacBook Pro 16" M3 Pro', 4000000, 'https://example.com/macbook-pro-16-m3.jpg', 25, NOW(), NOW()),
('MacBook Pro 14" M3', 3000000, 'https://example.com/macbook-pro-14-m3.jpg', 40, NOW(), NOW()),
('MacBook Air 15" M2', 2000000, 'https://example.com/macbook-air-15-m2.jpg', 60, NOW(), NOW()),
('MacBook Air 13" M2', 1500000, 'https://example.com/macbook-air-13-m2.jpg', 80, NOW(), NOW()),
('iPad Pro 12.9" M2', 1500000, 'https://example.com/ipad-pro-12-m2.jpg', 35, NOW(), NOW()),
('iPad Pro 11" M2', 1200000, 'https://example.com/ipad-pro-11-m2.jpg', 45, NOW(), NOW()),
('iPad Air 5세대', 800000, 'https://example.com/ipad-air-5.jpg', 70, NOW(), NOW()),
('iPad 10세대', 600000, 'https://example.com/ipad-10.jpg', 100, NOW(), NOW()),
('Apple Watch Series 9', 500000, 'https://example.com/apple-watch-9.jpg', 60, NOW(), NOW()),
('Apple Watch SE 2세대', 350000, 'https://example.com/apple-watch-se-2.jpg', 80, NOW(), NOW()),
('Apple Watch Ultra 2', 1200000, 'https://example.com/apple-watch-ultra-2.jpg', 20, NOW(), NOW()),
('Magic Keyboard', 150000, 'https://example.com/magic-keyboard.jpg', 90, NOW(), NOW()),
('Magic Mouse', 80000, 'https://example.com/magic-mouse.jpg', 110, NOW(), NOW()),
('Studio Display', 2000000, 'https://example.com/studio-display.jpg', 15, NOW(), NOW()),
('Pro Display XDR', 8000000, 'https://example.com/pro-display-xdr.jpg', 5, NOW(), NOW()),
('HomePod 2세대', 400000, 'https://example.com/homepod-2.jpg', 30, NOW(), NOW()),
('HomePod mini', 120000, 'https://example.com/homepod-mini.jpg', 85, NOW(), NOW()),
('Apple TV 4K 3세대', 250000, 'https://example.com/apple-tv-4k-3.jpg', 40, NOW(), NOW()),
('Apple TV 4K 2세대', 200000, 'https://example.com/apple-tv-4k-2.jpg', 55, NOW(), NOW());
