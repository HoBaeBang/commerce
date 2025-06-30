import { Link, useLocation } from 'react-router-dom'
import { CheckCircle, Home, ShoppingBag } from 'lucide-react'

const OrderSuccessPage = () => {
  const location = useLocation()
  const orderInfo = location.state || {
    orderId: 'ORD-2024-001',
    totalAmount: 1500000
  }

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('ko-KR').format(price)
  }

  return (
    <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div className="text-center">
        <div className="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-green-100 mb-6">
          <CheckCircle className="h-8 w-8 text-green-600" />
        </div>
        
        <h1 className="text-3xl font-bold text-gray-900 mb-4">
          주문이 완료되었습니다!
        </h1>
        
        <p className="text-lg text-gray-600 mb-8">
          주문해주셔서 감사합니다. 주문 내역은 이메일로 발송해드립니다.
        </p>

        <div className="bg-gray-50 rounded-lg p-6 mb-8">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">주문 정보</h2>
          <div className="space-y-2 text-sm text-gray-600">
            <div className="flex justify-between">
              <span>주문번호:</span>
              <span className="font-medium">{orderInfo.orderId}</span>
            </div>
            <div className="flex justify-between">
              <span>주문일시:</span>
              <span className="font-medium">{new Date().toLocaleString('ko-KR')}</span>
            </div>
            <div className="flex justify-between">
              <span>결제금액:</span>
              <span className="font-medium text-primary-600">₩{formatPrice(orderInfo.totalAmount)}</span>
            </div>
            <div className="flex justify-between">
              <span>결제수단:</span>
              <span className="font-medium">카카오페이 (테스트)</span>
            </div>
            <div className="flex justify-between">
              <span>주문상태:</span>
              <span className="font-medium text-green-600">주문 완료</span>
            </div>
          </div>
        </div>

        {/* 테스트 모드 알림 */}
        <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-8">
          <div className="flex">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-blue-400" viewBox="0 0 20 20" fill="currentColor">
                <path fillRule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clipRule="evenodd" />
              </svg>
            </div>
            <div className="ml-3">
              <h3 className="text-sm font-medium text-blue-800">
                테스트 모드 안내
              </h3>
              <div className="mt-2 text-sm text-blue-700">
                <p>이 주문은 테스트 모드에서 생성되었습니다. 실제 결제는 진행되지 않았으며, 실제 서비스에서는 카카오페이를 통해 결제가 진행됩니다.</p>
              </div>
            </div>
          </div>
        </div>

        <div className="flex flex-col sm:flex-row gap-4 justify-center">
          <Link
            to="/"
            className="btn-primary px-8 py-3 text-lg font-semibold"
          >
            <Home className="w-5 h-5 mr-2" />
            홈으로 돌아가기
          </Link>
          <Link
            to="/products"
            className="btn-outline px-8 py-3 text-lg font-semibold"
          >
            <ShoppingBag className="w-5 h-5 mr-2" />
            쇼핑 계속하기
          </Link>
        </div>
      </div>
    </div>
  )
}

export default OrderSuccessPage 