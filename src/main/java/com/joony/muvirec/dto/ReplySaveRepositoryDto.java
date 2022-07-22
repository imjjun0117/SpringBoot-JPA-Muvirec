package com.joony.muvirec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveRepositoryDto {

	private int postId;
	private int userId;
	private String content;
}//class
