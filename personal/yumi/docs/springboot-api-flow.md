# 스프링부트 API 개발 흐름 정리 (배포 섹션 전까지 강의 정리)

---

# API와 HTTP

클라이언트와 서버는 HTTP를 통해 통신한다.

예를 들어:

```http
GET /user
POST /user
```

같은 요청을 서버에 보내게 된다.

그리고 서버와 클라이언트는 보통 JSON 형태로 데이터를 주고받는다.

예시:

```json
{
  "name": "yumi",
  "age": 23
}
```

Spring Boot에서는 이런 요청을 Controller가 받는다.

---

# Controller

Spring Boot에서 API 요청은 Controller가 담당한다.

```java
@RestController
public class UserController {
}
```

Controller의 역할은 다음과 같다.

- HTTP 요청 받기
- JSON → 객체 변환
- 응답 반환

즉 API의 입구 역할을 한다.

초반에는 Controller 하나에서 모든 것을 처리했지만, 점점 문제점이 발생했다.

---

# Database가 필요한 이유

처음에는 데이터를 메모리에 저장할 수 있다.

하지만 서버가 종료되면 메모리에 저장된 데이터는 모두 사라진다.

즉:

```text
RAM 데이터는 휘발된다.
```

따라서 데이터를 영구적으로 저장하기 위해 Database가 필요하다.

이번 학습에서는 MySQL을 사용했다.

---

# SQL과 MySQL

Spring Boot는 MySQL과 연결해 데이터를 저장할 수 있다.

데이터를 조회/수정하기 위해 SQL을 사용한다.

대표적인 SQL:

```sql
SELECT
INSERT
UPDATE
DELETE
```

초반에는 문자열 SQL을 직접 작성했다.

```java
String sql = "SELECT * FROM user";
```

하지만 여기에는 문제점이 많았다.

---

# 문자열 SQL의 문제점

문자열 SQL 방식은 다음과 같은 단점이 있다.

## 1. 오타 위험

컴파일 시점이 아니라 실행 시점에 오류가 발생한다.

---

## 2. 특정 DB에 종속적

MySQL 문법과 PostgreSQL 문법이 조금씩 다르다.

---

## 3. 반복 작업 증가

CRUD마다 SQL을 반복 작성해야 한다.

---

## 4. 객체지향과 맞지 않음

Java 객체 구조와 DB 테이블 구조는 다르다.

---

# 역할 분리의 필요성

처음에는 Controller 하나에서 다음 역할을 모두 수행했다.

- API 처리
- 예외 처리
- SQL 처리

하지만 이런 구조는 유지보수가 매우 어렵다.

코드가 길어질수록:

- 수정이 어려워지고
- 테스트가 어려워지고
- 협업이 어려워진다.

그래서 역할을 분리하게 된다.

---

# Controller / Service / Repository 구조

Spring Boot에서는 보통 아래 구조를 사용한다.

```text
Controller
↓
Service
↓
Repository
```

각각 역할이 다르다.

---

## Controller

HTTP 요청/응답 담당

즉 API의 입구 역할이다.

---

## Service

비즈니스 로직 담당

예시:

- 중복 검사
- 예외 처리
- 대출 가능 여부 확인

같은 실제 서비스 로직을 처리한다.

---

## Repository

DB 접근 담당

데이터 저장/조회 기능을 수행한다.

---

# Clean Code

강의에서 계속 강조했던 부분 중 하나가 Clean Code였다.

좋은 코드는 단순히 예쁜 코드가 아니라:

- 읽기 쉽고
- 수정하기 쉽고
- 유지보수하기 쉬운 코드

를 의미한다.

코드는 한 번 작성하고 끝나는 것이 아니라 계속 수정된다.

따라서 코드 구조가 매우 중요하다.

---

# Spring Container와 Bean

Spring은 객체를 직접 생성하고 관리한다.

예를 들어:

```java
@Service
@Repository
```

를 붙이면 Spring이 객체를 자동으로 생성한다.

이 객체를 Spring Bean이라고 부른다.

즉 개발자가 직접 new를 남발하지 않고, Spring이 객체 생명주기를 관리하게 된다.

이것이 Spring의 핵심 특징 중 하나이다.

---

# JPA 등장

문자열 SQL의 문제를 해결하기 위해 JPA를 사용한다.

JPA는:

```text
객체와 DB 테이블을 매핑하는 기술
```

이다.

즉 Java 객체와 Database 테이블을 연결해준다.

예를 들어:

```java
User 객체
```

와

```sql
user 테이블
```

을 연결한다.

---

# Entity

JPA에서는 DB 테이블과 연결되는 객체를 Entity라고 한다.

```java
@Entity
public class User {
}
```

이렇게 작성한다.

이제 개발자는 SQL 중심이 아니라 객체 중심으로 개발할 수 있다.

기존:

```java
jdbcTemplate.update(...)
```

이후:

```java
userRepository.save(user);
```

처럼 훨씬 간단하게 데이터를 저장할 수 있다.

---

# Hibernate

JPA는 규칙이고, Hibernate는 그 규칙의 구현체이다.

구조는 다음과 같다.

```text
개발자 코드
↓
JPA
↓
Hibernate
↓
SQL
↓
MySQL
```

즉 Hibernate가 내부적으로 SQL을 생성해준다.

---

# 실제 API 개발 흐름

강의 후반부에서는 실제 도서관리 API를 개발했다.

예시 기능:

- 책 등록
- 책 대출
- 책 반납

실제 API 개발은 다음 순서로 진행됐다.

---

## 1. 요구사항 분석

예:

```text
책을 등록할 수 있어야 한다.
```

---

## 2. API 설계

```http
POST /book
```

---

## 3. DB 테이블 설계

```text
book
- id
- name
```

---

## 4. Entity 생성

```java
@Entity
public class Book
```

---

## 5. Repository 생성

```java
public interface BookRepository
```

---

## 6. DTO 생성

```java
BookCreateRequest
```

---

## 7. Service 작성

비즈니스 로직 구현

---

## 8. Controller 연결

API 완성

---

# DTO

DTO는 데이터 전달 객체이다.

클라이언트와 서버 사이에서 데이터를 주고받기 위해 사용한다.

DTO를 사용하는 이유는 다음과 같다.

- Entity 직접 노출 방지
- 필요한 데이터만 전달 가능
- 유지보수 편리
- 요청/응답 구조 분리 가능

---

# Transaction

Transaction은 여러 DB 작업을 하나로 묶는 개념이다.

예를 들어:

```text
책 대출
→ 대출 기록 저장
→ 책 상태 변경
```

중간에 하나라도 실패하면 전체 작업이 취소되어야 한다.

이런 상황에서 Transaction이 필요하다.

---

# 학습하면서 느낀 점

Spring Boot는 단순히 API를 만드는 프레임워크가 아니라, 좋은 구조를 만들도록 유도하는 프레임워크라는 느낌이 강했다.

특히:

- 역할 분리
- 객체지향
- 유지보수성
- 확장성

을 굉장히 중요하게 생각한다는 점이 인상적이었다.

또 단순히 “동작하는 코드”보다:

```text
왜 이렇게 구조를 나누는가
왜 객체 중심으로 개발하는가
```

를 이해하는 것이 훨씬 중요하다고 느꼈다.