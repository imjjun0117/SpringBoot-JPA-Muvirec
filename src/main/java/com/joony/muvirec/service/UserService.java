package com.joony.muvirec.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joony.muvirec.model.KakaoProfile;
import com.joony.muvirec.model.OAuthToken;
import com.joony.muvirec.model.Post;
import com.joony.muvirec.model.RoleType;
import com.joony.muvirec.model.User;
import com.joony.muvirec.repository.PostRepository;
import com.joony.muvirec.repository.UserRepository;

@Service
public class UserService {

	@Value("kakao.key")
	private String kakaoKey;
	@Value("kakao.client")
	private String client;
	
	
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private BCryptPasswordEncoder encoder; //security 인코더 의존성 주입
	
	@Transactional
	public int save(User user) {
		int cnt = 0;
		if(user != null && dupCheck(user.getUsername())!= 1) {
			
			//패스워드 인코딩 작업 수행
			String encPassword = encoder.encode(user.getPassword()); 
			
			 user.setRole(RoleType.USER);
			 user.setPassword(encPassword);
			 userRepository.save(user);
			 cnt = 1;
			 //가입 성공시 1 반환
		}//end if
		return cnt;
	}//save
	@Transactional(readOnly = true)
	public int dupCheck(String username) {
		int cnt = 0;
		User respUser = userRepository.findByUsername(username).orElseGet(()->{
			return null;
		});
		if(respUser != null) {
			cnt = 1;
		}
		return cnt;
	}//dupCheck
	
	@Transactional
	public int update(User user) {
		int cnt = 0;
		User tempUser = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저를 찾을 수 없습니다.");
		});
		if(user != null && dupCheck(user.getUsername())!= 1) {
			tempUser.setUsername(user.getUsername());
			tempUser.setEmail(user.getEmail());
			tempUser.setPassword(encoder.encode(user.getPassword())); //인코딩 후 저장
			// JPA에서 영속화된 데이터에 변화를 감지하면 자동으로 update
			cnt=1; // update 완료시 1을 반환한다.
		}//end if
		return cnt;
	}//update
	
	
	@Transactional(readOnly = true)
	public Page<Post> findByUserId(int id,Pageable pageable){
		
		Page<Post> posts = postRepository.findByUserId(id,pageable).orElseGet(()->{
			return null;
		});
		return posts;
	}//findByUserId
	
	/**
	 * code값을 받아 토큰을 구하고 사용자 정보를 반환하는 메소드
	 * @param code
	 * @return
	 */
	public KakaoProfile getKakaoProfile(String code) {
		// POST 방식으로 key=value 데이터를 요청(카카오쪽으로)
				// HttpURLConnection,Restrofit2,OkHttp,RestTemplate 라이브러리로 key=value전송 가능
				RestTemplate tkRt = new RestTemplate();
			
				//헤더값 설정
				HttpHeaders tkHeader = new HttpHeaders(); 
				tkHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
				//바디값 설정
				MultiValueMap<String, String> tkParams = new LinkedMultiValueMap<>();
				tkParams.add("grant_type", "authorization_code");
				tkParams.add("client_id", client);
				tkParams.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
				tkParams.add("code", code);
				
				//바디와 헤더를 하나의 HttpEntity로 묶는다
				HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(tkParams,tkHeader);
				
				//HttpEntity를 보내고 응답 받음
				ResponseEntity<String> response = tkRt.exchange("https://kauth.kakao.com/oauth/token "
						,HttpMethod.POST,kakaoTokenRequest, String.class); 
				
				//ObjectMapper로 JSON 데이터 받기
				ObjectMapper objectMapper = new ObjectMapper();
				OAuthToken oauthToken = null;
				
				try {
					oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}//end catch
				
				RestTemplate pfRt = new RestTemplate();
				HttpHeaders pfHeader = new HttpHeaders();
				pfHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				pfHeader.add("Authorization", "Bearer "+oauthToken.getAccess_token());//엑세스 토근을 보냄
				
				HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(pfHeader);
				
				ResponseEntity<String> kakaoProfileResponse = pfRt.exchange("https://kapi.kakao.com/v2/user/me",
						HttpMethod.POST,
						kakaoProfileRequest,
						String.class);
				
			   ObjectMapper pfObjMapper = new ObjectMapper();
			   KakaoProfile kakaoProfile = null;
			   try {
				   kakaoProfile = pfObjMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}//end catch
				return kakaoProfile;
	}//getKakaoProfile
	
	public void kakaoLogin(KakaoProfile kakaoProfile) {
		
		String username = "kakao_"+kakaoProfile.getId(); // 카카오 로그인 아이디 설정
		String password = kakaoKey+kakaoProfile.getId()/300; //임의의 비밀번호 설정
		String email = "";//이메일이 없는 경우 등록하지 않게한다.
		if(kakaoProfile.getKakao_account().getHas_email()) {
			email = kakaoProfile.getKakao_account().getEmail();
		}//end if
		
		//정보 만들기(임시)
		User kakaoUser = User.builder()
				.username(username).password(password)
				.email(email).oauth("oauth").build();
		
		User originUser = userRepository.findByUsername(username).orElseGet(()->{
			return null;
		});
		//비가입자일 경우 가입
		if(originUser == null) {
			save(kakaoUser);
		}//end if
		
		//가입자인 경우 로그인 & 세션
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),kakaoUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);	
	}//kakaLogin
	
}//class
