# Flush (플러시)

영속성 컨텍스트의 변경 내용을 데이터베이스에 반영

**Flush 실행하면?**
1. 변경 감지가 동작하여 영속성 컨텍스트에 있는 모든 엔티티를 스냅샷과 비교
2. 수정된 엔티티 찾음
3. 수정된 엔티티는 수정 쿼리를 만들어 쓰기 지연 SQL 저장소에 등록
4. 쓰기 지연 SQL 저장소의 쿼릴르 데이터베이스에 전송

### 📌 Flush 실행 방법
1. `em.flush();` -> 직접 호출
    > 엔티티 매니저의 flush() 메서드를 직접 호출하여 영속성 컨텍스트를 강제로 플러시 
    > <br> 테스트나 다른 프레임워크와 JPA를 함께 사용할 때를 제외하고 거의 사용하지 않음

2. 트랜잭션 커밋 시 -> 플러시 자동 호출
3. JPQL 쿼리 실행 시 -> 플러시 자동 호출


```
javax.persistence.FlushModeType

//커밋이나 쿼리를 실행할 때 플러시 (기본값)
FlushModeType.AUTO

//커밋할 때만 플러시
FlushModeType.COMMIT

//플러시 모드 직접 설정
em.setFlushMode(FlushModeType.COMMIT);
```