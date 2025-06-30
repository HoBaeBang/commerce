# 프론트엔드 상태 관리 가이드 (장바구니)

이 문서는 React Context API를 사용하여 구현된 장바구니 전역 상태 관리 기능에 대해 설명합니다.

## 개요

장바구니 기능은 사용자가 선택한 상품들을 애플리케이션의 여러 페이지에 걸쳐 유지해야 하므로 전역 상태 관리가 필수적입니다.
우리 프로젝트에서는 React의 내장 기능인 Context API를 사용하여 `CartContext`를 구현했으며, 이를 통해 복잡한 외부 라이브러리 없이도 효율적으로 상태를 관리합니다.

-   **컨텍스트 위치**: `frontend/src/contexts/CartContext.tsx`
-   **주요 기술**: React Context API, `useReducer` 훅, `localStorage` 연동

`useReducer`를 사용하여 복잡한 상태 로직(추가, 삭제, 수량 변경)을 컴포넌트 외부에서 관리하여 코드의 가독성과 유지보수성을 높였습니다. 또한 `localStorage`를 사용하여 사용자가 브라우저를 새로고침하거나 재방문했을 때도 장바구니 상태가 유지되도록 구현했습니다.

## 데이터 구조

장바구니에서 사용되는 핵심 데이터 타입은 `CartItem`입니다.

```typescript
// frontend/src/types/api.ts
export interface CartItem {
  id: number;          // 상품 ID
  name: string;        // 상품명
  price: number;       // 상품 가격
  imageUrl: string;    // 상품 이미지 URL
  quantity: number;      // 장바구니에 담은 수량
  stock: number;         // 상품 재고
}
```

## 핵심 로직 및 함수

`CartContext`는 장바구니와 관련된 상태와 함수들을 `CartProvider`를 통해 하위 컴포넌트에 제공합니다.

### 제공되는 함수

-   `addToCart(item: Product)`: 상품 상세 정보(`Product`)를 받아 장바구니에 추가합니다. 이미 있는 상품이면 수량을 1 증가시킵니다.
-   `removeFromCart(id: number)`: 상품 ID를 받아 장바구니에서 해당 상품을 완전히 제거합니다.
-   `updateQuantity(id: number, quantity: number)`: 특정 상품의 수량을 지정된 값으로 업데이트합니다. 수량이 0 이하이면 상품을 제거하고, 재고를 초과할 수 없습니다.
-   `clearCart()`: 장바구니의 모든 상품을 비웁니다. 주로 결제가 완료되었을 때 사용합니다.
-   `getItemQuantity(id: number)`: 특정 상품이 장바구니에 몇 개 담겨있는지 반환합니다.

### 상태 값

-   `cartItems`: 현재 장바구니에 담긴 `CartItem` 배열.
-   `totalQuantity`: 장바구니에 담긴 모든 상품의 총개수. (Header의 아이콘에 표시)
-   `totalPrice`: 장_바구니에 담긴 모든 상품의 총가격.

## 사용 방법

컴포넌트에서 장바구니 상태나 함수를 사용하려면 `useCart` 커스텀 훅을 호출하면 됩니다.

### `App.tsx` 설정

애플리케이션 전체에서 `useCart`를 사용할 수 있도록, 최상위 컴포넌트인 `App.tsx`를 `CartProvider`로 감싸야 합니다.

```tsx
// frontend/src/App.tsx
// ...
import { CartProvider } from './contexts/CartContext';

function App() {
  return (
    <CartProvider>
      <Router>
        {/* ... 라우팅 설정 ... */}
      </Router>
    </CartProvider>
  );
}
```

### 컴포넌트에서 사용 예시

상품 상세 페이지에서 '장바구니에 추가' 버튼을 구현하는 예시입니다.

```tsx
// frontend/src/pages/ProductDetailPage.tsx
import { useCart } from '../contexts/CartContext';
import { Product } from '../types/api';

const ProductDetailPage = () => {
  const { addToCart } = useCart();
  const product: Product = /* API로부터 받아온 상품 데이터 */;

  const handleAddToCart = () => {
    addToCart(product);
    alert(`${product.name}이(가) 장바구니에 추가되었습니다.`);
  };

  return (
    <div>
      {/* ... 상품 정보 표시 ... */}
      <button onClick={handleAddToCart}>장바구니에 추가</button>
    </div>
  );
};
```

이와 같이 `useCart` 훅을 사용하면 어떤 컴포넌트에서든 손쉽게 장바구니의 상태를 읽고 업데이트할 수 있습니다. 