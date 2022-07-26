package com.joony.muvirec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String main(Model model) {
		
		List<Post> postList = postService.findAll();
//		List<Post> postList = postService.find();
//		System.out.println(postList.get(0));
		model.addAttribute("postList",postList);
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
