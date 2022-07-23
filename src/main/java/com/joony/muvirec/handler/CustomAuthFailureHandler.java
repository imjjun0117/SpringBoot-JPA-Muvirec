package com.joony.muvirec.handler;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		  String errorMessage ="";        
		
		  if (exception instanceof BadCredentialsException) {           
			  errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";        
		  }else if (exception instanceof InternalAuthenticationServiceException) {            
			  errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";        
		  }else if (exception instanceof AuthenticationCredentialsNotFoundException) {            
			  errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";     
		  }else {         
			  errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요."+exception.getMessage();       
		  } 
		  errorMessage = URLEncoder.encode(errorMessage,"UTF-8"); // 한글이 깨져 UTF-8로 인코딩을 해준다.
		  setDefaultFailureUrl("/auth/login-form?&exception="+errorMessage);     
		  super.onAuthenticationFailure(request, response, exception);
		
	}//onAuthenticationFailure

}//class
