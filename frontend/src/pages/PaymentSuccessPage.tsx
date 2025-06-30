import { useEffect, useState } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { CheckCircle, Home, ShoppingBag } from 'lucide-react'
import { toast } from 'react-hot-toast'
import { paymentApi } from '../services/api'
import { useCart } from '../contexts/CartContext'

const PaymentSuccessPage = () => {
  const [searchParams] = useSearchParams()
  const [isProcessing, setIsProcessing] = useState(true)
  const [paymentInfo, setPaymentInfo] = useState<any>(null)
  const { clearCart } = useCart()

  const pgToken = searchParams.get('pg_token')
  const orderId = searchParams.get('partner_order_id')

  useEffect(() => {
    if (pgToken && orderId) {
      // 카카오페이 결제 승인 요청
      paymentApi.approvePayment({
        orderId: orderId,
        pgToken: pgToken
      })
        .then((res) => {
          setPaymentInfo(res.data)
          toast.success('결제가 성공적으로 완료되었습니다!')
          clearCart() // 결제 성공 시 장바구니 비우기
        })
        .catch((error) => {
          console.error('Payment approval error:', error)
          toast.error('결제 승인 중 오류가 발생했습니다.')
        })
        .finally(() => {
          setIsProcessing(false)
        })
    } else {
      setIsProcessing(false)
    }
  }, [pgToken, orderId, clearCart])

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('ko-KR').format(price)
  }

  if (isProcessing) {
    return (
      <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto mb-4"></div>
          <h2 className="text-xl font-semibold text-gray-900">결제 처리 중...</h2>
          <p className="text-gray-600 mt-2">잠시만 기다려주세요.</p>
        </div>
      </div>
    )
  }

  return (
    <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div className="text-center">
        <div className="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-green-100 mb-6">
          <CheckCircle className="h-8 w-8 text-green-600" />
        </div>
        
        <h1 className="text-3xl font-bold text-gray-900 mb-4">
          결제가 완료되었습니다!
        </h1>
        
        <p className="text-lg text-gray-600 mb-8">
          주문해주셔서 감사합니다. 주문 내역은 이메일로 발송해드립니다.
        </p>

        {paymentInfo && (
          <div className="bg-gray-50 rounded-lg p-6 mb-8">
            <h2 className="text-lg font-semibold text-gray-900 mb-4">결제 정보</h2>
            <div className="space-y-2 text-sm text-gray-600">
              <div className="flex justify-between">
                <span>주문번호:</span>
                <span className="font-medium">{paymentInfo.partner_order_id}</span>
              </div>
              <div className="flex justify-between">
                <span>결제번호:</span>
                <span className="font-medium">{paymentInfo.aid}</span>
              </div>
              <div className="flex justify-between">
                <span>결제일시:</span>
                <span className="font-medium">{paymentInfo.approved_at}</span>
              </div>
              <div className="flex justify-between">
                <span>결제금액:</span>
                <span className="font-medium text-primary-600">₩{formatPrice(paymentInfo.amount?.total || 0)}</span>
              </div>
              <div className="flex justify-between">
                <span>결제수단:</span>
                <span className="font-medium">카카오페이</span>
              </div>
              <div className="flex justify-between">
                <span>결제상태:</span>
                <span className="font-medium text-green-600">결제 완료</span>
              </div>
            </div>
          </div>
        )}

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

export default PaymentSuccessPage 