import { useQuery } from 'react-query'
import { productApi } from '../services/api'
import ProductCard from '../components/ProductCard'
import { Link } from 'react-router-dom'

const HomePage = () => {
  // 최신 상품 4개만 미리보기
  const { data, isLoading, error } = useQuery(
    ['home-products'],
    () => productApi.getProducts({ page: 1, size: 4, direction: 'desc' })
  )

  return (
    <div className="min-h-screen bg-gray-50">
      {/* 메인 배너 */}
      <div className="bg-gradient-to-r from-primary-600 to-blue-400 py-20 mb-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <h1 className="text-5xl font-extrabold text-white mb-4 drop-shadow-lg">Welcome to Commerce!</h1>
          <p className="text-xl text-blue-100 mb-8">최신 트렌드 상품을 한눈에, 쉽고 빠른 쇼핑 경험을 제공합니다.</p>
          <Link
            to="/products"
            className="inline-block bg-white text-primary-600 font-bold px-8 py-4 rounded-lg shadow-lg hover:bg-primary-50 transition"
          >
            지금 쇼핑하기
          </Link>
        </div>
      </div>

      {/* 인기 상품 미리보기 */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mb-16">
        <h2 className="text-2xl font-bold text-gray-900 mb-6">인기 상품</h2>
        {isLoading ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {[...Array(4)].map((_, i) => (
              <div key={i} className="bg-gray-200 rounded-lg h-80 animate-pulse"></div>
            ))}
          </div>
        ) : error ? (
          <div className="text-center text-red-500">상품을 불러오는 중 오류가 발생했습니다.</div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {data?.data?.products?.map((product: any) => (
              <ProductCard key={product.id} product={product} viewMode="grid" />
            ))}
          </div>
        )}
        <div className="text-center mt-8">
          <Link
            to="/products"
            className="inline-block bg-primary-600 text-white font-semibold px-6 py-3 rounded-lg hover:bg-primary-700 transition"
          >
            전체 상품 보기
          </Link>
        </div>
      </div>

      {/* 카테고리/이벤트 안내 (예시) */}
      <div className="bg-white py-12 border-t border-b border-gray-200 mb-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 grid grid-cols-1 md:grid-cols-3 gap-8 text-center">
          <div>
            <h3 className="text-lg font-bold text-primary-600 mb-2">빠른 배송</h3>
            <p className="text-gray-600">오후 2시 이전 주문 시 당일 출고!</p>
          </div>
          <div>
            <h3 className="text-lg font-bold text-primary-600 mb-2">특가 이벤트</h3>
            <p className="text-gray-600">매주 새로운 할인 상품을 만나보세요.</p>
          </div>
          <div>
            <h3 className="text-lg font-bold text-primary-600 mb-2">고객센터</h3>
            <p className="text-gray-600">궁금한 점은 언제든 문의하세요.</p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default HomePage 