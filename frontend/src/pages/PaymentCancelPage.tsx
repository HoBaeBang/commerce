import { Link } from 'react-router-dom'
import { XCircle, Home, ShoppingBag } from 'lucide-react'

const PaymentCancelPage = () => {
  return (
    <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div className="text-center">
        <div className="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-red-100 mb-6">
          <XCircle className="h-8 w-8 text-red-600" />
        </div>
        
        <h1 className="text-3xl font-bold text-gray-900 mb-4">
          결제가 취소되었습니다
        </h1>
        
        <p className="text-lg text-gray-600 mb-8">
          결제가 취소되었습니다. 다시 시도하시거나 다른 결제 방법을 선택해주세요.
        </p>

        <div className="bg-gray-50 rounded-lg p-6 mb-8">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">안내사항</h2>
          <div className="space-y-2 text-sm text-gray-600">
            <p>• 결제 취소 시 주문도 함께 취소됩니다.</p>
            <p>• 다른 결제 방법으로 다시 주문하실 수 있습니다.</p>
            <p>• 문의사항이 있으시면 고객센터로 연락해주세요.</p>
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

export default PaymentCancelPage 