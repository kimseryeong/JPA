package com.study.jpa.persistence;

import com.study.jpa.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Detach {

	//영속성 컨텍스트 분리: detach()
	public void testDetached(EntityManager em) {

		//회원 엔티티 생성 -> 비영속 상태
		Member member = new Member();
		member.setId("detachTest");
		member.setAge(5);
		
		//영속성 컨텍스트에 저장 -> 영속 상태
		em.persist(member);
		
		//회원 엔티티를 영속성 컨텍스트에서 분리 -> 준영속 상태
		//메서드 호출 순간 1차 캐시부터 쓰기 지연 SQL 저장소까지 해당 엔티티를 관리하기 위한 모든 정보가 제거됨
		em.detach(member);
		
		EntityTransaction transaction = em.getTransaction();
		transaction.commit();
	}
	
	
	//영속성 컨텍스트 초기화: clear()
	public void testClear(EntityManager em) {
		Member member = em.find(Member.class, "detachTest");
		
		//영속성 컨텍스트에 있는 모든 것이 초기화
		em.clear();
		
		//준영속 상태 -> 변경 감지가 동작하지 않음
		member.setAge(10);
	}
	
	
	//영속성 컨텍스트 종료: close()
	public void closeEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		
		transaction.begin();
		
		Member memberA = em.find(Member.class, "memberA");
		Member memberB = em.find(Member.class, "memberB");
		
		transaction.commit();
		
		em.close();
	}
}
