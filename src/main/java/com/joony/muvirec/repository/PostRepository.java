package com.joony.muvirec.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.joony.muvirec.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	@Query(value="select videoId from post where view =(select max(view) from post) "
			+ "order by createTime desc limit 1",nativeQuery = true)
	String findVideo();
	
	//페이징 처리
	Optional<Page<Post>> findByUserId(int id,Pageable pageable);
	
}//interface
