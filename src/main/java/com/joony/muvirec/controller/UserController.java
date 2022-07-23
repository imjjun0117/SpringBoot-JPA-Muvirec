package com.joony.muvirec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joony.muvirec.config.auth.PrincipalDetail;
import com.joony.muvirec.model.KakaoProfile;
import com.joony.muvirec.model.OAuthToken;
import com.joony.muvirec.model.Post;
import com.joony.muvirec.repository.PostRepository;
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
	public String myPost(Model model,@AuthenticationPrincipal PrincipalDetail principal) {
		List<Post> posts =  userService.findByUserId(principal.getUser().getId());
		model.addAttribute("posts",posts);
		return "user/myPost";
	}//myPost
	
}//class
