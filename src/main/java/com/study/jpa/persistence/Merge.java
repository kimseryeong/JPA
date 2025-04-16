package com.study.jpa.persistence;

import com.study.jpa.entity.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Merge {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
	
	public static void main(String args[]) {
		Member member = createMember("memberA", "회원1");
		
		member.setUserName("firstMem"); //준영속 상태에서 변경
		
		mergeMember(member);
	}

	
	static Member createMember(String id, String userName) {
		EntityManager em1 = emf.createEntityManager();
		EntityTransaction tx1 = em1.getTransaction();
		
		tx1.begin();
		
		Member member = new Member();
		member.setId(id);
		member.setUserName(userName);
		
		em1.persist(member);
		
		tx1.commit();
		
		//==영속성 컨텍스트1 종료== -> member 엔티티는 준영속 상태가 됨
		em1.close(); 
		
		return member;
	}
	
	static void mergeMember(Member member) {
		EntityManager em2 = emf.createEntityManager();
		EntityTransaction tx2 = em2.getTransaction();
		
		tx2.begin();
		
		Member mergeMember = em2.merge(member);
		// -> 준영속 상태인 member 엔티티의 식별자 값으로 1차 캐시에서 엔티티 조회
		// -> 만약 1차 캐시에 엔티티가 없다면 데이터베이스에서 엔티티 조회 후 1차 캐시에 저장
		// -> 조회한 영속 엔티티에 member 엔티티 값을 채워 넣음
		
		//위 코드 대신 아래 코드로 변경
		//(member 엔티티와 mergeMember 엔티티는 서로 다른 인스턴스이므로)
		member = em2.merge(member);
		
		tx2.commit();
		
		//준영속
		System.out.println("member = " + member.getUserName());
		
		//영속
		System.out.println("mergeMember = " + mergeMember.getUserName());
		
		System.out.println("em2 contains member = " + em2.contains(member));
		System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember));
		
	
		//==영속성 컨텍스트2 종료==
		em2.close();

		
		
		//비영속 상태도 merge() 가능
		Member transientMember = new Member();
		Member transiented = em2.merge(transientMember);
		tx2.commit();
	}
}
