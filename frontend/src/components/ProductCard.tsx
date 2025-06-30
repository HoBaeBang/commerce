import { Link } from 'react-router-dom'
import { ShoppingCart, Eye } from 'lucide-react'
import { Product } from '../types/api'
import { useCart } from '../contexts/CartContext'

interface ProductCardProps {
  product: Product
  viewMode: 'grid' | 'list'
}

const ProductCard = ({ product, viewMode }: ProductCardProps) => {
  const { addToCart } = useCart()

  const handleAddToCart = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault() // Link로 감싸진 경우 페이지 이동 방지
    addToCart(product, 1)
  }

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('ko-KR').format(price)
  }

  if (viewMode === 'list') {
    return (
      <div className="card p-6">
        <div className="flex items-center space-x-6">
          <div className="flex-shrink-0">
            <img
              src={product.imageUrl || '/placeholder-product.jpg'}
              alt={product.name}
              className="w-32 h-32 object-cover rounded-lg"
            />
          </div>
          <div className="flex-1">
            <h3 className="text-xl font-semibold text-gray-900 mb-2">
              {product.name}
            </h3>
            <p className="text-gray-600 mb-4">
              재고: {product.stockQuantity}개
            </p>
            <div className="flex items-center justify-between">
              <span className="text-2xl font-bold text-primary-600">
                ₩{formatPrice(product.price)}
              </span>
              <div className="flex space-x-2">
                <Link
                  to={`/products/${product.id}`}
                  className="btn-outline"
                >
                  <Eye className="w-4 h-4 mr-2" />
                  상세보기
                </Link>
                <button
                  onClick={handleAddToCart}
                  className="btn-primary"
                  disabled={product.stockQuantity === 0}
                >
                  <ShoppingCart className="w-4 h-4 mr-2" />
                  장바구니
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="card group hover:shadow-lg transition-shadow duration-200">
      <div className="relative">
        <img
          src={product.imageUrl || '/placeholder-product.jpg'}
          alt={product.name}
          className="w-full h-48 object-cover rounded-t-lg group-hover:scale-105 transition-transform duration-200"
        />
        <div className="absolute top-2 right-2">
          <span className="bg-primary-600 text-white text-xs px-2 py-1 rounded-full">
            재고 {product.stockQuantity}
          </span>
        </div>
      </div>
      
      <div className="p-4">
        <h3 className="text-lg font-semibold text-gray-900 mb-2 line-clamp-2">
          {product.name}
        </h3>
        <p className="text-2xl font-bold text-primary-600 mb-4">
          ₩{formatPrice(product.price)}
        </p>
        
        <div className="flex space-x-2">
          <Link
            to={`/products/${product.id}`}
            className="flex-1 btn-outline text-center"
          >
            <Eye className="w-4 h-4 mr-2" />
            상세보기
          </Link>
          <button
            onClick={handleAddToCart}
            className="flex-1 btn-primary"
            disabled={product.stockQuantity === 0}
          >
            <ShoppingCart className="w-4 h-4 mr-2" />
            장바구니
          </button>
        </div>
      </div>
    </div>
  )
}

export default ProductCard 