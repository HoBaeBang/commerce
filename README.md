# 이커머스 백엔드 프로젝트

이 프로젝트는 상품 조회/검색과 카카오페이를 활용한 결제 기능을 제공하는 이커머스 백엔드 시스템입니다.

## 프로젝트 개요

이 백엔드 애플리케이션은 이커머스 플랫폼의 핵심 기능을 제공합니다. 
현재는 Phase 1 기능(상품 조회/검색, 주문 및 결제)을 구현하고 있으며, 향후 Phase 2에서 추가 기능을 확장할 예정입니다.

## 주요 기능

### 상품 조회/검색 기능
- 상품명 검색 (like 검색 지원)
- 가격 범위 필터링 (이상/이하)
- 검색 결과 페이징 처리
    - default: 20개
    - max : 20개
    - 크기 설정 가능(작게)
- 다양한 정렬 옵션 지원
    - 가격 오름차순
    - 상품명 오름차순
    - 최신 등록순
- 상품별 재고 상태 확인

### 주문 및 결제 기능
- 카카오페이 결제 방식 연동
- 주문 프로세스 관리
    - 배송지 정보 입력 및 저장
    - 주문 금액 계산
    - 주문 상품 목록 관리
- 주문 상태 관리 시스템
    - PENDING: 주문 생성, 결제 대기
    - PAID: 결제 완료
    - CANCELLED: 주문 취소
    - PAYMENT_FAILED: 결제 실패
    - PAYMENT_ERROR: 결제 검증 실패
    - EXPIRED: 주문 만료 (30분 내 미결제)
- 주문 만료 자동화 (30분 제한)
- 임시 재고 차감 방식 구현
- 주문 상태 변경 이력 추적
- 결제 검증 프로세스
- 주문/결제 관련 알림 처리

## ⚙️ 기술 스택

- **언어**: Java 17
- **프레임워크**: Spring Boot 3.x
- **데이터베이스**: MySQL 9.*
- **빌드 도구**: Gradle
- **API 문서화**: Swagger/OpenAPI 3.0
- **결제 연동**: 카카오페이 API



## 🔄 시스템 흐름도

### 주문 상태 다이어그램
![주문 상태 다이어그램](https://www.plantuml.com/plantuml/png/VLDVQzDG57-_d-8ygZ0GV1jZh6ip2TGuWW-AYWHNPsXs8rdw94BdWamCBZvCivcEoE9MeGzngpgXdoZtv3juuysrz587VRcdlp-_yoShsxxb-RqjXsntRVh2ygmju2M_0oy9mAhrjBlfkJswqNGTrrjQqgdjsvQpl0njU_Evl-hqElDWQpRz2rgtQPUy8kJXZSUmiC3Vcj7IZTkm27WoOQDBmDq11jy9TRcXLoc2--n_A2W_1r2SvvWcmC9-SPdpFtRAtUGHlNMJ_Go0xH_YmHvWafP7Gq9g6M7Sq5kmADbShBZu0IpVvvK-dWwK03Fe8k_N14ntC9cmBrpWC60d8OkIEi6iMRDXDBLsMwiqyFK8ZxxXSGJuQuXlGiLfBhHob4dFWlBZMy3iW-GhRJsuendtdgmrTA4XQU-oCXogNQbW_3skVd5MleznCFv7Fe_PwKXunXdx6lD19Nm5ywqrqxnhJft5FAyCe5B4CCLs1Rkuc61wW9yY39AhCcZtrtLJQqqFXPs5bTGrSGyutkDArvKKWZGpBBxZ6YssUnpnKwh5k6Dr3PKTls8oNPYiA6DLow9PMPNcyhtIeHlyu_i3)

### 주요 API 시퀀스 다이어그램

#### 상품 검색 API
![상품 검색 API](https://www.plantuml.com/plantuml/png/ZLDTQnD157sVNt4VMsXf4PzyAAqY8ZueuXyOiuDTs2RfxaJmRQLHqcOLFv9cBTcQXz2c476xIR65WFzdvyv_y6Q6TaqCQY2NOVQSS-yvTtSpaDoNjHsFyPAi-73FSqLPiYfTkYMtoiiIjgekSxZaptaWOETLiEin9btf2S2zr_fJ8yz2tDk7hISF0PjT7OPuq0VLx6ConiDn_kDI7GyOiyAmkZ7LWpkpN3ngOVODbXxSVmPhLR_Yr4eoM0i4zqlRownSaG9yzyMs99vbaF0AuDLOludoKK-T35SWJyCyErDP2xjrK9_FGKUHIYRg-wMkfu2zkceEsTIb-v9BOLpHlnZ8pZ37wlRqqG3hvwRjIK9yJ_uTGXdqTJHWG3-ZJmsiT-fnUtqTxd87dehTcWYa0Ssuisgw7UDf2DW-m2ISQzVveBuEGPsrA6i2CU45uXyJqJ4VNR1Yf0sxnfijmFKQVaueIkFWSD_0JRd9pB3CbUMl5k4P9kca-MW2AetrbzW07R60RSfC-7Q0Qxv-7qzVdjCsO1QJxfzuJ3wgvWMeToa1oRJPFdaiPlRad2Xstc8IcOSBTtEBTlFu4IofgqRX8yNEUDbmJCvpYiUD_rAq8V_kgjTM8wCxcn0fs5vbnpgP7jWcLVf0Vm40)

#### 상품 상세 조회 API
![상품 상세 조회 API](https://www.plantuml.com/plantuml/png/ZLDTQnD157sVNt4VsuVI8dtAGsdzK8AW8lw1DJlemZP9inD1H5XnAJKRyODkNKEs9X1g2X7MV625elzdvyv_y6PsisQrXIxip31xpvbppjpTTeJH4CrTcnaLKMl03TlYLS7gj6bLhBfH5R1JjvXf2EEnuN3OVUxisOm9IzWSyFKhzV40i1-hJXjs7fG1_QvoNMmDGFe3ZAPuD4r-dCdZ8MCfCwnjpGcXz0yOEuSu_WOhTsuzWlLwesOsAy9PVv6jokRBLLQj2GuDwybJGMZL-eczO45IlYdxtPon5yZ9b2PF-YCszsKzCmJNFkWjAj1Wm6vFTOReVOTaxE99H2XRB7rNOO1-z5yPsHsezf01FPgPg3ER9TZSs83hXWaF-LwJEq8N5IoaR2e8yQiB6BGmSYyuyjDx-MK4ylIGegL2ncs7Nq4PBPF9ZFsLjfLU7PIACUNv_pwN9oDjwEYDHkaX3q-3zLPAivQdc26JSPnCpa76eVeSwaAJNu3MmrAus9_YyMWvXBOkkIJIJRXN4t2xrgoQULanLDr7AbXq0qOUOFnBJmSXnRc_E24FCbUHCnOLv4QZ3zAVWNmRKsChBmQrFrF1ALb-1qdiKjoNAxz6pN3_Bgnavz64tbbs1Qki61Q1Ku6PHpPVi6qQwJVz0m00)

#### 주문 및 결제 프로세스
![주문 및 결제 프로세스 1](https://www.plantuml.com/plantuml/png/rLZTRXD75BxVfnXnqTeIA53rAX8jgMsGrP2a3aZjLRIi9tILPTVTtKH4g9935fIGjAI5K0DsY4N8J-LApWyX5-aBUMRVeRF7aw6dwsHTMwLjBc9hTixFd7E-xnll5S_NNN_wZgNfXk-uvAP7NQqabap3BEcsJpAMIMqVBGsLJA9xvDoeMwHknh5zrx4iwfv3c-3XE7LdJ8EY1sFwx1tXiTEZ8M_MDcxetbIdXr_gKxeZZ4LqhQZx-YtTe-JwxFXNmv1CLYwTqpJVz2rA-FnS-FC2uQ-FMUE8iEPZqjfhylKguKlLi5pcZpO8MzhWjGF-xA3rxeYjRMlQntAxCAxnO9U4jHMs6MXHKSY5psGjo23XUmVXN8El1lpbF0b_h4HUjig4RmLyxO7MtYOCe5A3okUBftplDv8Q6ns_GGRqaZdWH3Noqfhj-9IuvkrlmNSxxtpsFE4B5VPgyRn8liaVrY72-YhRFo3ioQxu6h2bXgQ9628KghVmOhXKzoaifj8ah1pnmshKOdD6BEFcQCXKk3effKhc92olDyCNovfwVW77ZHkmPYLyNj4qtV89NskqzjS9Eopp-gv6n1-EgyerEksFJexxZZ6LkslGacywjd1K5Iq1ApZldvlnwSMBv0kzI0hqksdg-II58gN1gBqPxA2PWpWTmdzPOQyQ9FofAgP3evP7JpRmmnrs6E1yOtMIBLPrMY_pjJSa5HLZlZf8nd8ZsVp8jNOgPrLFkibdPUN0eDlOK7cSGc90cIb_7h3Nozr5Mmj4YLHrcjkjvWf9bQXTDEtRq3N2DskjPZdvj1rT9QI2w0EykEssotKrx62i1XvRAUze7Vmhce5Pb9IXe1lnaKtT2RDudlZHlzRH0b-jHtY4uoBh2_5ibSFtd76pC1nhDhQAboxPJP4c7FrKzC5oWNebn_PerdUaLv9gXuH0uUD6kBgTnf4Kg-0uSRn-Sl4IoG0z5KaA0mTOMiNf3DtuuLczIjXceytcR7qnV7IaYNaKTAxwjrSH6p2TnrkDlGfAu45LUTWAmcUBaidkD_YB7RwseXw-s-OFb-C3W2G3zfoTj3mk4ezhEIGM0wLsEmSCtJQezJcivekNxvd5xt7DuqDJSXs3UfuSY8nkMRTqOqeXXgGoGoEPtF1mBjkkV49ZkwEa-QeWrmViXwOufZWZ2f904CYm5uu2Hc4hYcBO_bl-ygzi9RQ8nYGdWtmaPeElCk304r5XMmPQP-cHiequlZbf6l0TSnVs05uL0kGmyTLbjdbCeW7QUSfhno2J3-k969Tm4DFRsZ-M0EyDp2eJA0SHQOZ2Y-dkY5mr5ohz1NR_G8ekM83oAbG5kwtzjmceWZrdeagq-_Q4xqnH6_5MjpZpfeq8D0Xe7vrul7mFFlf7tjXGlZVGnGHcXhhcv6nS3RiNEyd5hRso478fVLsBMyo1dBBkT0tRn7HHaA-CeR9RsWYNj-5XxvGagl_DzTp8ZObSeJ1QQBUXLsfIkVP5KTsaW2sISy0KWEedhx_I9evorTZA1djJmIWddP6EBU981w2ZNaeizz_BVFQ3jb2s8No-AkQUf4H5nPdmfJRzT_hPGre9SkFOawPx9oOt7cQ7HCs1eiCvQqVlDMTW_Fgur1sSPY_waw11y9SqfPsrw6mz0ejEKseNOxeaCPEWIx0dGPVQg2FyzwViINL0vTkJJ4tgfeKrYhgkuquOJf5UlXVzxrka54rU7Sh_etT45EbVl2X-c2kXhFTLqS1fb_Ps7qIJ-H-u5kA4agw5U3U68RpD4P_iSK1uvIr_K4dBttRgpTxMCe0N_SI3DshE37KxLVqIEmpOre808uHyjYY2FDhWnsM-8TRdqbhCvbImvEwMJ9UsUTRhuqMRF4KqWl9baJnUqZitPAjAykiEgzSqpNAS4carvs8x-i5cxkknVE7_1aeOcTF6-8fuAjw0_m40)

## 🚀 성능 요구사항

- 조회성 GET API (상품 조회): 200ms 이내 응답
- 주문, 결제 POST API: 500ms 이내 응답
- 상품 조회: 1,000 QPS 처리 가능
- 주문 결제: 100 TPS 처리 가능

## 💻 설치 및 실행 방법

### 요구 사항
- Java 17 이상
- MySQL 8.0 이상
- Gradle

### 환경 설정
1. 프로젝트 클론
```bash
git clone https://github.com/your-username/ecommerce-backend.git
cd ecommerce-backend
```

2. 데이터베이스 설정
```bash
# MySQL에 접속
mysql -u root -p

# 데이터베이스 생성
CREATE DATABASE ecommerce;
```

3. 애플리케이션 설정
   `src/main/resources/application.yml`
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce?useSSL=false&serverTimezone=UTC
    username: {your_username}
    password: {your_password}
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        
kakao:
  pay:
    cid: {카카오페이_CID}
    admin-key: {카카오페이_ADMIN_KEY}
    approval-url: http://localhost:8080/api/orders/payment/success
    cancel-url: http://localhost:8080/api/orders/payment/cancel
    fail-url: http://localhost:8080/api/orders/payment/fail
```

### 빌드 및 실행
```bash
# 프로젝트 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### API 문서 접근
애플리케이션 실행 후 브라우저에서 다음 URL을 통해 Swagger API 문서에 접근할 수 있습니다:
```
http://localhost:8080/swagger-ui/index.html
```

## 📝 향후 계획 (Phase 2)

- 장바구니 관리 기능
- 판매자 페이지 및 기능
- 관리자 기능
- 쿠폰/포인트 시스템
- 회원 관리 (로그인/로그아웃)
- 상품 리뷰 시스템
- 주문 취소/환불 프로세스
