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
import org.springframework.web.bind.annotation.RequestParam;

import com.joony.muvirec.config.auth.PrincipalDetail;
import com.joony.muvirec.model.Post;
import com.joony.muvirec.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	/**
	 * 로그인 페이지 이동 
	 * @return
	 */
	@GetMapping("/auth/login-form")
	public String loginForm(@RequestParam(value = "exception",required = false) String exception, 
			Model model) {
		model.addAttribute("exception", exception);
		return "user/loginForm";
	}//loginForm
	
	@GetMapping("/auth/join-form")
	public String joinForm() {
		return "user/joinForm";
	}//joinForm

	@GetMapping("/users/update-form")
	public String updateForm() {
		return "user/updateForm";
	}//updateForm

	@GetMapping("/users/my-post")
	public String myPost(Model model,@AuthenticationPrincipal PrincipalDetail principal,
			@PageableDefault(size = 4, sort = "createTime", direction = Sort.Direction.DESC)Pageable pageable) {
		Page<Post> posts =  userService.findByUserId(principal.getUser().getId(),pageable);
		int startPage = ((pageable.getPageNumber())/5)*5+1; // 보여줄 페이지 수 5개일 때 시작 페이지 
		//끝 페이지가 전체 페이지 수보다 클 경우 첫페이지+4 아니면 전체 페이지를 반환
		int endPage = startPage+4 > posts.getTotalPages()? posts.getTotalPages() : startPage+4; 
		
		model.addAttribute("posts",posts);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		
		return "user/myPost";
	}//myPost
	
}//class
