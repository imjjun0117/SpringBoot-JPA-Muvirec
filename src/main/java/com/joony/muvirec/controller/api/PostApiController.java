package com.joony.muvirec.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joony.muvirec.config.auth.PrincipalDetail;
import com.joony.muvirec.dto.ResponseDto;
import com.joony.muvirec.model.Post;
import com.joony.muvirec.service.PostService;
//@RequestMapping("/api")
@RestController
public class PostApiController {

	@Autowired
	private PostService postService;
	
	/**
	 * 포스트 등록
	 * @param post
	 * @param principal
	 * @return
	 */
	@PostMapping("/api/posts")
	public ResponseDto<Integer> save(@RequestBody Post post,@AuthenticationPrincipal PrincipalDetail principal){
		
		int cnt = postService.save(post, principal.getUser());
		
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//save
	
	/**
	 * 포스트 id값을 받아 포스트를 지운다
	 * @param id
	 * @return
	 */
	@DeleteMapping("/api/posts/{id}")
	public ResponseDto<Integer> delete(@PathVariable int id){
		int cnt = postService.deleteById(id);
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//delete
	
	/** 
	 * 포스트 수정
	 * @param post
	 * @return
	 */
	@PutMapping("/api/posts/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Post post){
		int cnt = postService.udpatePost(id,post);
		return new ResponseDto<>(HttpStatus.OK.value(),cnt);
	}//update

	
	/**
	 * 인덱스 페이지 검색 기능 구현
	 * @return
	 */
	@GetMapping("/auth/api/posts")
	public Page<Post> search(String keyword,
			@PageableDefault(size = 8, sort = "createTime", direction = Sort.Direction.DESC)Pageable pageable){
		Page<Post> posts = postService.searchPost(keyword,pageable);
		return posts;
	}//search
	
}//class
