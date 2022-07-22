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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joony.muvirec.config.auth.PrincipalDetail;
import com.joony.muvirec.dto.ReplySaveRepositoryDto;
import com.joony.muvirec.dto.ResponseDto;
import com.joony.muvirec.model.Post;
import com.joony.muvirec.model.Reply;
import com.joony.muvirec.service.PostService;

@RestController
public class PostApiController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/posts")
	public ResponseDto<Integer> save(@RequestBody Post post,@AuthenticationPrincipal PrincipalDetail principal){
		
		int cnt = postService.save(post, principal.getUser());
		
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//save
	
	@PostMapping("/posts/{postId}/replys")
	public ResponseDto<Integer> saveReply(@RequestBody ReplySaveRepositoryDto rDto){
		postService.saveReply(rDto);
		return new ResponseDto<>(HttpStatus.CREATED.value(),1);
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
		return new ResponseDto<>(HttpStatus.CREATED.value(),1);
	}//deleteReply
	
}//class
