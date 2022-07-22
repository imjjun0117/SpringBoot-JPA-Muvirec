package com.joony.muvirec.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.joony.muvirec.model.User;

import lombok.Getter;
//스프링 시큐리티가 로그인 요청을 가로채고 로그인 처리를 한 다음
// UserDetails 오브젝트를 스프링 고유 세션 저장소에 담는다  
@Getter // 세션값 사용을 위해 getter 선언
public class PrincipalDetail implements UserDetails{

	private User user;
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	//사용자 권한을 얻을 수 있는 method
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{
			return "ROLE_"+user.getRole();
		});
		return collectors;
	}//getAuthorities

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정 만료가 되었는지? (false : 만료)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정 잠금 상태(false : 잠금)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되었는지 (false : 만료)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화 되었는지? (true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	
}//class
