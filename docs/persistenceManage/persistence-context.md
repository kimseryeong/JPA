# Persistence Context (영속성 컨텍스트)

엔티티를 영구 저장하는 환경

<br>

엔티티 매니저를 통해 엔티티를 저장, 조회하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관하고 관리함

```
persist();
```

<br>

## 📌 영속성 컨텍스트 특징

### 1️⃣ 영속성 컨텍스트와 식별자 값

영속성 컨텍스트는 엔티티를 식별자 값으로 구분하기 때문에 영속 상태는 반드시 식별자 값이 있어야 함
<br> (식별자 값이 없으면 예외 발생)
<br>

### 2️⃣ 영속성 컨텍스트와 데이터베이스 저장

JPA 가 트랜잭션을 **커밋**하는 순간 영속성 컨텍스트에 새로 저장된 엔티티를 DB에 반영 (==Flush)
<br>

### 3️⃣ 장점

### ✔️ 1차 캐시 (엔티티 내부에 존재하는 캐시, 키는 식별자 값)

**1차 캐시**

![1차 캐시](/docs/images/firstLevelCache.png)

```
//EntityManager.find() 메서드 정의
public <T> T find(Class<T> entityClass, Object primaryKey);

em.find(Member.class, "member1");

// -> 호출하면 먼저 1차 캐시에서 엔티티를 찾고 없으면 데이터베이스에서 조회
```

<br>

**1차 캐시에서 조회**

![1차 캐시에서 조회](/docs/images/selectFromFirstLevelCache.png)

```
Member member = new Member();
member.setId("member1");
member.setUserName("테스트");

//1차 캐시에 저장됨
em.persist(member);

//1차 캐시에서 조회
Member findMember = em.find(Member.class, "member1");
```

<br>

**DB에서 조회**

![DB에서 조회](/docs/images/selectFromDB.png)

-> 1차 캐시에 엔티티가 없는 경우 DB에서 조회
<br> -> 조회한 데이터로 엔티티 생성 후 1차 캐시에 저장 (영속 상태)
<br> -> 조회한 엔티티 반환

```
Member findMember2 = em.find(Member.class, "member2");
```

<br>

### ✔️ 동일성 보장

```
Member a = em.find(Member.class, "member1");
Member b = em.find(Member.class, "member1");

System.out.println(a == b); //동일성 비교 결과는 true
```

-> 반복해서 호출해도 영속성 컨텍스트는 1차 캐시에 있는 같은 엔티티 인스턴스를 반환 


### ✔️ 트랜잭션을 지원하는 쓰기 지연

-> 데이터베이스에 보낼 SQL 쿼리를 차곡차곡 모아두고 commit() 이 실행되면 데이터베이스에 한 번에 전달 
<br> -> 성능 최적화

```
begin(); 

save(A);
save(B);
save(C);

commit();
```


### ✔️ 변경 감지 (dirty checking)

-> 엔티티의 변경사항을 데이터베이스에 자동으로 반영하는 기능
<br> JPA 는 엔티티를 영속성 컨텍스트에 보관할 때 최초 상태를 복사해서 저장해둠 (스냅샷)
<br> 그 후 플러시 시점에 스냅샷과 엔티티 비교하여 변경된 엔티티를 찾음

```
//필드가 너무 많거나 저장되는 내용이 너무 크면 수정된 데이터만 동저긍로 UPDATE 하는 전략
//하이버네이트 확장
@org.hibernate.annotation.DynamicUpdate
```


### ✔️ 지연 로딩



