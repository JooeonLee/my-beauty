# ====== 1. 빌드 단계 (Gradle 사용) ======
FROM gradle:8.5-jdk17 AS builder

# 작업 디렉터리 설정
WORKDIR /app

# 프로젝트 파일 복사
COPY --chown=gradle:gradle . .

# 의존성 미리 다운로드 (캐시 최적화)
RUN gradle dependencies --no-daemon

# Spring Boot 애플리케이션 빌드 (JAR 생성)
RUN gradle bootJar --no-daemon

# ====== 2. 실행 단계 ======
FROM openjdk:17-jdk-slim

# 작업 디렉터리 설정
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 환경 변수 설정 (실행 시 적용)
ENV SPRING_PROFILES_ACTIVE=local

# 컨테이너 실행 시 명령어
CMD ["java", "-jar", "app.jar"]
