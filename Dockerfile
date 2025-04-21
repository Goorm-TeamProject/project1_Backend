# 1. 베이스 이미지 지정 (ECS 배포 호환 위해 플랫폼도 명시)
FROM --platform=linux/amd64 openjdk:17-jdk-slim

# 2. 작업 디렉터리
WORKDIR /app

# 3. JAR 파일 복사
COPY build/libs/*.jar app.jar

# 4. 포트 오픈
EXPOSE 8080

# 5. 애플리케이션 실행 (prod 프로파일 + 환경변수 주입 지원)
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
