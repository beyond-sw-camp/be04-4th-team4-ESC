# ESC (English Study Club) 

### 로고
  ![Group 63](https://github.com/dongh810/ESC-English_Study_Club-develop/assets/105986200/33f78c72-f09c-435d-a057-e756ffa259c8)

---

### 팀명: HighFives(Team HighFives)
### 팀원

- 팀원 : **백동현**
[<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/dongh810)

- 팀원 : **이예원**
[<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">]

- 팀원 : **곽고은**
[<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">]

- 팀원 : **정우진**
[<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">]

- 팀원 : **정태원**
[<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/t4e1)

---

# 1. 프로젝트 개요

ESC(English Study Club) 프로젝트는 

# 2. 프로젝트 관리 

 ESC 프로젝트의 산출 결과물은 다음과 같습니다.
- **WBS** : 프로젝트 기간별 진행 과제를 정리한 문서
- **요구사항 명세서** : 프로젝트의 요구사항을 정리하여 기능별로 분류해 둔 문서
- **데이터베이스 모델링** : 프로젝트에 사용된 DB의 논리/물리 모델링 이미지
- **Context Map** : DDD 방식 프로젝트 설계에 사용한 Conetext Map 이미지    
- **시스템 아키텍쳐** : 프로젝트의 전체 시스템 아키텍쳐 이미지
- **Devops 빌드/배포문서** :  CICD 구성을 위한 빌드 및 배포 방식을 설명하는 문서   
- **테스트 케이스 정의서** : 요구사항에 해당하는 테스트 케이스를 보여주는 문서 
- **테스트 결과** : 테스트 실행 결과 이미지  

## 추진 계획
| 구분 | 조직 | 주요 역할 |
| --- | --- | --- |
| 주관 | HighFives (Team HighFives) | CICD 프로젝트 |
| 지원기관 | 한화시스템x엔코아 | 장비 지원 및 퀄리티 점검 |

**프로젝트 일정** : 2024-04-01 ~ 2024-04-17

**작업 공간** : 서울 동작구 보라매로 SFC빌딩, 원격 소통

**기술 스택**   
|Vue 3.0|JavaScript|Spring Boot|Spring Data JPA|Docker|Kubernetes|Jenkins|Prometheus|Grafana|
|---|---|---|---|---|---|---|---|---|
|<img src="https://github.com/beyond-sw-camp/be04-3rd-TeamPhoenix-ahub/blob/feature-post-check/img/Vue.png" height="60" />|<img src="https://github.com/beyond-sw-camp/be04-3rd-TeamPhoenix-ahub/blob/feature-post-check/img/js.png" height="80" />|![icons8-봄-로고-48](https://github.com/dongh810/ESC-English_Study_Club-develop/assets/105986200/434f3918-640f-4cef-8050-43ca415d6d4c)|![다운로드__1_-removebg-preview](https://github.com/dongh810/ESC-English_Study_Club-develop/assets/105986200/82d8f3e5-9b6d-481b-92e3-0ff4a691b01b)|![icons8-부두-노동자-96](https://github.com/dongh810/ESC-English_Study_Club-develop/assets/105986200/547c5002-b2bb-49b7-afc8-f083846a0769)|![icons8-쿠버네티스-96](https://github.com/dongh810/ESC-English_Study_Club-develop/assets/105986200/08d3f8ed-6755-4e79-aba2-8680a63dfa52)|![icons8-젠킨스-96](https://github.com/dongh810/ESC-English_Study_Club-develop/assets/105986200/50215ec0-8598-4f6c-869a-2e795d69950f)|![icons8-프로메테우스-앱-96](https://github.com/dongh810/ESC-English_Study_Club-develop/assets/105986200/b146f025-eed4-4af1-9db8-101cad3f78b6)|![icons8-그라파나-48](https://github.com/dongh810/ESC-English_Study_Club-develop/assets/105986200/8afb44c6-75e1-4ca5-8072-2686600fdec9)|


<br>

## 2-1. WBS


## 2-2. 요구사항 명세서


<br>

## 2-3. DB 모델링

### 2-3-1. 논리 모델링

### 2-3-2. 물리 모델링

## 2-4. Context Map 

## 2-5. 시스템 아키텍쳐

- **Jenkins** <br>
테스트 코드 실행, Docker Image 빌드, Dockerhub 업로드 

- **Dockerhub** <br>
Jenkins가 생성한 최신화된 Docker image 저장 

- **Kubernetes** <br>
Dockerhub의 이미지를 사용, Spring boot & Prometheus의 컨테이너와 파드 관리

- **Prometheus** <br>
Spring boot 서버 Metric 수집, 서버 모니터링

- **Grafana** <br>
Prometheus에서 수집한 Metric과 Log를 시각화하여 제공

## 2-6. CICD 빌드/배포 
- **CICD 프로세스**

  ![image](https://github.com/beyond-sw-camp/be04-4th-team4-ESC/assets/108782390/1ce2f683-333c-4fc0-9310-d0671ee7843a)

<br>

<details>
<summary>Dockerfile</summary>

- Docker 이미지 생성에 사용 될 Docker 파일 생성


</details>

<details>
<summary>Github</summary>

- Webhook & Deploy key 생성

  지정한 브랜치에 변화가 있을 경우 Webhook을 발생, Jenkins 로 Webhook 을 보낼 수 있다. <br>
Deploy key 는 Jenkins 서버의 public Key 를 사용, Jenkins 서버로 SFTP로 변경된 코드를 전송한다.


</details>

<details>
<summary>Jenkins</summary>



</details>

<details>
<summary>Kubernetes</summary>

- Webhook & Deploy key 생성


</details>



## 2-7. 테스트 케이스 & 결과 

<details>
<summary>테스트1 결과</summary>

</details>

<details>
<summary>테스트2 결과</summary>

</details>

<br>

# 3. 고도화 계획

## 로그인 기능


## 채팅 기능 
- 1:1 채팅방 구현
- 채팅 메세지 백엔드 처리
- 채팅방 입장/퇴장 메세지 추가

## 기타

<br>

# 4. 프로젝트 회고

| 이름 | 내용 |
| ----- | ----------|
|    백동현   |  |
| 곽고은 |  |
| 이예원 | |
|    정우진    |  |
| 정태원 |  |
