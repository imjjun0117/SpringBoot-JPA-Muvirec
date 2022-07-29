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
	
//	@Query(value = "select p.*, round(avg(r.rating),1) as rating from post p "
//			+ "left join rating r on r.postId=p.id group by p.id order by createTime desc",nativeQuery = true)
//	List<Post> findPost();
//	
	Optional<Page<Post>> findByTitleContainsOrTagContains(String keyword,String keyword2,Pageable pageable);
}//interface