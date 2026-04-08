---
created: 2026-03-26
tags: [backend, java, django, jpa, orm]
status: seed
---

# JPA와 Django ORM 대응 개념

## 대응 표

| JPA | Django ORM | 설명 |
|---|---|---|
| `Entity` | `Model` | DB 테이블과 매핑되는 클래스 |
| `@Column` | `Field` (CharField 등) | 컬럼 정의 |
| `@Id` / `@GeneratedValue` | `AutoField` (자동) | PK |
| `EntityManager` | `Manager` (`Model.objects`) | DB 조작 진입점 |
| `Repository` | `QuerySet` | 데이터 조회/필터 인터페이스 |
| `JPQL` | `QuerySet API` | ORM 쿼리 언어 |
| `@OneToMany` | `ForeignKey` (역참조) | 1:N 관계 |
| `@ManyToOne` | `ForeignKey` | N:1 관계 |
| `@ManyToMany` | `ManyToManyField` | M:N 관계 |
| `@OneToOne` | `OneToOneField` | 1:1 관계 |
| `CascadeType` | `on_delete=` 옵션 | 연관 데이터 삭제 정책 |
| `fetch = LAZY` | `select_related` 없이 접근 | 지연 로딩 (기본값) |
| `fetch = EAGER` | `select_related()` / `prefetch_related()` | 즉시 로딩 |
| `@Transactional` | `@transaction.atomic` | 트랜잭션 관리 |
| `Persistence Context` (1차 캐시) | 없음 (QuerySet은 매번 DB 조회) | JPA만의 개념 |
| Migration (`Flyway`/`Liquibase`) | `makemigrations` + `migrate` | 스키마 버전 관리 |

## 핵심 차이점

**Persistence Context (영속성 컨텍스트)**
JPA는 엔티티를 메모리에 캐싱해 동일 트랜잭션 내 같은 PK 조회 시 DB를 다시 안 침. 
Django는 이 개념 없음 — QuerySet은 매번 DB 히트.

**Lazy Loading N+1**
JPA는 기본이 LAZY라 명시 안 하면 N+1 발생. 
Django도 기본적으로 lazy loading을 하기 때문에 마찬가지 — `select_related()` / `prefetch_related()` 안 쓰면 N+1. → [[ORM N+1 문제와 해결법]]

**Django QuerySet은 Lazy Evaluation**
Django QuerySet은 실제로 iterate하거나 `list()`, `len()` 호출 전까지 SQL 실행 안 함.
JPA도 `getResultList()` 호출 전까지 SQL을 실행하지 않으므로 같은 개념.

**QuerySet vs JPQL 쿼리 작성 방식**
Spring Data JPA의 `findBy~` 메서드는 체이닝처럼 보이지만, 내부적으로 메서드 이름을 파싱해 JPQL로 변환하는 것. 복잡한 쿼리는 `findBy~`로 표현이 안 돼 결국 `@Query`로 JPQL 문자열을 직접 써야 함.
Django QuerySet은 복잡한 쿼리도 일관되게 메서드 체이닝(`.filter().order_by()`)으로 작성할 수 있어 더 직관적.

 ### 레이어 정리
  Spring Data JPA (findBy~ 메서드)   ← 편의 레이어
          ↓ 변환
  JPQL                                ← JPA 쿼리 언어
          ↓ 변환
  Hibernate                           ← 실제 구현체
          ↓ 변환
  SQL                                 ← DB 실행
