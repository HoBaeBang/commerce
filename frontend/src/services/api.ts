import axios from 'axios'


const API_BASE_URL = 'http://localhost:8080/api/v1'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// 상품 관련 API
export const productApi = {
  getProducts: (params: {
    page?: number
    size?: number
    direction?: string
    keyword?: string
    minPrice?: number
    maxPrice?: number
  } = {}) => {
    const { page = 1, size = 10, direction = 'desc', keyword, minPrice, maxPrice } = params
    const queryParams = new URLSearchParams()
    queryParams.append('page', page.toString())
    queryParams.append('size', size.toString())
    queryParams.append('direction', direction)
    if (keyword) queryParams.append('keyword', keyword)
    if (minPrice !== undefined) queryParams.append('minPrice', minPrice.toString())
    if (maxPrice !== undefined) queryParams.append('maxPrice', maxPrice.toString())
    
    return api.get(`/products?${queryParams.toString()}`)
  },
  
  getProduct: (id: number) => api.get(`/products/${id}`),
}

// 주문 관련 API
export const orderApi = {
  createOrder: (orderData: any) => api.post('/orders', orderData),
  
  getOrder: (orderId: string) => api.get(`/orders/${orderId}`),
}

// 결제 관련 API
export const paymentApi = {
  readyPayment: (params: { orderId: string; itemName: string; totalAmount: number }) =>
    api.post('/payments/ready', null, { params }),
  
  approvePayment: (params: { orderId: string; pgToken: string }) =>
    api.post('/payments/approve', null, { params }),
  
  cancelPayment: (params: { orderId: string; cancelAmount: number }) =>
    api.post('/payments/cancel', null, { params }),
}

export default api 
