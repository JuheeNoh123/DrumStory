# 🎵 드럼 학원 연습실 예약 시스템

**드럼 학원의 연습실 예약을 편리하게 관리할 수 있는 웹 애플리케이션입니다.**  
기존 오프라인 예약 방식의 불편함을 개선하고, 웹에서 예약 현황을 확인하고 예약할 수 있도록 제작하였습니다.

---

## 🚀 프로젝트 개요

### ✅ 기존 불편했던 점
1. 학원 내 태블릿에서만 예약 가능하여, 미리 예약 현황을 확인할 수 없음.
2. 연습실 이용 종료를 직접 알람을 맞춰야 해서 불편함.
3. 연습실이 모두 차 있을 경우, 언제 빈 자리가 생길지 알 수 없음.

### 🎯 개선된 기능
- 웹에서 실시간 예약 현황 확인 가능
- 예약 시간 자동 알림 기능 추가
- 예약이 모두 찬 경우, 다음 이용 가능 시간 안내

---

## 📌 주요 기능

### 🔹 일반 사용자
- **메인 페이지**: 오늘 날짜 예약 타임테이블 조회
- **예약**: 회원 ID 입력 후 예약 진행 (최대 한 시간만 가능)
- **퇴실**: 예약 내역이 있는 경우, 조기 퇴실 가능

### 🔹 관리자
- **회원 관리**: 회원 조회, 수정, 삭제 및 추가 가능
- **예약 관리**: 날짜별 예약 내역 조회 및 강제 삭제(강제 퇴실, 예약 취소)
- **관리자 계정 관리**: ID/PW 변경 가능

---
## 🛠 기술 스택
### ✔️Backend
<img src="https://img.shields.io/badge/Springboot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/><img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/><img src="https://img.shields.io/badge/springsecurity-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"><img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=amazonaws&logoColor=white"/>
### ✔️Frontend
<img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=black"/><img src="https://img.shields.io/badge/Typescript-3178C6?style=flat-square&logo=Typescript&logoColor=white"/><img src="https://img.shields.io/badge/Tailwind CSS-06B6D4?style=flat-square&logo=Tailwind CSS&logoColor=white"/>

### ✔️API 문서화
<img src="https://img.shields.io/badge/swagger-85EA2D?style=flat-square&logo=swagger&logoColor=white">  

### ✔️디자인 기획
[<img src="https://img.shields.io/badge/figma-F24E1E?style=flat-square&logo=figma&logoColor=white">](https://www.figma.com/your-figma-link)  
디자인 보러 가기 👉 [Figma 링크](https://www.figma.com/design/oWFyMhrxptTOvngFMnDeP9/%EB%93%9C%EB%9F%BC%EC%8A%A4%ED%86%A0%EB%A6%AC?node-id=20-26&p=f&t=sp2l2h5bArvuN271-0)


---

## 🔒 보안 및 예외 처리

### 🔐 인증 및 보안
- **Spring Security**를 사용하여 사용자 인증 및 인가를 적용
- **JWT(Json Web Token)** 기반 로그인 및 세션 관리 (Access Token 사용)
- **보안 헤더 설정**: HTTP 응답 헤더에서 보안 강화를 위한 설정 적용
- **관리자 권한 분리**: 일반 사용자와 관리자의 권한을 구분하여 접근 제한

### ⚠ 예외 처리
- **글로벌 예외 처리** (`@ControllerAdvice` 활용)로 일관된 예외 응답 제공
- **JWT 인증 예외 처리**: 유효하지 않은 토큰에 대한 예외 처리
- **입력 데이터 검증**: 예약 요청 시 필수 입력값 누락 및 형식 검증 적용
