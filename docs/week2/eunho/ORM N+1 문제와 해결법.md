---
created: 2026-03-26
tags: [backend, django, jpa, orm, performance]
status: seed
---

# ORM N+1 문제와 해결법

관련: [[JPA와 Django ORM 대응 개념]]

## 개념

ORM이 연관 데이터를 조회할 때 **쿼리를 N+1번 실행**하는 현상.
목록 1번 조회(1) + 각 항목의 연관 데이터 조회(N) = **총 N+1번**.

게시글 100개의 작성자를 출력하면 → 101번 쿼리.

## 왜 발생하나

ORM의 Lazy Loading 때문. 연관 객체(`post.author`)에 접근하는 순간 그때서야 DB를 조회함. 반복문 안에서 접근하면 매 iteration마다 쿼리가 나감.

## Django에서

```python
# 문제: 게시글마다 author SELECT가 나감
posts = Post.objects.all()
for post in posts:
    print(post.author.name)  # N번 추가 쿼리

# 해결 1: select_related — SQL JOIN으로 한 방에 (ForeignKey, OneToOne)
posts = Post.objects.select_related('author').all()

# 해결 2: prefetch_related — 별도 쿼리 1번 후 Python에서 매핑 (ManyToMany, 역참조)
posts = Post.objects.prefetch_related('tags').all()
```

## JPA에서

```java
// 문제: getAuthor() 호출마다 SELECT
List<Post> posts = postRepository.findAll();
for (Post post : posts) {
    System.out.println(post.getAuthor().getName()); // N번 추가 쿼리
}

// 해결 1: JPQL JOIN FETCH
@Query("SELECT p FROM Post p JOIN FETCH p.author")
List<Post> findAllWithAuthor();

// 해결 2: EntityGraph
@EntityGraph(attributePaths = {"author"})
List<Post> findAll();
```

## JPA EAGER & Lazy

연관된 엔티티를 불러오는 방식은 Lazy와 Eager가 있는데
- Lazy : 접근할 때 되서야 조회
- Eager : 연관 엔티티도 즉시 함께 조회

```Java
// LAZY
Post post = postRepository.findById(1L).get(); // Post만 SELECT
post.getAuthor().getName();                     // 이 시점에 User SELECT

// EAGER
Post post = postRepository.findById(1L).get(); // Post + User 동시에 SELECT
post.getAuthor().getName();                     // 추가 쿼리 없음

```

EAGER는 JOIN이 아니라 ==**개별 SELECT를 즉시 실행**하는 것==임
내부적으로 개별 SELECT를 보내기 때문에 목록 조회 시 N+1이 그대로 발생하게 됨.
```java
// 게시글 100개 조회 시
// → Post SELECT 1번 + User SELECT 100번 = 101번
List<Post> posts = postRepository.findAll();
for (Post post : posts) {
    System.out.println(post.getAuthor().getName()); // N번 추가 쿼리
}

```

실제로 쓸 땐 모든 관계에서 Lazy로 사용하고, 필요할 땐 JOIN fetch를 명시적으로 사용한다고 하네요.


## Django vs JPA 비교

|            | Django               | JPA                                   |
| ---------- | -------------------- | ------------------------------------- |
| 기본 로딩 전략   | Lazy                 | `@ManyToOne` EAGER, `@OneToMany` LAZY |
| JOIN 해결법   | `select_related()`   | `JOIN FETCH` / `@EntityGraph`         |
| 별도 쿼리 해결법  | `prefetch_related()` | `@BatchSize`                          |
| N+1 인지 난이도 | 상대적으로 쉬움 (기본 Lazy)   | 어려움 (EAGER가 해결책처럼 보임)                 |
