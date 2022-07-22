package com.joony.muvirec.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joony.muvirec.model.User;
import com.joony.muvirec.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository; // username을 통해 조회를 하기 위한 선언
	
	/**
	 * 유저네임을 받아 DB 조회를 통해 인코딩된 비밀번호를 비교한다.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username).orElseThrow(()->{
			return new IllegalArgumentException(username+"을 찾을 수 없습니다.");
		});
		
		//SecurityConfig에서 값을 비교후 시큐리티 세션에 오브젝트를 저장한다.
		return new PrincipalDetail(user);
		
	}//loadUserByUsername
	
}//class
