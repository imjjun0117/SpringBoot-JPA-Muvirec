package com.joony.muvirec.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joony.muvirec.dto.ResponseDto;
import com.joony.muvirec.model.User;
import com.joony.muvirec.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authManager;//시큐리티 세션에 저장하기 위해 AuthenticationManager 선언
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> join(@RequestBody User user){
		int cnt = userService.save(user);
		
		return new ResponseDto<>(HttpStatus.CREATED.value(),cnt);
	}//join
	
	@PostMapping("/auth/joinProc/dup")
	public ResponseDto<Integer> dupCheck(@RequestBody String username){
		int cnt = userService.dupCheck(username);
		return new ResponseDto<>(HttpStatus.OK.value(),cnt);
	}//dupChekc
	
	@PutMapping("/users")
	public ResponseDto<Integer> update(@RequestBody User user){
		int cnt = userService.update(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐지만
		// 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세견값을 변경해줄것임
		//세션 등록
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<>(HttpStatus.OK.value(),cnt);
	}//update
	
}//class
