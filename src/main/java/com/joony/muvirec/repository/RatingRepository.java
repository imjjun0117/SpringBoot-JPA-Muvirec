package com.joony.muvirec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.joony.muvirec.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
	
	@Modifying//없으면 select로 인식한다
	@Query(value="INSERT INTO RATING(userId,postId,rating) values(?1,?2,?3)",nativeQuery = true)
	int mSave(int userId,int postId,int rating); // 업데이트된 행의 개수를 리턴해줌
	
	Rating findByUserIdAndPostId(int userId, int postId);
	
	@Query(value = "select round(avg(rating),1) from rating where postId=?1",nativeQuery = true)
	Float findAvg(int postId);
	
	
}//class
