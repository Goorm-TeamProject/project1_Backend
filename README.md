# 클라우드 퍼포먼스 최적화 프로젝트 

## 프로젝트 개요

* **기간:** 2025.03.21 ~ 2025.04.17
* **참여 인원:** 5명 (BE 3, FE 2)
* **주제:** 클라우드 기반 트랜잭션 서비스 및 오류 대응 시스템 구현

## 🎯 목표 및 전략

* Spring Boot + MySQL 기반 트랜잭션 API 구현 (JDBC + JPA 병행)
* EC2 + ECS + S3 + CloudFront 기반 AWS 인프라 전반 실습
* 테스트 기반 개발(TDD), 수동 Docker 배포 및 수동 점검

## 🔧 주요 기술 스택 및 인프라

* **백엔드:** Java, Spring Boot, MySQL, JDBC + JPA, JWT, Docker
* **프론트엔드:** React (TS), Axios, S3 + CloudFront
* **인프라:** AWS EC2, ECS(EC2 타입), ALB, VPC, Route53, OpenTelemetry
* **배포:** 수동 Docker 빌드 + ECR 업로드 → ECS(EC2) 수동 배포

## 📊 성과 요약

| 항목                  | 결과                                                                    |
| ------------------- | ---------------------------------------------------------------------   |
| ⚙️ **API 평균 응답 시간** | **80ms** 유지 (CloudFront → ALB 이중 리버스 프록시 경유 + t2.micro 서버 구성)  |
| 💬 **테스트 커버리지**     | 단위/통합 테스트 적용 + Swagger 문서 기반 검증 완료                            |
| 🐳 **컨테이너화**        | Dockerfile + Compose 구성 / ECR 업로드 및 ECS 배포 완료                     |
| 🧪 **동시성 처리**       | Race Condition 발생 → 비관적 락 적용으로 개선 예정                            |
| ☁️ **오토스케일링**       | ECS Auto Scaling 적용 / CPU 기반 1\~2대 자동 확장                          |
| 🔐 **보안 구성**        | HTTPS + CloudFront + ALB + VPC 적용 / WAF는 미적용 상태                     |
| 🛠 **모니터링 도입**      | OpenTelemetry → CloudWatch 로그 연동 완료                                  |
| 🧪 **부하 테스트 결과**    | t2.micro DB 환경에서도 1,000건 이상의 트랜잭션 부하 테스트 성공적으로 처리           |
| 🚀 무중단 배포 경험	        ECS 롤링 업데이트 방식으로 30회 이상 무중단 배포 완료                              |
|                                                                                                 |


## 시스템 아키텍처
<img width="1016" alt="image" src="https://github.com/user-attachments/assets/85081441-9566-45c6-872f-dfe1fe484186" />

