package com.joony.muvirec.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joony.muvirec.dto.RatingSaveRepositoryDto;
import com.joony.muvirec.dto.ResponseDto;
import com.joony.muvirec.service.RatingService;

@RequestMapping("/api")
@RestController
public class RatingApiController {

	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/posts/{postId}/ratings")
	public ResponseDto<Integer> save(@RequestBody RatingSaveRepositoryDto ratingDto) {
		int cnt = ratingService.save(ratingDto);
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//save
	
}//class
