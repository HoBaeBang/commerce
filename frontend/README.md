# Commerce Frontend

React + TypeScript + Tailwind CSS로 구축된 이커머스 프론트엔드 애플리케이션입니다.

## 🚀 기술 스택

- **React 18** - 사용자 인터페이스 라이브러리
- **TypeScript** - 타입 안전성
- **Vite** - 빠른 개발 서버 및 빌드 도구
- **Tailwind CSS** - 유틸리티 우선 CSS 프레임워크
- **React Router** - 클라이언트 사이드 라우팅
- **React Query** - 서버 상태 관리
- **React Hook Form** - 폼 관리
- **Lucide React** - 아이콘 라이브러리
- **Axios** - HTTP 클라이언트

## 📋 주요 기능

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

- Node.js 18.0.0 이상
- npm 또는 yarn

### 설치

```bash
# 의존성 설치
npm install
```

### 개발 서버 실행

```bash
# 개발 서버 시작 (포트 3000)
npm run dev
```

### 빌드

```bash
# 프로덕션 빌드
npm run build

# 빌드 결과 미리보기
npm run preview
```

## 📁 프로젝트 구조

```
src/
├── components/          # 재사용 가능한 컴포넌트
│   ├── Layout.tsx      # 레이아웃 컴포넌트
│   ├── Header.tsx      # 헤더 컴포넌트
│   ├── Footer.tsx      # 푸터 컴포넌트
│   └── ProductCard.tsx # 상품 카드 컴포넌트
├── pages/              # 페이지 컴포넌트
│   ├── HomePage.tsx    # 홈페이지
│   ├── ProductListPage.tsx    # 상품 목록 페이지
│   ├── ProductDetailPage.tsx  # 상품 상세 페이지
│   ├── CartPage.tsx    # 장바구니 페이지
│   ├── CheckoutPage.tsx       # 결제 페이지
│   └── OrderSuccessPage.tsx   # 주문 완료 페이지
├── services/           # API 서비스
│   └── api.ts         # API 함수들
├── types/             # TypeScript 타입 정의
│   └── api.ts         # API 응답 타입들
├── App.tsx            # 메인 앱 컴포넌트
├── main.tsx           # 앱 진입점
└── index.css          # 전역 스타일
```

## 🔧 환경 설정

### API 서버 연결

프로젝트는 기본적으로 `http://localhost:8080`의 백엔드 API 서버와 연결됩니다.

Vite 설정에서 프록시가 구성되어 있어 `/api` 경로로 요청하면 자동으로 백엔드로 전달됩니다.

### 환경 변수

필요한 경우 `.env` 파일을 생성하여 환경 변수를 설정할 수 있습니다:

```env
VITE_API_BASE_URL=http://localhost:8080
```

## 🎯 주요 페이지

### 홈페이지 (`/`)
- 메인 랜딩 페이지
- 상품 소개 및 서비스 특징
- 상품 목록 및 장바구니로의 빠른 이동

### 상품 목록 (`/products`)
- 상품 검색 및 필터링
- 가격 범위 설정
- 정렬 옵션 (가격 낮은순/높은순)
- 그리드/리스트 뷰 전환

### 상품 상세 (`/products/:id`)
- 상품 상세 정보
- 수량 선택
- 장바구니 추가
- 찜하기 기능

### 장바구니 (`/cart`)
- 장바구니 상품 목록
- 수량 변경 및 삭제
- 주문 요약 및 결제 진행

### 결제 (`/checkout`)
- 주문자 정보 입력
- 배송지 정보 입력
- 카카오페이 결제 연동

### 주문 완료 (`/order-success`)
- 주문 성공 확인
- 주문 정보 표시
- 홈으로 돌아가기

## 🎨 스타일 가이드

### 색상 팔레트

- **Primary**: Blue 계열 (#3B82F6 ~ #1E3A8A)
- **Gray**: Neutral 계열 (#F9FAFB ~ #111827)
- **Success**: Green (#10B981)
- **Error**: Red (#EF4444)

### 타이포그래피

- **Font Family**: Inter
- **Weights**: 300, 400, 500, 600, 700

### 컴포넌트 클래스

- `.btn` - 기본 버튼 스타일
- `.btn-primary` - 주요 액션 버튼
- `.btn-secondary` - 보조 액션 버튼
- `.btn-outline` - 아웃라인 버튼
- `.input` - 입력 필드
- `.card` - 카드 컨테이너

## 🔄 개발 워크플로우

1. **개발 서버 시작**
   ```bash
   npm run dev
   ```

2. **브라우저에서 확인**
   - http://localhost:3000 접속

3. **코드 수정**
   - 파일 저장 시 자동으로 브라우저 새로고침

4. **빌드 및 배포**
   ```bash
   npm run build
   ```

## 🚀 배포

### 정적 호스팅

빌드된 파일을 정적 호스팅 서비스에 배포할 수 있습니다:

```bash
npm run build
```

`dist` 폴더의 내용을 Netlify, Vercel, GitHub Pages 등에 업로드하세요.

### Docker 배포

```dockerfile
FROM node:18-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

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