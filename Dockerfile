# 1. Java 17 이미지 (x86 아키텍처용)
FROM openjdk:17-jdk-slim

# 2. 작업 디렉터리
WORKDIR /app

# 3. jar 파일 복사 (이름 명시적으로 지정!)
COPY build/libs/*.jar app.jar

# 4. 포트 오픈
EXPOSE 8080

# 5. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
