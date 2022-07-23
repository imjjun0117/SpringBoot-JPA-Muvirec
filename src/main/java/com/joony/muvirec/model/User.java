package com.joony.muvirec.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java Object 테이블로 맵핑


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@DynamicInsert // null인 필드를 insert 시에 제외시킨다.
@Entity // User 클래스가 MySQL에 테이블이 생성된다
public class User {
	
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)// 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username;
	
	@Column(nullable = false, length = 200)// 123456 -> 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//private String oauth; // oauth 사용자 판별
	
	// @ColumnDefault("'user'")
	// DB는 RoleType이라는게 없다 어노테이션으로 String 설정
	@Enumerated(EnumType.STRING)
	@JoinColumn(name="userId")
	private RoleType role; // Enum을 쓰는게 좋다 
	//admin,user,manager 도메인(범위)를 줄 수 있어 휴먼에러 감소
	
	
	@CreationTimestamp // 시간을 자동으로 입력
	private Timestamp createDate;
	
}
