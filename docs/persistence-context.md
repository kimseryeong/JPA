# Persistence Context (영속성 컨텍스트)

엔티티를 영구 저장하는 환경

<br>

엔티티 매니저를 통해 엔티티를 저장, 조회하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관하고 관리함

```
persist()
```

<br>

## 📌 엔티티 생명주기

|상태|설명|
|--|--|
|비영속(new/transient)|영속성 컨텍스트와 전혀 관계가 없는 상태|
|영속(managed)|영속성 컨텍스트에 저장된 상태|
|준영속(detached)|영속성 컨텍스트에 저장되었다가 분리된 상태|
|삭제(removed)|삭제된 상태|

<br>

#### ✔️ 비영속 (new/transient)

엔티티 객체 생성 후 아직 저장하지 않은 상태. 순수한 객체 상태

```
Member member = new Member();
member.setId("testId");
member.setUserName("테스트");
```

#### ✔️ 영속 (managed)

엔티티 매니저를 통해 엔티티를 영속성 컨텍스트에 저장함. <br>
영속성 컨텍스트가 관리하는 엔티티를 영속 상태라 함.

```
em.persist(member);
```


#### ✔️ 준영속 (detached)

영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준영속 상태가 됨.

```
em.detach(member);
em.close();
em.clear();
```


#### ✔️ 삭제 (removed)

엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제함

```
em.remove(member);
```

<br>


## 📌 영속성 컨텍스트 특징

### 1️⃣ 영속성 컨텍스트와 식별자 값

<br> 영속성 컨텍스트는 엔티티를 식별자 값으로 구분하기 때문에 영속 상태는 반드시 식별자 값이 있어야 함
<br> (식별자 값이 없으면 예외 발생)
<br>

### 2️⃣ 영속성 컨텍스트와 데이터베이스 저장

<br> JPA 가 트랜잭션을 **커밋**하는 순간 영속성 컨텍스트에 새로 저장된 엔티티를 DB에 반영 (==Flush)
<br>

### 3️⃣ 장점

#### ✔️ 1차 캐시 (엔티티 내부에 존재하는 캐시, 키는 식별자 값)

![1차 캐시](/docs/images/firstLevelCache.png)

```
//EntityManager.find() 메서드 정의
public <T> T find(Class<T> entityClass, Object primaryKey);

em.find(Member.class, "member1");

// -> 호출하면 먼저 1차 캐시에서 엔티티를 찾고 없으면 데이터베이스에서 조회
```

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

![DB에서 조회](/docs/images/selectFromDB.png)

-> 1차 캐시에 엔티티가 없는 경우 DB에서 조회
<br> -> 조회한 데이터로 엔티티 생성 후 1차 캐시에 저장 (영속 상태)
<br> -> 조회한 엔티티 반환

```
Member findMember2 = em.find(Member.class, "member2");
```

#### ✔️ 동일성 보장

```
Member a = em.find(Member.class, "member1");
Member b = em.find(Member.class, "member1");

System.out.println(a == b); //동일성 비교 결과는 true
```

-> 반복해서 호출해도 영속성 컨텍스트는 1차 캐시에 있는 같은 엔티티 인스턴스를 반환 


#### ✔️ 트랜잭션을 지원하는 쓰기 지연

-> `transaction.commit();` 을 하기 전까지 데이터베이스에 보낼 SQL 쿼리를 차곡차곡 모아 둠


#### ✔️ 변경 감지
#### ✔️ 지연 로딩



