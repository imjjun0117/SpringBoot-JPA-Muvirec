package com.joony.muvirec.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joony.muvirec.config.auth.PrincipalDetail;
import com.joony.muvirec.dto.ReplySaveRepositoryDto;
import com.joony.muvirec.dto.ResponseDto;
import com.joony.muvirec.model.Post;
import com.joony.muvirec.model.Reply;
import com.joony.muvirec.service.PostService;
@RequestMapping("/api")
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
	@PostMapping("/posts")
	public ResponseDto<Integer> save(@RequestBody Post post,@AuthenticationPrincipal PrincipalDetail principal){
		
		int cnt = postService.save(post, principal.getUser());
		
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//save
	
	/**
	 * 포스트 id값을 받아 포스트를 지운다
	 * @param id
	 * @return
	 */
	@DeleteMapping("posts/{id}")
	public ResponseDto<Integer> delete(@PathVariable int id){
		int cnt = postService.deleteById(id);
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//delete
	
	/** 
	 * 포스트 수정
	 * @param post
	 * @return
	 */
	@PutMapping("/posts/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Post post){
		int cnt = postService.udpatePost(id,post);
		return new ResponseDto<>(HttpStatus.OK.value(),cnt);
	}//update
	
	@PostMapping("/posts/{postId}/replys")
	public ResponseDto<Integer> saveReply(@RequestBody ReplySaveRepositoryDto rDto){
		int cnt = postService.saveReply(rDto);
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//saveReply
	
	@GetMapping(path = "/posts/{postId}/replys",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Reply> getReplyList(@PathVariable int postId ){
		
		List<Reply> replyList = null;
		Post post = postService.findById(postId);
		replyList = post.getReplys();
		
		return replyList;
	}//getReplyList
	
	@DeleteMapping("/replys/{replyId}")
	public ResponseDto<Integer> deleteReply(@PathVariable int replyId){
		postService.deleteReply(replyId);
		return new ResponseDto<>(HttpStatus.OK.value(),1);
	}//deleteReply
	
}//class
