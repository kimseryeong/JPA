package com.study.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Member {

	@Id
	@Column(name="Id")
	private String id;
	
	private String userName;
	private Integer age;
	
}
