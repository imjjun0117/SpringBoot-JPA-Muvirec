package com.joony.muvirec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.joony.muvirec.config.auth.PrincipalDetailService;
import com.joony.muvirec.handler.CustomAuthFailureHandler;
@Configuration
@EnableWebSecurity //시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalDetailService principalDetailService;
	@Autowired
	private CustomAuthFailureHandler Handler; // 로그인 실패 예외를 핸들링하여 메세지를 전송
	
	//IoC 설정 encode 값을 스프링이 관리한다.
	//인코드가 되지 않은 비밀번호는 사용 불가능하다
	@Bean 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}//encodePWD
	
	//시큐리티가 대신 로그인으로 password를 가로채기를 하는데
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}//configure


	/**
	 * 로그인에서 authenticationManager는 시큐리티 세션에 세션값을 저장하는 역할을 한다
	 * 회원정보 수정 후 세션값을 authenticationManager를 통해 토큰을 받아 강제로 변경하기 위해 선언 
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	//시큐리티 기본 설정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	
		.csrf().disable() // csrf 토큰 비활성화(테스트시 걸어두는게 좋음) 
		.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**") // 접근 가능 경로 설정
			.permitAll() // 접근 가능
			.anyRequest() // antMatchers 이외의 응답은
			.authenticated() // 로그인 후 접근 가능 
		.and()
			.formLogin()
			.loginPage("/auth/login-form")
			.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인한다.
			//principalDetailService로 유저 정보를 던져준다 
			.failureHandler(Handler) // 로그인 실패시 
			.defaultSuccessUrl("/"); //정상일 경우 메인 페이지 이동 
			
	}//configure

}//class
