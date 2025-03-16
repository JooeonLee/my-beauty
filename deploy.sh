#!/bin/bash

# 1. 기존 컨테이너 중지 및 삭제
echo "🛑 Stopping and removing existing container..."
docker stop mybeauty-app || true
docker rm mybeauty-app || true

# 2. 최신 코드 가져오기 (Jenkins가 이미 checkout 했다면 생략 가능)
echo "🔄 Pulling latest code..."
git pull origin main

# 3. Docker 이미지 빌드
echo "🐳 Building Docker image..."
docker build -t mybeauty-app .

# 4. 새 컨테이너 실행
echo "🚀 Deploying new container..."
docker run -d --name mybeauty-app \
    -p 8080:8080 \
    -e SPRING_PROFILES_ACTIVE=prod \
    -v /var/run/docker.sock:/var/run/docker.sock \
    mybeauty-app

echo "✅ Deployment completed!"