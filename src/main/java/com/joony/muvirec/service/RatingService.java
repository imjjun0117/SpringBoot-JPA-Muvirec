package com.joony.muvirec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joony.muvirec.dto.RatingSaveRepositoryDto;
import com.joony.muvirec.model.Rating;
import com.joony.muvirec.repository.RatingRepository;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Transactional
	public int save(RatingSaveRepositoryDto ratingDto) {
		int userId = ratingDto.getUserId();
		int postId = ratingDto.getPostId();
		int cnt = 0;
		if(findRating(userId, postId) == null) { //userId,postId로 레이팅 조회 후 없을 때에만 평점을 준다.
			cnt = ratingRepository.mSave(userId, postId, ratingDto.getRating()); 
		}//end if
		
		return cnt;
	}//save
	
	@Transactional
	public Rating findRating(int userId, int postId){
		Rating ratings = ratingRepository.findByUserIdAndPostId(userId, postId);
		//System.out.println(ratings);
		return ratings;
	}//findRating
	
	
	@Transactional
	public float findAvgRating(int postId) {
		Float respAvg = ratingRepository.findAvg(postId);
		float rating = 0;
		if(respAvg != null) {
			rating = respAvg;
		}//end if
		return rating;
	}//findAvgRating
	
}//class
