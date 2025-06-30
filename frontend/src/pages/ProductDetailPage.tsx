import { useState } from 'react'
import { useParams } from 'react-router-dom'
import { useQuery } from 'react-query'
import { ShoppingCart, Heart, Star, Truck, Shield } from 'lucide-react'
import { productApi } from '../services/api'
import { useCart } from '../contexts/CartContext'
import { Product } from '../types/api'

const ProductDetailPage = () => {
  const { productId } = useParams<{ productId: string }>()
  const [quantity, setQuantity] = useState(1)
  const { addToCart } = useCart()

  const { data, isLoading, error } = useQuery<Product>(
    ['product', productId],
    () => productApi.getProduct(parseInt(productId!)).then(res => res.data),
    {
      enabled: !!productId,
    }
  )

  const product = data;

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('ko-KR').format(price)
  }

  const handleQuantityChange = (newQuantity: number) => {
    if (product && newQuantity >= 1 && newQuantity <= product.stockQuantity) {
      setQuantity(newQuantity)
    }
  }

  const handleAddToCart = () => {
    if (product) {
      addToCart(product, quantity)
    }
  }

  if (isLoading) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="animate-pulse">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <div className="bg-gray-200 rounded-lg h-96"></div>
            <div className="space-y-4">
              <div className="h-8 bg-gray-200 rounded w-3/4"></div>
              <div className="h-6 bg-gray-200 rounded w-1/4"></div>
              <div className="h-4 bg-gray-200 rounded w-full"></div>
              <div className="h-4 bg-gray-200 rounded w-2/3"></div>
            </div>
          </div>
        </div>
      </div>
    )
  }

  if (error || !product) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">상품을 찾을 수 없습니다</h2>
          <p className="text-gray-600">요청하신 상품이 존재하지 않거나 삭제되었습니다.</p>
        </div>
      </div>
    )
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Product Image */}
        <div className="space-y-4">
          <div className="aspect-w-1 aspect-h-1 w-full">
            <img
              src={product.imageUrl || '/placeholder-product.jpg'}
              alt={product.name}
              className="w-full h-full object-cover rounded-lg shadow-lg"
            />
          </div>
        </div>

        {/* Product Info */}
        <div className="space-y-6">
          <div>
            <h1 className="text-3xl font-bold text-gray-900 mb-2">
              {product.name}
            </h1>
            <div className="flex items-center space-x-2 mb-4">
              <div className="flex items-center">
                {[...Array(5)].map((_, i) => (
                  <Star key={i} className="w-5 h-5 text-yellow-400 fill-current" />
                ))}
              </div>
              <span className="text-gray-600">(4.8)</span>
            </div>
          </div>

          <div className="border-t border-gray-200 pt-6">
            <div className="flex items-center justify-between mb-4">
              <span className="text-3xl font-bold text-primary-600">
                ₩{formatPrice(product.price)}
              </span>
              <span className="text-sm font-semibold text-gray-700">
                재고: {product.stockQuantity}개
              </span>
            </div>
          </div>

          {/* Quantity Selector */}
          <div className="border-t border-gray-200 pt-6">
            <label className="block text-sm font-medium text-gray-700 mb-2">
              수량
            </label>
            <div className="flex items-center space-x-3">
              <button
                onClick={() => handleQuantityChange(quantity - 1)}
                disabled={quantity <= 1}
                className="w-10 h-10 border border-gray-300 rounded-lg flex items-center justify-center hover:bg-gray-50 disabled:opacity-50"
              >
                -
              </button>
              <span className="w-16 text-center text-lg font-medium">
                {quantity}
              </span>
              <button
                onClick={() => handleQuantityChange(quantity + 1)}
                disabled={!product || quantity >= product.stockQuantity}
                className="w-10 h-10 border border-gray-300 rounded-lg flex items-center justify-center hover:bg-gray-50 disabled:opacity-50"
              >
                +
              </button>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="border-t border-gray-200 pt-6 space-y-3">
            <button
              onClick={handleAddToCart}
              disabled={!product || product.stockQuantity === 0}
              className="w-full btn-primary py-3 text-lg font-semibold disabled:opacity-50 flex items-center justify-center"
            >
              <ShoppingCart className="w-5 h-5 mr-2" />
              장바구니에 추가
            </button>
            <button className="w-full btn-outline py-3 text-lg font-semibold">
              <Heart className="w-5 h-5 mr-2" />
              찜하기
            </button>
          </div>

          {/* Features */}
          <div className="border-t border-gray-200 pt-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">서비스</h3>
            <div className="space-y-3">
              <div className="flex items-center space-x-3">
                <Truck className="w-5 h-5 text-primary-600" />
                <span className="text-gray-700">무료 배송 (5만원 이상)</span>
              </div>
              <div className="flex items-center space-x-3">
                <Shield className="w-5 h-5 text-primary-600" />
                <span className="text-gray-700">7일 무료 반품</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ProductDetailPage 