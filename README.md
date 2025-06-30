# Commerce - 온라인 쇼핑몰

Spring Boot 기반의 백엔드 API와 React 기반의 프론트엔드 웹 애플리케이션으로 구성된 완전한 이커머스 솔루션입니다.

## 🏗️ 프로젝트 구조

```
commerce/
├── cmc/                    # Spring Boot 백엔드
│   ├── src/main/java/     # Java 소스 코드
│   ├── src/main/resources/ # 설정 파일
│   └── build.gradle       # Gradle 빌드 설정
├── frontend/              # React 프론트엔드
│   ├── src/               # React 소스 코드
│   ├── public/            # 정적 파일
│   └── package.json       # npm 의존성
├── data/                  # 데이터베이스 스키마 및 초기 데이터
├── docs/                  # 프로젝트 문서
└── docker-compose.yml     # Docker 컨테이너 설정
```

## 🚀 기술 스택

### 백엔드 (cmc/)
- **Spring Boot 3.2** - Java 기반 웹 프레임워크
- **Spring Data JPA** - 데이터 접근 계층
- **MySQL 9.2** - 관계형 데이터베이스
- **Gradle** - 빌드 도구
- **OpenAPI 3.0** - API 문서화

### 프론트엔드 (frontend/)
- **React 18** - 사용자 인터페이스 라이브러리
- **TypeScript** - 타입 안전성
- **Vite** - 빠른 개발 서버 및 빌드 도구
- **Tailwind CSS** - 유틸리티 우선 CSS 프레임워크
- **React Router** - 클라이언트 사이드 라우팅
- **React Query** - 서버 상태 관리
- **Axios** - HTTP 클라이언트

## 📋 주요 기능

### 백엔드 API
- 🏪 **상품 관리** - 상품 조회, 검색, 필터링
- 🛒 **주문 관리** - 주문 생성, 조회, 취소
- 💳 **결제 시스템** - 카카오페이 연동
- 📊 **데이터 관리** - MySQL 데이터베이스

### 프론트엔드 웹
- 🏠 **홈페이지** - 메인 랜딩 페이지
- 📦 **상품 목록** - 상품 검색, 필터링, 정렬
- 🔍 **상품 상세** - 상품 정보 및 구매 옵션
- 🛒 **장바구니** - 상품 관리 및 주문 요약
- 💳 **결제** - 주문 정보 입력 및 카카오페이 결제
- ✅ **주문 완료** - 주문 성공 페이지

## 🎨 디자인 특징

- **반응형 디자인** - 24인치~32인치 모니터 및 모바일 최적화
- **깔끔한 UI** - 현대적이고 직관적인 인터페이스
- **신뢰성 있는 디자인** - 전문적이고 안전한 느낌의 색상 및 레이아웃
- **접근성** - 키보드 네비게이션 및 스크린 리더 지원

## 🛠️ 설치 및 실행

### 필수 요구사항

- **Java 17** 이상
- **Node.js 18** 이상
- **Docker & Docker Compose**
- **MySQL 9.2** (Docker로 자동 설치)

### 1. 프로젝트 클론

```bash
git clone <repository-url>
cd commerce
```

### 2. 백엔드 실행

```bash
# Docker로 MySQL 및 백엔드 실행
docker-compose up -d

# 또는 수동으로 실행
cd cmc
./gradlew bootRun
```

### 3. 프론트엔드 실행

```bash
# 자동 실행 스크립트 사용
./frontend/start.sh

# 또는 수동으로 실행
cd frontend
npm install
npm run dev
```

### 4. 접속

- **프론트엔드**: http://localhost:3000
- **백엔드 API**: http://localhost:8080
- **API 문서**: http://localhost:8080/swagger-ui.html

## 📁 상세 구조

### 백엔드 (cmc/)

```
src/main/java/com/aslan/cmc/
├── CommerceApplication.java      # 메인 애플리케이션
├── common/                       # 공통 컴포넌트
│   └── error/                   # 에러 처리
├── config/                      # 설정
│   └── OpenAPIConfig.java       # Swagger 설정
├── products/                    # 상품 관리
│   ├── controller/              # REST API 컨트롤러
│   ├── service/                 # 비즈니스 로직
│   ├── repository/              # 데이터 접근
│   └── data/dto/                # 데이터 전송 객체
├── orders/                      # 주문 관리
│   ├── controller/              # 주문 API
│   ├── service/                 # 주문 비즈니스 로직
│   └── repository/              # 주문 데이터 접근
└── payment/                     # 결제 시스템
    ├── controller/              # 결제 API
    ├── service/                 # 카카오페이 연동
    └── config/                  # 결제 설정
```

### 프론트엔드 (frontend/)

```
src/
├── components/                  # 재사용 가능한 컴포넌트
│   ├── Layout.tsx              # 레이아웃 컴포넌트
│   ├── Header.tsx              # 헤더 컴포넌트
│   ├── Footer.tsx              # 푸터 컴포넌트
│   └── ProductCard.tsx         # 상품 카드 컴포넌트
├── pages/                      # 페이지 컴포넌트
│   ├── HomePage.tsx            # 홈페이지
│   ├── ProductListPage.tsx     # 상품 목록 페이지
│   ├── ProductDetailPage.tsx   # 상품 상세 페이지
│   ├── CartPage.tsx            # 장바구니 페이지
│   ├── CheckoutPage.tsx        # 결제 페이지
│   └── OrderSuccessPage.tsx    # 주문 완료 페이지
├── services/                   # API 서비스
│   └── api.ts                 # API 함수들
├── types/                     # TypeScript 타입 정의
│   └── api.ts                 # API 응답 타입들
├── App.tsx                    # 메인 앱 컴포넌트
├── main.tsx                   # 앱 진입점
└── index.css                  # 전역 스타일
```

## 🔧 환경 설정

### 데이터베이스 설정

MySQL 데이터베이스는 Docker Compose를 통해 자동으로 설정됩니다:

```yaml
# docker-compose.yml
mysql:
  image: mysql:9.2
  environment:
    MYSQL_ROOT_PASSWORD: root
    MYSQL_DATABASE: commerce
    MYSQL_USER: commerce
    MYSQL_PASSWORD: commerce123
```

### API 설정

백엔드 API는 기본적으로 `http://localhost:8080`에서 실행되며, 프론트엔드는 Vite 프록시를 통해 API 요청을 자동으로 백엔드로 전달합니다.

## 🎯 주요 API 엔드포인트

### 상품 API
- `GET /api/v1/products` - 상품 목록 조회
- `GET /api/v1/products/{id}` - 상품 상세 조회

### 주문 API
- `POST /api/v1/orders` - 주문 생성
- `GET /api/v1/orders/{orderId}` - 주문 조회
- `POST /api/v1/orders/{orderId}/cancel` - 주문 취소

### 결제 API
- `POST /api/v1/payments/ready` - 카카오페이 결제 준비
- `POST /api/v1/payments/approve` - 카카오페이 결제 승인
- `POST /api/v1/payments/cancel` - 결제 취소

## 🎨 스타일 가이드

### 색상 팔레트

- **Primary**: Blue 계열 (#3B82F6 ~ #1E3A8A)
- **Gray**: Neutral 계열 (#F9FAFB ~ #111827)
- **Success**: Green (#10B981)
- **Error**: Red (#EF4444)

### 타이포그래피

- **Font Family**: Inter
- **Weights**: 300, 400, 500, 600, 700

## 🔄 개발 워크플로우

1. **백엔드 개발**
   ```bash
   cd cmc
   ./gradlew bootRun
   ```

2. **프론트엔드 개발**
   ```bash
   cd frontend
   npm run dev
   ```

3. **데이터베이스 관리**
   ```bash
   docker-compose up -d mysql
   ```

4. **전체 시스템 실행**
   ```bash
   docker-compose up -d
   ./frontend/start.sh
   ```

## 🚀 배포

### 백엔드 배포

```bash
cd cmc
./gradlew build
java -jar build/libs/cmc-0.0.1-SNAPSHOT.jar
```

### 프론트엔드 배포

```bash
cd frontend
npm run build
```

빌드된 `dist` 폴더의 내용을 정적 호스팅 서비스에 업로드하세요.

### Docker 배포

```bash
# 전체 시스템 배포
docker-compose -f docker-compose.prod.yml up -d
```

## 📊 테스트

### 백엔드 테스트

```bash
cd cmc
./gradlew test
```

### 프론트엔드 테스트

```bash
cd frontend
npm test
```

## 📚 문서

- [API 문서](docs/commerce-api.yaml) - OpenAPI 3.0 스펙
- [ERD 문서](docs/commerce-ERD.md) - 데이터베이스 설계
- [프론트엔드 가이드](frontend/README.md) - React 애플리케이션 가이드

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 📞 지원

문제가 있거나 질문이 있으시면 이슈를 생성해주세요.

---

**Commerce** - 최고의 쇼핑 경험을 제공하는 온라인 쇼핑몰
