export interface Product {
  id: number
  name: string
  price: number
  imageUrl?: string
  stockQuantity: number
}

export interface ProductListResponse {
  totalItems: number
  totalPages: number
  currentPage: number
  products: Product[]
}

export interface OrderItem {
  productId: number
  quantity: number
}

export interface OrderRequest {
  orderItems: OrderItem[]
  shippingZipcode: string
  shippingAddress: string
  shippingAddressDetail: string
  receiverName: string
  receiverPhone: string
  memo?: string
}

export interface OrderResponse {
  orderId: string
  orderStatus: 'PENDING' | 'PAID' | 'CANCELLED' | 'PAYMENT_FAILED' | 'PAYMENT_ERROR' | 'EXPIRED'
  totalProductAmount: number
  shippingAmount: number
  paymentAmount: number
  paymentDueDate: string
  kakaopayReadyUrl?: string
  orderDate: string
  orderItems: Array<{
    productId: number
    productName: string
    price: number
    quantity: number
  }>
  shippingInfo: {
    receiverName: string
    receiverPhone: string
    shippingZipcode: string
    shippingAddress: string
    shippingAddressDetail: string
    memo?: string
  }
}

export interface KakaoPayReadyResponse {
  tid: string
  next_redirect_pc_url: string
  created_at: string
}

export interface KakaoPayApproveResponse {
  aid: string
  tid: string
  cid: string
  partner_order_id: string
  partner_user_id: string
  payment_method_type: string
  item_name: string
  item_code: string
  quantity: number
  amount: {
    total: number
    tax_free: number
    vat: number
    point: number
    discount: number
  }
  created_at: string
  approved_at: string
}

export interface ErrorResponse {
  error_code: string
  error_message: string
  error_data?: any
} 