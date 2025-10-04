# KTB_community

### david(이도연)/클라우드 4주차 과제: **커뮤니티 서버 만들기**

---

## 🚀 소개

* 커뮤니티 백엔드 서버
* 회원가입/로그인(JWT), 마이페이지, 게시글/댓글 CRUD, 좋아요, 이미지 업로드(Pre‑Signed URL) 지원
* 무한스크롤(No‑Offset) 기반 목록 조회

---

## 🧰 기술 스택

* **Java 21**, **Spring Boot 3.56**
* Spring Web, Spring Validation, Spring Data JPA, Spring Security
* **JWT**(Access/Refresh) – 쿠키 발급
* **QueryDSL** – 동적 조회
* **MySQL** (InnoDB), **Redis** (토큰), **S3** (이미지 저장)
* Gradle, Docker 

---

## 📁 프로젝트 구조

```
project
├─ global/              # 공통 예외, 응답 래퍼, 설정
├─ security/            # JWT, 인증/인가 필터, 핸들러
├─ platform/
│  ├─ user/             # 유저 도메인 (회원가입/로그인/마이페이지)
│  ├─ posts/            # 게시글 도메인
│  ├─ comments/         # 댓글 도메인
│  └─ images/           # 이미지(Pre‑Signed URL 발급)
└─ ...
```

---

## 🔐 인증/보안
* 로그인 성공 시 Access/Refresh Token을 쿠키로 발급 (HttpOnly 권장)
* 요청 시 JWT 검증 필터가 토큰을 확인하여 인증
* 특정 엔드포인트는 인증 필요

---

## 🖼️ 이미지 업로드 정책
* 클라이언트가 이미지 파일명을 보내면 서버가 Pre‑Signed URL 반환
* 클라이언트는 해당 URL로 S3에 직접 업로드

---
## 🔁 무한스크롤 규칙
* 리스트 API는 lastId, size 쿼리 파라미터를 사용
