package com.joony.muvirec.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joony.muvirec.dto.ReplySaveRepositoryDto;
import com.joony.muvirec.dto.ResponseDto;
import com.joony.muvirec.model.Post;
import com.joony.muvirec.model.Reply;
import com.joony.muvirec.service.PostService;
import com.joony.muvirec.service.ReplyService;
@RequestMapping("/api")
@RestController
public class ReplyApiController {
	
	@Autowired
	private ReplyService replyService;
	@Autowired
	private PostService postService;
	
	/**
	 * 댓글 등록
	 * @param rDto
	 * @return
	 */
	@PostMapping("/posts/{postId}/replys")
	public ResponseDto<Integer> saveReply(@RequestBody ReplySaveRepositoryDto rDto){
		int cnt = replyService.saveReply(rDto);
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//saveReply
	
	@GetMapping(path = "/posts/{postId}/replys",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Reply> getReplyList(@PathVariable int postId ){
		
		List<Reply> replyList = null;
		Post post = postService.findById(postId);
		replyList = post.getReplys();
		
		return replyList;
	}//getReplyList
	
	@DeleteMapping("/posts/{postId}/replys/{replyId}")
	public ResponseDto<Integer> deleteReply(@PathVariable int replyId){
		replyService.deleteReply(replyId);
		return new ResponseDto<>(HttpStatus.OK.value(),1);
	}//deleteReply
}
