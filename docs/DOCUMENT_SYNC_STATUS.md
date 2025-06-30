# 프로젝트 문서 현행화 상태

본 문서는 현재 프로젝트의 실제 구현 내용과 `docs` 폴더 내 기존 문서 간의 차이점을 요약하고, 문서 보강을 위한 제안을 포함합니다.

---

## 1. 문서와 실제 구현 내용 비교 요약

전반적으로 `docs`의 내용은 현재 구현 상태를 잘 반영하고 있으나, 일부 기능 추가 및 수정으로 인해 차이가 발생했습니다.

### 1.1. API 명세 (`commerce-api.yaml` vs. Controller)

-   **동기화 상태: 부분 일치**
-   **차이점**:
    -   **Payment API 엔드포인트 불일치**:
        -   **문서**: `POST /payments/ready`, `POST /payments/approve` 등 결제 관련 API가 **정의되어 있지 않습니다.**
        -   **실제 구현**: `PaymentController`에 결제 준비(`ready`), 승인(`approve`), 취소(`cancel`) API가 **구현되어 있습니다.** 이는 프론트엔드의 결제 흐름에 필수적인 API들입니다.
    -   **주문 취소 API 방식 차이**:
        -   **문서**: `POST /orders/{orderId}/cancel`로 정의되어 있습니다.
        -   **실제 구현**: `OrderController`에 동일한 경로와 메소드로 구현되어 있어 이 부분은 일치합니다.
    -   **API Base Path**:
        -   **문서 서버 URL**: `/v1`을 포함 (`http://localhost:8080/v1`)
        -   **실제 구현**: `@RequestMapping`에 `/api/v1`을 사용 (`/api/v1/orders`)
        -   **결론**: 실제 호출 경로는 `/api/v1/orders` 이므로, 문서의 서버 URL 정의가 실제와 약간 다릅니다.

### 1.2. 데이터베이스 스키마 (`commerce-model.ddl` vs. JPA Entity)

-   **동기화 상태: 거의 일치 (JPA Entity가 최신)**
-   **차이점**:
    -   **`orders` 테이블**:
        -   `kakaopay_ready_url` 컬럼이 DDL 파일에는 없으나, `Order` 엔티티에는 **존재합니다.** 이 컬럼은 결제 페이지로 리디렉션하기 위해 필수적입니다.
    -   **`payments` 테이블**:
        -   `kakao_tid` 컬럼이 DDL 파일에는 없으나, `Payment` 엔티티에는 **존재합니다.** 이는 결제 승인 및 조회에 사용되는 카카오페이의 고유 거래 ID입니다.
    -   **공통**:
        -   `created_at`, `updated_at` 컬럼이 DDL에는 없으나, 모든 엔티티에 JPA의 `@CreationTimestamp`, `@UpdateTimestamp`를 통해 **구현되어 있습니다.**

---

## 2. 문서 보강을 위한 제안

현재 문서들은 프로젝트의 초기 설계를 이해하는 데 도움이 되지만, 최신 상태를 정확히 반영하고 유지보수성을 높이기 위해 다음과 같은 보강이 필요합니다.

1.  **`commerce-api.yaml` 업데이트**:
    -   누락된 **결제(Payment) 관련 API 전체 (ready, approve, cancel)를 추가**해야 합니다.
    -   서버 URL의 base path를 `/api/v1`로 명확히 하거나, 각 API 경로 설명에 `/api` 접두사를 포함하여 혼선을 줄여야 합니다.

2.  **`commerce-model.ddl` 및 `commerce-ERD.md` 업데이트**:
    -   `orders` 테이블에 `kakaopay_ready_url` 컬럼을 추가해야 합니다.
    -   `payments` 테이블에 `kakao_tid` 컬럼을 추가해야 합니다.
    -   모든 테이블에 `created_at`, `updated_at` 타임스탬프 컬럼을 추가하여 DDL과 ERD가 실제 DB 상태와 100% 일치하도록 만들어야 합니다.

3.  **신규 문서 추가 제안**:
    -   **아키텍처 다이어그램**: 전체 시스템(Frontend, Backend, DB, KakaoPay API)의 상호작용을 보여주는 고수준 아키텍처 다이어그램을 추가하면 프로젝트 구조를 빠르게 파악하는 데 큰 도움이 될 것입니다. (`cmc/README.md`에 추가한 시퀀스 다이어그램과 별도로 전체 구조도)
    -   **Frontend 상태 관리 가이드**: `Context API`를 사용한 장바구니 상태 관리 흐름, 데이터 구조, 주요 함수(`addToCart`, `removeFromCart` 등)에 대한 간단한 설명을 추가하면 프론트엔드 유지보수 시 유용할 것입니다. 