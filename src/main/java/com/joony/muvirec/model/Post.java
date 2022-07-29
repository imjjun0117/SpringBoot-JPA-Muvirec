package com.joony.muvirec.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 120)
	private String title;
	
	@Column(nullable = false,length = 60)
	private String singer;
	
	@Column(nullable = false, length = 1200)
	private String description;
	
	@Column(nullable = false, length = 120)
	private String videoId;
	
	@Column(length=500)
	private String tag; // 해시태그용 컬럼
	
	@ColumnDefault("0")
	private int view;
	
	//Join용 변수 
	//mappedBy 연관관계의 주인이 아니다(난 FK가 아니에요) DB에 컬럼을 만들지 않는다 
	//reply에 있는 변수명을 적는다
	//reply 안에 board 변수가 존재함 -> 무한 참조가 발생한다.
	@OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)//cascade처리 
	@JsonIgnoreProperties({"post"})//reply 리스트를 호출할 때 무한 참조 방지
	@OrderBy("createDate desc")
	private List<Reply> replys;
	
	@OneToMany(mappedBy = "post",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"post"})
	private List<Rating> ratings;
	
	@CreationTimestamp
	private Timestamp createTime;
	
	@ManyToOne
	private User user;
	
	
}//class
