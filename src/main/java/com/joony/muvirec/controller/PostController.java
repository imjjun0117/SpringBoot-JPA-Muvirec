package com.joony.muvirec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joony.muvirec.config.auth.PrincipalDetail;
import com.joony.muvirec.model.Post;
import com.joony.muvirec.service.PostService;
import com.joony.muvirec.service.RatingService;

@Controller
public class PostController {

	@Autowired
	PostService postService;
	@Autowired
	RatingService ratingService;
	
	@GetMapping({"/",""})
	public String main(Model model,String keyword,
			@PageableDefault(size = 8, sort = "createTime", direction = Sort.Direction.DESC)Pageable pageable) {
		Page<Post> posts = postService.findAll(pageable);
		 
		int startPage = ((pageable.getPageNumber())/5)*5+1; // 보여줄 페이지 수 5개일 때 시작 페이지 
		//끝 페이지가 전체 페이지 수보다 클 경우 첫페이지+4 아니면 전체 페이지를 반환
		int endPage = startPage+4 > posts.getTotalPages()? posts.getTotalPages() : startPage+4; 
		
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		
		model.addAttribute("posts",posts);
		model.addAttribute("videoId",postService.findVideo());
		return "index";
	}//main
	
	@GetMapping("/posts/add-form")
	public String addPost() {
		return "/post/addForm";
	}//addPost
	
	@GetMapping("/posts/{id}/update-form")
	public String updatePost(@PathVariable int id,Model model) {
		Post post = postService.findById(id);
		model.addAttribute("post",post);
		return "/post/updateForm";
	}//addPost
	
	
	@GetMapping("/posts/{id}")
	public String postDetail(@PathVariable int id, Model model,@AuthenticationPrincipal PrincipalDetail principal) {
		Post post = postService.findById(id);
		postService.updateView(id);
		model.addAttribute("post",post);
		model.addAttribute("rating",ratingService.findRating(principal.getUser().getId(), id));
		model.addAttribute("avgRating",ratingService.findAvgRating(id));
		return "/post/postDetail";
	}//postDetail
	
}//class
