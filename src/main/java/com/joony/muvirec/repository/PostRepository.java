package com.joony.muvirec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.joony.muvirec.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	@Modifying
	@Query(value = "UPDATE POST SET VIEW =?1 WHERE ID=?2",nativeQuery = true)
	int updateView(int view,int id);
	
}//interface
