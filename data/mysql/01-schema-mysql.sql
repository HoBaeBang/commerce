-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS commerce
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 선택
USE commerce;

-- 사용자 생성 및 권한 부여 (선택사항)
-- CREATE USER IF NOT EXISTS 'commerce_user'@'%' IDENTIFIED BY 'commerce1234';
-- GRANT ALL PRIVILEGES ON commerce.* TO 'commerce_user'@'%';
-- FLUSH PRIVILEGES;