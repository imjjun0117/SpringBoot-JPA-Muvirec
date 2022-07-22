package com.joony.muvirec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {
	
	/**
	 * 로그인 페이지 이동 
	 * @return
	 */
	@GetMapping("/auth/loginForm")
	public String loginForm() { 
		return "user/loginForm";
	}//loginForm
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}//joinForm

	@GetMapping("/user/updateForm")
	public String myPage() {
		return "user/updateForm";
	}//myPage

	
}//class
