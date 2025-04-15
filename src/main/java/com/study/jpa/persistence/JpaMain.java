package com.study.jpa.persistence;

import java.util.List;

import com.study.jpa.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class JpaMain {

	public static void main(String[] args) {
		
		//[엔티티 매니저 팩토리] - 생성
		// 애플리케이션 전체를 공유
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		
		//[엔티티 매니저] - 생성
		// 엔티티를 저장, 수정, 삭제, 조회하는 등의 엔티티와 관련된 모든 일을 처리
		// (엔티티 매니저 == 엔티티를 관리하는 관리자)
		// 여러 쓰레드가 동시에 접근하면 동시성 문제 발생 -> 스레드 간 공유는 절대 금지
		EntityManager em = emf.createEntityManager();
		
		//[트랜잭션] - 획득
		// 엔티티 매니저는 데이터 변경 시 트랜잭션을 시작해야 함
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin(); //트랜잭션 - 시작
			
			logic(em); //비즈니스 로직 실행
			
			tx.commit(); //트랜잭션 - 커밋
			//커밋하는 순간 데이터베이스에 비즈니스 로직(SQL)을 보낸다
		}
		catch(Exception e) {
			tx.rollback(); //트랜잭션 - 롤백
		}
		finally {
			em.close(); //엔티티 매니저 - 종료
		}
		
		emf.close(); //엔티티 매니저 팩토리 - 종료
	}
	
	//비즈니스 로직
	public static void logic(EntityManager em) {
		//Member 엔티티 생성 후 엔티티 매니저를 통해 DB에 CRUD
		
		String id = "testId";
		Member member = new Member();
		member.setId(id);
		member.setUserName("test");
		member.setAge(20);
		
		//등록
		em.persist(member);
		
		//수정
		//JPA는 어떤 엔티티가 변경되었는지 추적하는 기능을 갖추고 있기 때문에 값만 변경해주면 됨.
		member.setAge(26);
		
		//한 건 조회
		//조회할 엔티티 타입과 @Id 로 데이터베이스 테이블의 기본키와 매핑한 식별자 값으로 엔티티 하나를 조회
		Member findMember = em.find(Member.class, id);
		System.out.println("findMember: " + findMember.getUserName() + ", age: " + findMember.getAge());
		
		//목록 조회
		List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
		System.out.println("members.size: " + members.size());
		
		//JPQL(select m from Member m)을 통해 검색조건 포함된 SQL 사용
		//from Member: Member 엔티티 객체를 의미
		//JPQL은 대소문자 명확히 구분
		TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
		List<Member> memberList = query.getResultList();

		//삭제
		em.remove(member);
		
	}
	
}
