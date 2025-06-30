# 제안된 API 명세 변경안

회원 시스템, 사용자 상품 등록, 배송지 관리 기능 추가에 따라 API는 다음과 같이 변경 및 확장될 것을 제안합니다.

## API 변경 요약

-   **신규 태그(Tag) 추가**: `사용자`, `인증` 태그를 추가하여 API를 그룹화합니다.
-   **인증 흐름 API**: 소셜 로그인을 시작하고, 로그인 상태를 확인하며, 로그아웃하는 API를 정의합니다.
-   **사용자 상품 관리 API**: 인증된 사용자가 자신의 상품을 생성, 조회, 수정, 삭제하는 API를 정의합니다.
-   **기존 API 변경**: 주문 생성(`POST /orders`)과 같은 기존 API는 이제 사용자 인증을 필수로 요구하게 됩니다.

---

## 신규 및 변경 API 명세

### 태그

```yaml
tags:
  - name: 인증
    description: 소셜 로그인, 로그아웃 등 인증 관련 API
  - name: 사용자
    description: 로그인된 사용자 정보 조회 및 관리 API
  - name: 상품 (수정)
    description: 상품 조회, 검색 및 **생성, 수정, 삭제** 관련 API
# ... 기존 태그
```

### 인증 API (`/auth`)

-   **`GET /oauth2/authorization/{provider}`**
    -   **summary**: 소셜 로그인 페이지로 리디렉션
    -   **description**: 사용자를 해당 `provider`(google, kakao, naver)의 로그인 페이지로 이동시킵니다. 실제 API 호출이라기보다는, 프론트엔드에서 연결할 링크입니다.
    -   **parameters**: `provider` (path, string, required)
    -   **responses**: `302 Found` - 로그인 성공 시 프론트엔드의 콜백 URL로 리디렉션됩니다.

-   **`POST /logout`**
    -   **summary**: 로그아웃
    -   **description**: 현재 사용자의 세션을 만료시키고 로그아웃 처리합니다.
    -   **responses**: `200 OK`

### 사용자 API (`/users`)

-   **`GET /api/v1/users/me`**
    -   **summary**: 내 정보 조회
    -   **description**: 현재 로그인된 사용자의 프로필 정보(이메일, 이름, 역할 등)를 반환합니다.
    -   **security**: 인증 필요 (세션 또는 Bearer 토큰)
    -   **responses**:
        -   `200 OK`: `UserResponseDto` 반환
        -   `401 Unauthorized`: 인증되지 않은 사용자

-   **`GET /api/v1/users/me/products`**
    -   **summary**: 내가 등록한 상품 목록 조회
    -   **description**: 현재 로그인된 사용자가 등록한 모든 상품의 목록을 조회합니다.
    -   **security**: 인증 필요 (SELLER 권한)
    -   **responses**:
        -   `200 OK`: `ProductListResponse` 반환
        -   `401 Unauthorized`: 인증되지 않은 사용자
        -   `403 Forbidden`: SELLER 권한이 없는 사용자

-   **`POST /api/v1/users/me/nickname`**
    -   **summary**: 닉네임 설정/수정
    -   **description**: 현재 로그인된 사용자의 닉네임을 설정/수정합니다.
    -   **requestBody**:
        -   `nickname`: string
    -   **responses**:
        -   `200 OK`: 닉네임 변경 후 User 정보 반환

-   **`GET /api/v1/users/me/addresses`**
    -   **summary**: 내 배송지 목록 조회
    -   **description**: 현재 로그인된 사용자의 배송지 목록을 최대 5개까지 조회합니다.
    -   **security**: 인증 필요 (SELLER 권한)
    -   **responses**:
        -   `200 OK`: `AddressListResponse` 반환
        -   `401 Unauthorized`: 인증되지 않은 사용자
        -   `403 Forbidden`: SELLER 권한이 없는 사용자

-   **`POST /api/v1/users/me/addresses`**
    -   **summary**: 배송지 추가
    -   **description**: 현재 로그인된 사용자의 배송지를 최대 5개까지 추가합니다.
    -   **requestBody**:
        -   `address_nickname`: string
        -   `zipcode`: string
        -   `address`: string
        -   `address_detail`: string
        -   `is_default`: boolean
    -   **responses**:
        -   `200 OK`: 배송지 추가 성공 시 응답
        -   `400 Bad Request`: 잘못된 요청 데이터
        -   `401 Unauthorized`: 인증되지 않은 사용자
        -   `403 Forbidden`: SELLER 권한이 없는 사용자

-   **`PUT /api/v1/users/me/addresses/{addressId}`**
    -   **summary**: 배송지 정보 수정
    -   **description**: 현재 로그인된 사용자의 배송지 정보를 수정합니다.
    -   **security**: 인증 필요 (소유권 확인)
    -   **parameters**: `addressId` (path, integer, required)
    -   **requestBody**: `AddressUpdateRequestDto`
    -   **responses**:
        -   `200 OK`: 수정된 배송지 정보 반환
        -   `403 Forbidden`: 수정 권한이 없는 사용자
        -   `404 Not Found`: 존재하지 않는 배송지

-   **`DELETE /api/v1/users/me/addresses/{addressId}`**
    -   **summary**: 배송지 삭제
    -   **description**: 현재 로그인된 사용자의 배송지를 삭제합니다.
    -   **security**: 인증 필요 (소유권 확인)
    -   **parameters**: `addressId` (path, integer, required)
    -   **responses**:
        -   `204 No Content`: 성공적으로 삭제됨
        -   `403 Forbidden`: 삭제 권한이 없는 사용자
        -   `404 Not Found`: 존재하지 않는 배송지

-   **`PUT /api/v1/users/me/addresses/{addressId}/default`**
    -   **summary**: 해당 배송지를 기본 배송지로 설정
    -   **description**: 현재 로그인된 사용자의 배송지를 기본 배송지로 설정합니다.
    -   **security**: 인증 필요 (소유권 확인)
    -   **parameters**: `addressId` (path, integer, required)
    -   **responses**:
        -   `200 OK`: 기본 배송지 설정 성공 시 응답
        -   `403 Forbidden`: 설정 권한이 없는 사용자
        -   `404 Not Found`: 존재하지 않는 배송지

### 상품 API (`/products`)

-   **`POST /api/v1/products`**
    -   **summary**: 상품 등록
    -   **description**: 새로운 상품을 등록합니다. 상품 이미지 업로드를 위해 `multipart/form-data` 형식을 사용합니다.
    -   **security**: 인증 필요 (SELLER 권한)
    -   **requestBody**:
        -   `content-type`: `multipart/form-data`
        -   `schema`: `ProductCreateRequestDto` (name, price, stock, description 등) + `imageFile` (file)
    -   **responses**:
        -   `201 Created`: 생성된 상품의 `ProductDetailDto` 반환
        -   `400 Bad Request`: 잘못된 요청 데이터
        -   `401 Unauthorized`: 인증되지 않은 사용자
        -   `403 Forbidden`: SELLER 권한이 없는 사용자

-   **`PUT /api/v1/products/{productId}`**
    -   **summary**: 상품 정보 수정
    -   **description**: 기존 상품의 정보를 수정합니다. 상품을 등록한 본인만 수정할 수 있습니다.
    -   **security**: 인증 필요 (소유권 확인)
    -   **parameters**: `productId` (path, integer, required)
    -   **requestBody**: `ProductUpdateRequestDto`
    -   **responses**:
        -   `200 OK`: 수정된 상품의 `ProductDetailDto` 반환
        -   `403 Forbidden`: 수정 권한이 없는 사용자
        -   `404 Not Found`: 존재하지 않는 상품

-   **`DELETE /api/v1/products/{productId}`**
    -   **summary**: 상품 삭제
    -   **description**: 상품을 삭제합니다. 상품을 등록한 본인만 삭제할 수 있습니다.
    -   **security**: 인증 필요 (소유권 확인)
    -   **parameters**: `productId` (path, integer, required)
    -   **responses**:
        -   `204 No Content`: 성공적으로 삭제됨
        -   `403 Forbidden`: 삭제 권한이 없는 사용자
        -   `404 Not Found`: 존재하지 않는 상품

### 기존 API 변경 사항

-   **`POST /api/v1/orders` (주문 생성)**
    -   이제 **인증이 반드시 필요**하게 됩니다.
    -   주문 생성 시, 요청을 보낸 인증된 사용자의 `userId`가 주문 정보에 자동으로 기록됩니다. 비회원은 주문할 수 없습니다. 

### 기타

- 회원가입(소셜 로그인) 후 닉네임/기본 배송지 미설정 시, 최초 로그인 시점에 닉네임/배송지 입력을 강제하는 UX 필요
- 주문 생성 시 기본 배송지 자동 선택, 필요시 배송지 선택 가능
- 기존 상품/주문/결제 API는 기존과 동일하나, 주문 시 배송지 정보가 반드시 포함되어야 함 