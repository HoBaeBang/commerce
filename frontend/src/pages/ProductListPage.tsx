import { useState } from 'react'
import { useQuery } from 'react-query'
import { Search, Filter, Grid, List } from 'lucide-react'
import { productApi } from '../services/api'
import ProductCard from '../components/ProductCard'

const ProductListPage = () => {
  const [keyword, setKeyword] = useState('')
  const [minPrice, setMinPrice] = useState('')
  const [maxPrice, setMaxPrice] = useState('')
  const [sort, setSort] = useState('createdAt')
  const [direction, setDirection] = useState<'asc' | 'desc'>('desc')
  const [viewMode, setViewMode] = useState<'grid' | 'list'>('grid')
  const [page, setPage] = useState(1)
  const [size, setSize] = useState(10)

  const { data, isLoading, error } = useQuery(
    ['products', page, size, direction, keyword, minPrice, maxPrice],
    () => productApi.getProducts({
      page,
      size,
      direction,
      keyword: keyword || undefined,
      minPrice: minPrice ? parseInt(minPrice) : undefined,
      maxPrice: maxPrice ? parseInt(maxPrice) : undefined
    }),
    {
      keepPreviousData: true,
    }
  )

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault()
    setPage(1) // 검색 시 첫 페이지로 이동
  }

  if (isLoading) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="animate-pulse">
          <div className="h-8 bg-gray-200 rounded w-1/4 mb-8"></div>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {[...Array(8)].map((_, i) => (
              <div key={i} className="bg-gray-200 rounded-lg h-80"></div>
            ))}
          </div>
        </div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">오류가 발생했습니다</h2>
          <p className="text-gray-600">상품을 불러오는 중 문제가 발생했습니다. 다시 시도해주세요.</p>
        </div>
      </div>
    )
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">상품 목록</h1>
        <p className="text-gray-600">
          총 {data?.data?.totalItems || 0}개의 상품이 있습니다.
        </p>
      </div>

      {/* Search and Filter */}
      <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-8">
        <form onSubmit={handleSearch} className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
              <input
                type="text"
                placeholder="상품명 검색..."
                value={keyword}
                onChange={(e) => setKeyword(e.target.value)}
                className="input pl-10"
              />
            </div>
            <input
              type="number"
              placeholder="최소 가격"
              value={minPrice}
              onChange={(e) => setMinPrice(e.target.value)}
              className="input"
            />
            <input
              type="number"
              placeholder="최대 가격"
              value={maxPrice}
              onChange={(e) => setMaxPrice(e.target.value)}
              className="input"
            />
            <select
              value={direction}
              onChange={(e) => setDirection(e.target.value as 'asc' | 'desc')}
              className="input"
            >
              <option value="desc">최신순</option>
              <option value="asc">오래된순</option>
            </select>
          </div>
        </form>
      </div>

      {/* View Mode Toggle */}
      <div className="flex justify-between items-center mb-6">
        <div className="flex items-center space-x-2">
          <button
            onClick={() => setViewMode('grid')}
            className={`p-2 rounded ${viewMode === 'grid' ? 'bg-primary-100 text-primary-600' : 'text-gray-400 hover:text-gray-600'}`}
          >
            <Grid className="w-5 h-5" />
          </button>
          <button
            onClick={() => setViewMode('list')}
            className={`p-2 rounded ${viewMode === 'list' ? 'bg-primary-100 text-primary-600' : 'text-gray-400 hover:text-gray-600'}`}
          >
            <List className="w-5 h-5" />
          </button>
        </div>
      </div>

      {/* Products Grid */}
      {data?.data?.products && data.data.products.length > 0 ? (
        <div className={`grid gap-6 ${
          viewMode === 'grid' 
            ? 'grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4' 
            : 'grid-cols-1'
        }`}>
          {data.data.products.map((product) => (
            <ProductCard key={product.id} product={product} viewMode={viewMode} />
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <h3 className="text-lg font-medium text-gray-900 mb-2">상품을 찾을 수 없습니다</h3>
          <p className="text-gray-600">검색 조건을 변경해보세요.</p>
        </div>
      )}
    </div>
  )
}

export default ProductListPage 