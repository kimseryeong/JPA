package com.study.jpa.entity;

import java.beans.Transient;
import java.util.Date;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;


//JPA 가 엔티티 데이터에 접근하는 방식 지정
// -> Access 를 설정하지 않으면 @Id의 위치를 기준으로 접근 방식이 설정됨
//@Access(AccessType.FIELD) //: 필드 접근
//@Access(AccessType.PROPERTY) //: 프로퍼티 접근

@Entity
@Table(name = "MEMBER", uniqueConstraints = {
		//유니크 제약조건 설정
		@UniqueConstraint(
		name="NAME_AGE_UNIQUE"
		,columnNames= {"NAME", "AGE"}
	)
})
@Getter
@Setter
public class Member {

	@Id
	@Column(name = "Id")
	private String id;
	
	//컬럼명 및 제약조건 설정 시 사용 
	@Column(name = "Name", nullable = false, length = 10) 
	private String userName;
	
	private Integer age;
	
	
	//enum 타입 매핑 시 사용
	// -> EnumType.STRING: enum의 이름을 db에 저장
	// -> EnumType.ORDINAL: enum의 순서를 db에 저장 (0, 1)
	@Enumerated(EnumType.STRING) 
	private RoleType roleType;
	
	//날짜 타입 매핑 시 사용
	// -> TemporalType.DATE: 날짜, 데이터베이스 date 타입과 매핑
	// -> TemporalType.TIME: 시간, 데이터베이스 time 타입과 매핑
	// -> TemporalType.TIMESTAMP: 날짜와 시간, 데이터베이스 timestamp 타입과 매핑
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	//CLOB(문자), BLOB(문자 외) 타입 매핑 시 사용 (길이 제한 없음)
	@Lob 
	private String description;
	
	
	//객체에 임시로 어떤 값을 보관하고 싶을 때 사용
	//db에 저장하지 않고 조회하지도 않음
	@jakarta.persistence.Transient
	private Integer temp;
	
	
	
	
	
}
