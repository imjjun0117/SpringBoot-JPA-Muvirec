package com.joony.muvirec.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private int id;
	
	private String title;
	
	private String singer;
	
	private String description;
	
	private String videoId;
	
	private String tag; // 해시태그용 컬럼
	
	private int rating;
	
	private int view;
	
	private Timestamp createTime;
}
