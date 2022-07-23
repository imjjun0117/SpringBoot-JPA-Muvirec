package com.joony.muvirec.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.OrderBy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.joony.muvirec.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	@Query(value="select videoId from post where view =(select max(view) from post) "
			+ "order by createTime desc limit 1",nativeQuery = true)
	String findVideo();
	Optional<List<Post>> findByUserIdOrderByCreateTimeDesc(int id);
	
}//interface
