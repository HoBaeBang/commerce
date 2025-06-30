import { Link } from 'react-router-dom'
import { AlertTriangle, Home, ShoppingBag } from 'lucide-react'

const PaymentFailPage = () => {
  return (
    <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div className="text-center">
        <div className="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-yellow-100 mb-6">
          <AlertTriangle className="h-8 w-8 text-yellow-600" />
        </div>
        
        <h1 className="text-3xl font-bold text-gray-900 mb-4">
          결제에 실패했습니다
        </h1>
        
        <p className="text-lg text-gray-600 mb-8">
          결제 처리 중 오류가 발생했습니다. 다시 시도하시거나 다른 결제 방법을 선택해주세요.
        </p>

        <div className="bg-gray-50 rounded-lg p-6 mb-8">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">가능한 원인</h2>
          <div className="space-y-2 text-sm text-gray-600">
            <p>• 잔액 부족</p>
            <p>• 카드 한도 초과</p>
            <p>• 네트워크 오류</p>
            <p>• 결제 정보 오류</p>
          </div>
        </div>

        <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-8">
          <div className="flex">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-blue-400" viewBox="0 0 20 20" fill="currentColor">
                <path fillRule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clipRule="evenodd" />
              </svg>
            </div>
            <div className="ml-3">
              <h3 className="text-sm font-medium text-blue-800">
                도움이 필요하신가요?
              </h3>
              <div className="mt-2 text-sm text-blue-700">
                <p>결제 관련 문의사항이 있으시면 고객센터로 연락해주세요.</p>
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

export default PaymentFailPage 