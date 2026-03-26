---
created: 2026-03-26
tags: []
status: seed
---

# JPA 영속성 컨텍스트에서 왜 save가 생략이 되는가

## 영속성 컨텍스트 (Persistence Context)

> 테이블과 매핑된 Entity 객체를 **관리/보관**하는 역할
> 스프링에서는 트랜잭션을 사용하면 영속성 컨텍스트가 생겨나고, 트랜잭션이 종료되면 영속성 컨텍스트도 종료된다
### 변경 감지 (Dirty Checking)
영속성 컨텍스트 안에서 불러와진 Entity는 명시적으로 `save()`를 해주지 않아도 변경을 감지하여 저장

```java
@Transactional
public void updateUser(UserUpdateRequest request) {
    User user = userRepository.findById(request.getId())
        .orElseThrow(IllegalArgumentException::new);
    user.updateName(request.getName());
    userRepository.save(user); // 생략 가능! 트랜잭션 commit 시 UPDATE 자동 실행
}
```

> [!note]
> 왜 save를 생략해도 되는가?
> `@Transactional` 어노테이션의 범위는 매서드 단위! 즉, 이 경우엔 updateUser내에서만 영속성 컨텍스트가 유지되는것.
> 이때 영속성 컨텍스트는 엔티티를 불러올 때 원본 스냅샷을 따로 보관하게 되는데, 끝났을 때 시점과 비교해서 달라진 필드만 Update를 하게 됨.
> 
> 따라서 DB에서 조회(영속성 컨택스트 생성) 후 필드 수정시엔 save를 생략해도 됨

### 2. 엔티티 상태와 save() 필요 여부

| 상태 | 설명 | save() 필요 | 변경 감지 |
|---|---|---|---|
| **비영속** | `new`로 생성, 컨텍스트에 없음 | O | X |
| **영속** | `save()` 또는 `findBy~`로 등록됨 | X | O |
| **준영속** | `@Transactional` 밖으로 나온 엔티티 | O | X |
| **삭제** | `delete()` 호출됨 | - | - |

새로 만든 객체는 영속성 컨텍스트에 등록되지 않은 **비영속 상태**라 `save()` 없이는 DB에 반영되지 않음.

```java
// 비영속 — save() 필수
User user = new User("eunho");
userRepository.save(user); // INSERT + 영속 상태 등록

// save() 이후도 영속 상태 → 변경 감지 동작
user.setName("changed");
// 트랜잭션 커밋 시 flush → "changed"로 INSERT 한 번만 나감
// ("eunho"로 INSERT 후 UPDATE가 아니라, 최종 상태로 한 번에 처리)
```

> [!note]
> `save()`의 역할은 트랜잭션 커밋이 아니라 **비영속 → 영속 상태 전환**.
> 실제 INSERT/UPDATE는 flush(트랜잭션 커밋 직전)에 최종 상태로 한꺼번에 실행됨.




