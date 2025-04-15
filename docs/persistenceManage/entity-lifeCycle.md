
# Entity LifeCycle (엔티티 생명주기)

|상태|설명|
|--|--|
|비영속(new/transient)|영속성 컨텍스트와 전혀 관계가 없는 상태|
|영속(managed)|영속성 컨텍스트에 저장된 상태|
|준영속(detached)|영속성 컨텍스트에 저장되었다가 분리된 상태|
|삭제(removed)|삭제된 상태|

<br>

### ✔️ 비영속 (new/transient)

엔티티 객체 생성 후 아직 저장하지 않은 상태. 순수한 객체 상태

```
Member member = new Member();
member.setId("testId");
member.setUserName("테스트");
```

### ✔️ 영속 (managed)

엔티티 매니저를 통해 엔티티를 영속성 컨텍스트에 저장함. <br>
영속성 컨텍스트가 관리하는 엔티티를 영속 상태라 함.

```
em.persist(member);
```


### ✔️ 준영속 (detached)

영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준영속 상태가 됨.

```
em.detach(member);
em.close();
em.clear();
```


### ✔️ 삭제 (removed)

엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제함

```
em.remove(member);
```

<br>

