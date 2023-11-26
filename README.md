# 목차

1. [Instruction](#1-instruction)
2. [Architecture](#2-architecture)
3. [Performance Test Report](#3-performance-test-report)
4. [Contributors](#4-contributors)

# 1. Instruction

<img src="https://github.com/f-lab-clone/ticketing-backend/assets/41976906/b728aab9-d2ce-41bd-a448-c5c181b61453"  width="70%" height="70%"/>

<br/> 

성능 테스트를 위한 티켓팅 서비스 구현
- [Backend](https://github.com/f-lab-clone/ticketing-backend) Kotlin, Spring-boot, Testcontainers, MySQL
- [Infra](https://github.com/f-lab-clone/ticketing-infra) AWS, EKS, Terraform, Helm, Argocd, Prometheus, Grafana 
- [Queue System](https://github.com/f-lab-clone/queuing-system) Node.js, Redis
- K6를 활용한 Spike Test 수행
- Prometheus 및 Grafana를 통한 모니터링 및 개선사항 보고서 작성


# 2. Architecture

## Infra
 
![image](https://github.com/f-lab-clone/ticketing-backend/assets/41976906/354a8f92-852c-4cd1-8713-de05e8aa83f0)

[Terraform을 통해 전체 배포 환경 구현](https://github.com/f-lab-clone/ticketing-infra/issues/1)
- 비용 절감을 위해 [AWS ALB 삭제 후 Nginx Ingress(Baremetal) 구축](https://github.com/f-lab-clone/ticketing-infra/issues/42)
- [How to scrape metrics from multiple pods using spring-actuator](https://medium.com/@hayounglim/prometheus-helm-how-to-scrape-metrics-from-multiple-pods-using-spring-actuator-08fccd0cf69e)
- [Terraform으로 EKS Pod에 Secret 주입](https://devkly.com/devops/terraform-secret-manager/)
- [[발표자료] Desired State를 중심으로 알아보는 인프라 환경](https://github.com/f-lab-clone/ticketing-backend/wiki/Desired-State%EB%A5%BC-%EC%A4%91%EC%8B%AC%EC%9C%BC%EB%A1%9C-%EC%82%B4%ED%8E%B4%EB%B3%B4%EB%8A%94-%EC%9D%B8%ED%94%84%EB%9D%BC-%ED%99%98%EA%B2%BD)



> NAT Gateway 비용 이슈로 [Public Subnet Node Group](https://github.com/f-lab-clone/ticketing-infra/issues/61#issuecomment-1748931936) 구성 사용

> ALB 비용 이슈로 인해 [ALB 삭제 후 Nginx Ingress(Baremetal) 구축](https://github.com/f-lab-clone/ticketing-infra/issues/42#issue-1878969608)

## Performance Test 

![result](https://github.com/f-lab-clone/ticketing-backend/assets/41976906/5cc5b165-fdde-4b67-968f-b94dfc037cfd)


[성능 테스트 환경 구축에 대한 고민](https://github.com/f-lab-clone/ticketing-infra/issues/32) (테스트 도구, 성능테스트 자동화, 성능테스트 부하발생 환경, 테스트 결과 출력 및 분석)
- [성능테스트 시나리오 상세 설명](https://github.com/f-lab-clone/ticketing-infra/wiki/Desired-State%EB%A5%BC-%EC%A4%91%EC%8B%AC%EC%9C%BC%EB%A1%9C-%EC%82%B4%ED%8E%B4%EB%B3%B4%EB%8A%94-%EC%9D%B8%ED%94%84%EB%9D%BC-%ED%99%98%EA%B2%BD#%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%84%B1%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8-%ED%99%98%EA%B2%BD%EC%9D%84-%EA%B5%AC%EC%84%B1%ED%96%88%EB%8A%94%EA%B0%80)
- [ALB LCU를 이용한 SpikeTest 요금 계산](https://github.com/f-lab-clone/ticketing-infra/issues/62)
- [성능테스트 환경 구축을 위한 고민](https://github.com/f-lab-clone/ticketing-infra/issues/32)
- [성능테스트 K6 스크립트 작성](https://github.com/f-lab-clone/ticketing-backend/issues/83)
- [테스트용 생성 및 대용량 데이터 DB Insert](https://github.com/f-lab-clone/ticketing-backend/issues/101) 
- [Prometheus, Grafana를 이용한 모니터링 환경 구축](https://github.com/f-lab-clone/ticketing-infra/issues/30)



## 대기열 시스템

<img width="1474" alt="image" src="https://github.com/f-lab-clone/ticketing-backend/assets/41976906/37d47dc4-c795-437e-afb8-c13957f2c3b6">

[대기열 시스템 설계 이슈](https://github.com/f-lab-clone/ticketing-backend/issues/72#issuecomment-1763249911)
- 갱신 누락 고려, Non-blocking API, 대기열 데이터 구조 등

백엔드 설계
- [프로젝트 패키지 구조 고민](https://github.com/f-lab-clone/ticketing-backend/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%8C%A8%ED%82%A4%EC%A7%80-%EA%B5%AC%EC%A1%B0)
- [Convention Docs](https://github.com/f-lab-clone/ticketing-backend/wiki/Convention) (Branch, CommitMsg, Naming, HTTP Response, Serialization, Test 등 정의)
- Time 컨벤션, 레이어간 데이터(에러, 응답 등) 전달 형식, 로깅 등의 Best Practice 고민/구현을 담은 [API 고도화](https://github.com/f-lab-clone/ticketing-backend/issues/52)
- [jacoco + codecov를 이용한 테스트 커버리지 80% 이상 유지](https://github.com/f-lab-clone/ticketing-backend/issues/5)
- [testcontainer를 통해 MySQL Container 도입 통합테스트 환경 구축](https://github.com/f-lab-clone/ticketing-backend/issues/31)


## CD Pipeline

<img width="1085" alt="image" src="https://github.com/f-lab-clone/ticketing-backend/assets/41976906/00651cfb-8e03-4857-bc3b-14a400c84cbe">


# 3. Performance Test Report 

[`SignIn` Spike Test 보고서](https://github.com/f-lab-clone/ticketing-backend/issues/105)
- 100만건 데이터 단일 컬럼에 대한 인덱스 추가 후 Slow Query 개선
- 암호화 CPU 성능 이슈: CPU 코어수 증가 및 [암호화 난이도 조절](https://github.com/f-lab-clone/ticketing-backend/issues/107)에 따른 변화 관찰

[JVM Warm Up Test 보고서](https://github.com/f-lab-clone/ticketing-backend/issues/108)
- 프로세스 생성 후 동일 테스트를 반복함으로 JVM CodeHeap 및 성능 변화 관찰

[인터파크 트래픽 0.001% Spike Test 보고서](https://github.com/f-lab-clone/ticketing-backend/issues/135)
- 천만건 데이터 `SELECT COUNT(*)`를 [`NoOffset` 구현](https://github.com/f-lab-clone/ticketing-backend/issues/113)으로 개선
- 하나의 자원(=Event)에 대한 Lock 경쟁 발생에 대한 고민 및 [대기열 시스템](https://github.com/f-lab-clone/ticketing-backend/issues/72) 도입 후 테스트

[인터파크 트래픽 0.002% Spike Test 보고서](https://github.com/f-lab-clone/ticketing-backend/issues/144)
- Thread Pool 전략 수정으로 쓰레드 생성에 따른 CPU 리소스 사용 개선
- DB Connection Pool 전략 수정으로 Pending Connection 개선
- Redis 캐싱 적용으로 Latency 개선

# 4. Contributors


진행 기간 : 2023.07.01 ~ 2023.11.19 ([회의록](https://github.com/f-lab-clone/ticketing-backend/wiki/%ED%9A%8C%EC%9D%98%EB%A1%9D))
> 2023.07.01 ~ 2023.09.30 (집중기간)

<br/>

| 이름 | 역할 | Github |
| ---- | ---- | ---- |
|  안준하    |   Project Leader / Infra  | [junha-ahn](https://github.com/junha-ahn) |
| 임하영     |  Infra     | [hihahayoung](https://github.com/hihahayoung) |
|  박정섭    |  Backend     | [ParkJeongseop](https://github.com/ParkJeongseop) |
|  김민준    |   Backend   | [minjun3021](https://github.com/minjun3021) |
> 하차자 제외 

> [프로젝트 시작 일기](https://devkly.com/essay/f-lab-clone-start/)를 보면 알 수 있듯, 모두 **현역 군인** 신분으로 프로젝트를 진행했나갔습니다.
