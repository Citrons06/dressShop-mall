# 🛒DRESS SHOP(1인 프로젝트)
<hr />
DRESS SHOP SHOPPING MALL Web Application

Author: yeolee1212@gmail.com

## 🚍프로젝트 개요
### 프로젝트 소개
의류 상품을 판매하는 온라인 쇼핑몰 시스템입니다. 시중 쇼핑몰인 29CM 쇼핑몰을 모티브로 제작하였습니다.

사용자들은 이 플랫폼을 통해 다양한 카테고리의 상품을 탐색하고, 장바구니에 담아 주문할 수 있습니다.
관리자는 상품 및 카테고리, 회원, 주문 내역을 관리할 수 있어 쇼핑몰을 효율적으로 운영하도록 설계했습니다.

### 주제 선택 이유
쇼핑몰은 가장 일반적인 주제이지만, 사용자 인터페이스와 보안 및 트랜잭션 처리, 데이터베이스 관리 등 다양한 기술을 활용할 수 있는 주제입니다.
이 프로젝트를 통해 사용자와 관리자의 입장에서 웹 애플리케이션을 설계하고 개발하는 경험을 쌓고자 쇼핑몰을 선택했습니다.

***
## ⭐주요 기능
### 👨‍👨‍👧 사용자
- 회원가입 | 로그인 | 소셜 로그인(구글) | 주문 조회 및 취소

### 🎁 상품
- 상품 조회 | 장바구니 | 주문

### 🛠 관리자
- 상품 등록, 수정, 판매 상태 변경, 재고 관리 | 주문/배송 상태 변경

|  <small>시연 영상</small>   | 
|-------------------------|
| <small>추후 추가 예정</small> |

## 🌈 시스템 아키텍처
![아키텍처](https://github.com/Citrons06/dressShop-mall/assets/125535240/2c00921f-917e-4ab6-ad82-9e0a83815b30)

### ⚙ 기술 스택
OS | Windows 10
--- | --- |
Language | ![Java](https://img.shields.io/badge/JAVA-000?style=for-the-badge&logo=java&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-000?style=for-the-badge&logo=spring&logoColor=white) ![HTML5](https://img.shields.io/badge/html5-000?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-000?style=for-the-badge&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-000?style=for-the-badge&logo=javascript&logoColor=white)
IDE | ![InteliJ IDEA](https://img.shields.io/badge/InteliJ%20IDEA-000?style=for-the-badge&logo=intellijidea&logoColor=white)
Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
Build Tool | ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
Database | ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
Frontend | ![HTML5](https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
Library | ![springdatajpa](https://github.com/Citrons06/dressShop-mall/assets/125535240/2dc6a1f2-ad37-4d8c-889f-c295c5bb27ad) ![querydsl](https://github.com/Citrons06/dressShop-mall/assets/125535240/3e537e19-4dec-418a-9a50-7b636e9f0d7d) ![Spring Security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white) ![OAuth 2.0 Client](https://img.shields.io/badge/OAuth%202.0%20Client-4b4b4b?style=for-the-badge) ![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
API | ![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
Server |![Apache Tomcat 9.0](https://img.shields.io/badge/Apache%20Tomcat%20-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)
Version Control | ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)

### ✔ 기술적 의사결정
선택기술 | 선택이유 및 근거
--- | --- |
`Spring Data JPA` | 해당 프로젝트는 새로운 기능을 추가하면서 지속적으로 확장했던 애플리케이션임. 새로운 엔티티를 추가할 때마다 비즈니스 로직을 새로 작성할 필요 없이 쉽게 확장할 수 있도록 설계함.
`MySQL` | 엔티티끼리 관계를 맺고 있으며, 데이터의 일관성과 무결성을 유지하기 위해 관계형 데이터베이스를 사용함. 데이터 조회를 자주 하는 특성을 고려하여 사용
`JUnit5` | 주어진 조건에서(given) 코드가 실행됐을 때(when) 어떤 결과가 도출되는지(then) 모듈 단위 테스트를 통해 검증하고자 함. 의도대로 작동하는지 검증하면서 유지보수성을 향상시키고자 함.
`QueryDSL` | JPQL을 자바 코드로 작성할 수 있어 가독성이 좋음. 컴파일 시점에 오류를 잡을 수 있고 동적 쿼리를 작성할 수 있어 사용함.

### 📚ERD
![ERD](https://github.com/Citrons06/dressShop-mall/assets/125535240/8fbf6d8b-cf43-4c03-813e-1044a9fcb293)