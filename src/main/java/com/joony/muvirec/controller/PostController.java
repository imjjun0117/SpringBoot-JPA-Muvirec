package com.joony.muvirec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joony.muvirec.model.Post;
import com.joony.muvirec.service.PostService;

@Controller
public class PostController {

	@Autowired
	PostService postService;
	
	@GetMapping({"/",""})
	public String main(Model model) {
		List<Post> postList = postService.findAll();
		model.addAttribute("postList",postList);
		return "index";
	}//main
	
	@GetMapping("/post/addForm")
	public String addPost() {
		return "/post/addForm";
	}//addPost
	
	@GetMapping("/posts/{id}")
	public String postDetail(@PathVariable int id, Model model) {
		Post post = postService.findById(id);
		model.addAttribute("post",post);
		return "/post/postDetail";
	}//postDetail
}
