# Commerce 프로젝트 설정 및 실행 가이드

이 가이드는 Commerce 프로젝트의 전체 시스템을 설정하고 실행하는 방법을 단계별로 설명합니다.

## 📋 사전 요구사항

### 필수 소프트웨어
- **Java 17** 이상
- **Node.js 18** 이상
- **Docker & Docker Compose**
- **Git**

### 확인 방법
```bash
# Java 버전 확인
java -version

# Node.js 버전 확인
node --version
npm --version

# Docker 확인
docker --version
docker-compose --version

# Git 확인
git --version
```

## 🚀 빠른 시작 (권장)

### 1. 프로젝트 클론
```bash
git clone <repository-url>
cd commerce
```

### 2. 전체 시스템 실행
```bash
# 백엔드 및 데이터베이스 실행
docker-compose up -d

# 프론트엔드 실행
./frontend/start.sh
```

### 3. 접속 확인
- **프론트엔드**: http://localhost:3000
- **백엔드 API**: http://localhost:8080
- **API 문서**: http://localhost:8080/swagger-ui.html

## 🔧 상세 설정 가이드

### 백엔드 설정

#### 1. 데이터베이스 설정
```bash
# MySQL 컨테이너 실행
docker-compose up -d mysql

# 데이터베이스 상태 확인
docker-compose ps mysql
```

#### 2. 백엔드 애플리케이션 실행
```bash
cd cmc

# Gradle 래퍼 권한 설정 (Linux/Mac)
chmod +x gradlew

# 의존성 설치 및 실행
./gradlew bootRun
```

#### 3. 환경 설정 파일 생성
`cmc/src/main/resources/secret.yml` 파일 생성:
```yaml
DEV:
  DATABASE:
    URL: jdbc:mysql://localhost:3310/commerce?useSSL=false&serverTimezone=UTC
    USERNAME: root
    PASSWORD: root
  KAKAO:
    PAY:
      CID: 'TC0ONETIME'
      ADMIN-KEY: 'test_admin_key'
      APPROVAL-URL: 'http://localhost:8080/v1/payments/success'
      CANCEL-URL: 'http://localhost:8080/v1/payments/cancel'
      FAIL-URL: 'http://localhost:8080/v1/payments/fail'
```

### 프론트엔드 설정

#### 1. 의존성 설치
```bash
cd frontend
npm install
```

#### 2. 개발 서버 실행
```bash
npm run dev
```

#### 3. 빌드 (프로덕션용)
```bash
npm run build
npm run preview
```

## 🐳 Docker를 이용한 실행

### 전체 시스템 실행
```bash
# 모든 서비스 실행
docker-compose up -d

# 로그 확인
docker-compose logs -f

# 서비스 상태 확인
docker-compose ps
```

### 개별 서비스 실행
```bash
# MySQL만 실행
docker-compose up -d mysql

# 백엔드만 실행
docker-compose up -d cmc

# 프론트엔드만 실행
docker-compose up -d frontend
```

### 서비스 중지
```bash
# 모든 서비스 중지
docker-compose down

# 데이터베이스 데이터 유지하며 중지
docker-compose down --volumes
```

## 🔍 문제 해결

### 일반적인 문제들

#### 1. 포트 충돌
```bash
# 포트 사용 확인
lsof -i :8080  # 백엔드 포트
lsof -i :3000  # 프론트엔드 포트
lsof -i :3310  # MySQL 포트

# 충돌하는 프로세스 종료
kill -9 <PID>
```

#### 2. 데이터베이스 연결 실패
```bash
# MySQL 컨테이너 상태 확인
docker-compose ps mysql

# MySQL 로그 확인
docker-compose logs mysql

# MySQL 재시작
docker-compose restart mysql
```

#### 3. Node.js 의존성 문제
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

#### 4. Java/Gradle 문제
```bash
cd cmc
./gradlew clean
./gradlew build
```

### 로그 확인

#### 백엔드 로그
```bash
# 실시간 로그 확인
docker-compose logs -f cmc

# 또는 직접 실행 시
cd cmc
./gradlew bootRun
```

#### 프론트엔드 로그
```bash
# 개발 서버 로그
cd frontend
npm run dev
```

#### 데이터베이스 로그
```bash
docker-compose logs -f mysql
```

## 🧪 테스트

### 백엔드 테스트
```bash
cd cmc
./gradlew test
```

### 프론트엔드 테스트
```bash
cd frontend
npm test
```

### API 테스트
1. Swagger UI 접속: http://localhost:8080/swagger-ui.html
2. Postman 컬렉션 사용: `docs/postman-collection.json`

## 📊 모니터링

### 시스템 상태 확인
```bash
# Docker 컨테이너 상태
docker-compose ps

# 리소스 사용량
docker stats

# 로그 확인
docker-compose logs
```

### 애플리케이션 상태
- **백엔드**: http://localhost:8080/actuator/health
- **프론트엔드**: http://localhost:3000
- **데이터베이스**: MySQL Workbench 또는 phpMyAdmin

## 🔄 개발 워크플로우

### 1. 개발 시작
```bash
# 백엔드 개발
cd cmc
./gradlew bootRun

# 새 터미널에서 프론트엔드 개발
cd frontend
npm run dev
```

### 2. 코드 수정
- 백엔드: Java 파일 수정 시 자동 재시작
- 프론트엔드: React 파일 수정 시 자동 새로고침

### 3. 빌드 및 배포
```bash
# 백엔드 빌드
cd cmc
./gradlew build

# 프론트엔드 빌드
cd frontend
npm run build
```

## 🚀 프로덕션 배포

### Docker 배포
```bash
# 프로덕션 빌드
docker-compose -f docker-compose.prod.yml build

# 프로덕션 실행
docker-compose -f docker-compose.prod.yml up -d
```

### 수동 배포
```bash
# 백엔드 JAR 파일 생성
cd cmc
./gradlew build
java -jar build/libs/cmc-0.0.1-SNAPSHOT.jar

# 프론트엔드 빌드
cd frontend
npm run build
# dist 폴더를 웹 서버에 업로드
```

## 📚 추가 리소스

- [프로젝트 README](README.md)
- [API 문서](docs/commerce-api.yaml)
- [ERD 문서](docs/commerce-ERD.md)
- [프론트엔드 가이드](frontend/README.md)

## 🆘 지원

문제가 발생하면 다음을 확인하세요:

1. **로그 확인**: 각 서비스의 로그를 확인
2. **문서 참조**: 관련 문서 및 README 파일 확인
3. **이슈 등록**: GitHub Issues에 문제 등록

---

**Commerce** - 최고의 쇼핑 경험을 제공하는 온라인 쇼핑몰 