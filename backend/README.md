# 바닷마을 다이어리 Back-end

## 개발 환경
- **Back-end**
    - **IDE**: IntelliJ
    - **JDK**: 11
    - **Spring Boot**: 2.5.2
    - **RestAPI**: Spring REST Docs
    - **Testing**: JUnit5, Mockito
    - **Build Tool**: Gradle
    - **Authorization**: Spring Security, JWT
    - **DB access**: JPA, QueryDSL
    - **DB 형상관리**: Flyway
    - **CI/CD**: Jenkins
    - **Web Socket**: SockJS, STOMP
- **Nginx**
  - **Web Server and Spring Boot Application port**: 443 (HTTPS)
    - 정적 리소스(웹서버) url: / → response index.html
    - api url: /api/* → localhost:8080(스프링부트)로 리버스 프록시
- **OpenVidu(WebRTC)**
    - **version**: OpenVidu CE 2.22.0
    - **openvidu server port**: 5443
- **DB**
    - **MySQL**
        - version: 8.0.29
        - port: 3306
    - **Redis**
        - port: 6379
## 주요 기능
### 소셜 로그인
  - 네이버, 카카오 로그인 API 사용하여 소셜 로그인 구현
  - Spring Security JWT 기반의 access-token, refresh-token을 통한 유저 인증, 인가 관리
### WebRTC 기반 화상 미팅
  - OpenVidu 를 사용하여 WebRTC 화상 미팅 구현
### Redis를 이용한 방 및 참가자 상태 저장
  - 자주 닫히고 생성 및 조회 되는 방 과 참가자들의 상태를 RDB가 아닌 key-value 기반의 인메모리 데이터 베이스, Redis에 저장
  - 자주 수정, 변경 그리고 조회되는 데이터이므로 Redis를 통해 성능 향상 가능
### 이미지 파일 업로드 & 다운로드
  - AWS S3를 사용한 이미지 업로드 및 다운로드 구현
  - Public 이미지 자산의 직접적인 경로가 아닌 스프링 부트 애플리케이션을 통해 다운로드하여  
    이미지 파일 경로 추상화
### 방 내 게임 기능을 위한 웹소켓
  - SockJS, STOMP를 사용하여 웹소켓 구현
  - 라이어게임의 키워드와 라이어 그리고 콜마이네임의 정답을 브로드 캐스팅
### 다이어리 및 스탬프 관리
  - 소셜로그인을 통해 회원가입한 유저는 자신이 참여한 테마의 방에서 입장 시간과 공부시간을 다이어리를 통해 스탬프 형태로 기록 및 꾸밀 수 있음
### Spring REST Docs 사용한 테스트 및 API 문서 작성 자동화
  - 제공하는 모든 API들에 대해 컨트롤러 테스트를 수행하여 생성된 asciidoc 템플릿으로 API 문서 작성 및 `https://배포도메인/docs` URL을 통해 프론트엔드에서 직접 참고하며 개발 할 수 있게 하였음
## 프로젝트 구조도
```text
.
├── docs
│   └── asciidoc
├── main
│   ├── java
│   │   └── com
│   │       └── oceandiary
│   │           └── api
│   │               ├── common
│   │               │   ├── advice
│   │               │   ├── category
│   │               │   ├── entity
│   │               │   ├── exception
│   │               │   └── utils
│   │               ├── config
│   │               │   ├── jpa
│   │               │   └── security
│   │               ├── diary
│   │               │   ├── controller
│   │               │   ├── dto
│   │               │   ├── entity
│   │               │   ├── repository
│   │               │   └── service
│   │               ├── file
│   │               │   ├── controller
│   │               │   ├── dto
│   │               │   ├── entity
│   │               │   ├── exception
│   │               │   ├── repository
│   │               │   └── service
│   │               ├── room
│   │               │   ├── controller
│   │               │   ├── dto
│   │               │   ├── entity
│   │               │   ├── exception
│   │               │   ├── repository
│   │               │   └── service
│   │               ├── user
│   │               │   ├── controller
│   │               │   ├── domain
│   │               │   ├── dto
│   │               │   ├── entity
│   │               │   ├── exception
│   │               │   ├── repository
│   │               │   ├── security
│   │               │   │   ├── token
│   │               │   │   └── userdetails
│   │               │   └── service
│   │               └── websocket
│   │                   ├── config
│   │                   ├── controller
│   │                   └── dto
│   └── resources
│       ├── db
│       │   └── migration
│       └── dummy
│           └── image
├── rest
└── test
    ├── java
    │   └── com
    │       └── oceandiary
    │           └── api
    │               ├── diary
    │               │   └── controller
    │               ├── room
    │               │   └── controller
    │               └── user
    │                   └── controller
    └── resources

```