#!/bin/bash

echo "🚀 Commerce Frontend 시작 중..."

# Node.js 버전 확인
echo "📋 Node.js 버전 확인 중..."
node --version
npm --version

# 의존성 설치
echo "📦 의존성 설치 중..."
npm install

# 개발 서버 시작
echo "🌐 개발 서버 시작 중..."
echo "📍 접속 주소: http://localhost:3000"
echo "🔗 API 서버: http://localhost:8080"
echo ""
echo "💡 팁:"
echo "   - 백엔드 서버가 실행 중인지 확인하세요"
echo "   - 브라우저에서 http://localhost:3000 접속"
echo "   - Ctrl+C로 서버 중지"
echo ""

npm run dev 