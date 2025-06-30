import { useState, useEffect } from 'react'
import { useForm } from 'react-hook-form'
import { useMutation } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { toast } from 'react-hot-toast'
import { CreditCard, MapPin, User, Phone, Loader2, ShoppingBag } from 'lucide-react'
import { orderApi, paymentApi } from '../services/api'
import { useCart } from '../contexts/CartContext'

interface CheckoutForm {
  receiverName: string
  receiverPhone: string
  shippingZipcode: string
  shippingAddress: string
  shippingAddressDetail: string
  memo: string
}

const CheckoutPage = () => {
  const navigate = useNavigate()
  const { cartItems, clearCart } = useCart()

  useEffect(() => {
    if (cartItems.length === 0) {
      toast.error('장바구니가 비어있습니다. 상품을 추가해주세요.')
      navigate('/products')
    }
  }, [cartItems, navigate])
  
  const { register, handleSubmit, formState: { errors, isValid } } = useForm<CheckoutForm>({
    mode: 'onChange' 
  })

  // React Query의 useMutation을 활용한 주문 생성
  const createOrderMutation = useMutation({
    mutationFn: orderApi.createOrder,
    onSuccess: (orderData) => {
      toast.success('주문이 생성되었습니다. 카카오페이 결제 페이지로 이동합니다.')
      
      // 카카오페이 결제 준비 API 호출
      const paymentItemName = cartItems.length > 1
        ? `${cartItems[0].name} 외 ${cartItems.length - 1}건`
        : cartItems[0].name

      paymentReadyMutation.mutate({
        orderId: orderData.data.orderId,
        itemName: paymentItemName,
        totalAmount: orderData.data.paymentAmount,
      })
    },
    onError: (error: any) => {
      toast.error('주문 생성 중 오류가 발생했습니다.')
      console.error('Order creation error:', error)
    }
  })

  // React Query의 useMutation을 활용한 결제 준비
  const paymentReadyMutation = useMutation({
    mutationFn: paymentApi.readyPayment,
    onSuccess: (paymentData) => {
      // 카카오페이 결제 페이지로 리다이렉트
      window.location.href = paymentData.data.next_redirect_pc_url
    },
    onError: (error: any) => {
      toast.error('결제 준비 중 오류가 발생했습니다.')
      console.error('Payment ready error:', error)
    }
  })

  const onSubmit = (data: CheckoutForm) => {
    if (!isValid || cartItems.length === 0) {
      toast.error('입력 정보를 확인하거나 장바구니에 상품을 추가해주세요.')
      return
    }
    
    const orderData = {
      orderItems: cartItems.map(item => ({
        productId: item.id,
        quantity: item.quantity
      })),
      ...data
    }

    createOrderMutation.mutate(orderData)
  }

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('ko-KR').format(price)
  }

  const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0)
  const shippingFee = totalPrice >= 50000 || totalPrice === 0 ? 0 : 3000
  const finalTotal = totalPrice + shippingFee

  // 로딩 상태 관리
  const isLoading = createOrderMutation.isLoading || paymentReadyMutation.isLoading

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-8">주문/결제</h1>

      {/* 카카오페이 결제 안내 */}
      <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6">
        <div className="flex">
          <div className="flex-shrink-0">
            <svg className="h-5 w-5 text-blue-400" viewBox="0 0 20 20" fill="currentColor">
              <path fillRule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clipRule="evenodd" />
            </svg>
          </div>
          <div className="ml-3">
            <h3 className="text-sm font-medium text-blue-800">
              카카오페이 결제
            </h3>
            <div className="mt-2 text-sm text-blue-700">
              <p>주문 완료 후 카카오페이 결제 페이지로 이동하여 결제를 진행해주세요.</p>
              <p className="mt-1 text-xs">※ 실제 결제는 진행되지 않으며, 테스트용 결제입니다.</p>
            </div>
          </div>
        </div>
      </div>

      <form onSubmit={handleSubmit(onSubmit)} className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Order Form */}
        <div className="space-y-6">
          <div className="card p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
              <User className="w-5 h-5 mr-2" />
              주문자 정보
            </h2>
            
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  수령인 이름 *
                </label>
                <input
                  type="text"
                  {...register('receiverName', { required: '수령인 이름을 입력해주세요' })}
                  className="input"
                  placeholder="홍길동"
                  disabled={isLoading}
                />
                {errors.receiverName && (
                  <p className="text-red-500 text-sm mt-1">{errors.receiverName.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  연락처 *
                </label>
                <input
                  type="tel"
                  {...register('receiverPhone', { 
                    required: '연락처를 입력해주세요',
                    pattern: {
                      value: /^[0-9-]+$/,
                      message: '올바른 연락처 형식을 입력해주세요'
                    }
                  })}
                  className="input"
                  placeholder="010-1234-5678"
                  disabled={isLoading}
                />
                {errors.receiverPhone && (
                  <p className="text-red-500 text-sm mt-1">{errors.receiverPhone.message}</p>
                )}
              </div>
            </div>
          </div>

          <div className="card p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
              <MapPin className="w-5 h-5 mr-2" />
              배송지 정보
            </h2>
            
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  우편번호 *
                </label>
                <input
                  type="text"
                  {...register('shippingZipcode', { required: '우편번호를 입력해주세요' })}
                  className="input"
                  placeholder="12345"
                  disabled={isLoading}
                />
                {errors.shippingZipcode && (
                  <p className="text-red-500 text-sm mt-1">{errors.shippingZipcode.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  기본주소 *
                </label>
                <input
                  type="text"
                  {...register('shippingAddress', { required: '기본주소를 입력해주세요' })}
                  className="input"
                  placeholder="서울시 강남구 테헤란로 123"
                  disabled={isLoading}
                />
                {errors.shippingAddress && (
                  <p className="text-red-500 text-sm mt-1">{errors.shippingAddress.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  상세주소
                </label>
                <input
                  type="text"
                  {...register('shippingAddressDetail')}
                  className="input"
                  placeholder="123동 456호"
                  disabled={isLoading}
                />
              </div>
            </div>
          </div>

          <div className="card p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-4">배송 메모</h2>
            <textarea
              {...register('memo')}
              className="input"
              rows={3}
              placeholder="배송 시 요청사항이 있으시면 입력해주세요"
              disabled={isLoading}
            />
          </div>
        </div>

        {/* Order Summary */}
        <div className="space-y-6">
          <div className="card p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
              <ShoppingBag className="w-5 h-5 mr-2" />
              주문 상품
            </h2>
            
            <div className="space-y-4 max-h-60 overflow-y-auto">
              {cartItems.map(item => (
                <div key={item.id} className="flex items-center space-x-4">
                  <img
                    src={item.imageUrl || '/placeholder-product.jpg'}
                    alt={item.name}
                    className="w-16 h-16 object-cover rounded-lg"
                  />
                  <div className="flex-1">
                    <h3 className="font-medium text-gray-900">{item.name}</h3>
                    <p className="text-gray-600">수량: {item.quantity}개</p>
                  </div>
                  <p className="font-semibold text-primary-600">₩{formatPrice(item.price * item.quantity)}</p>
                </div>
              ))}
            </div>
          </div>

          <div className="card p-6 sticky top-8">
            <h2 className="text-xl font-semibold text-gray-900 mb-4">결제 정보</h2>
            
            <div className="space-y-3 mb-6">
              <div className="flex justify-between">
                <span className="text-gray-600">상품 금액</span>
                <span className="font-medium">₩{formatPrice(totalPrice)}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-600">배송비</span>
                <span className="font-medium">₩{formatPrice(shippingFee)}</span>
              </div>
              <div className="border-t border-gray-200 pt-3">
                <div className="flex justify-between">
                  <span className="text-lg font-semibold">총 결제금액</span>
                  <span className="text-lg font-bold text-primary-600">
                    ₩{formatPrice(finalTotal)}
                  </span>
                </div>
              </div>
            </div>

            <div className="space-y-4">
              <div className="flex items-center space-x-3 p-4 bg-gray-50 rounded-lg">
                <CreditCard className="w-6 h-6 text-primary-600" />
                <div>
                  <p className="font-medium text-gray-900">카카오페이</p>
                  <p className="text-sm text-gray-600">실제 카카오페이 API 사용</p>
                </div>
              </div>

              <button
                type="submit"
                disabled={isLoading || !isValid || cartItems.length === 0}
                className="w-full btn-primary py-3 text-lg font-semibold disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center"
              >
                {isLoading ? (
                  <>
                    <Loader2 className="w-5 h-5 mr-2 animate-spin" />
                    처리 중...
                  </>
                ) : (
                  `₩${formatPrice(finalTotal)} 카카오페이 결제`
                )}
              </button>

              {/* 에러 상태 표시 */}
              {(createOrderMutation.isError || paymentReadyMutation.isError) && (
                <div className="bg-red-50 border border-red-200 rounded-lg p-3">
                  <p className="text-red-700 text-sm">
                    결제 처리 중 오류가 발생했습니다. 다시 시도해주세요.
                  </p>
                </div>
              )}
            </div>
          </div>
        </div>
      </form>
    </div>
  )
}

export default CheckoutPage 