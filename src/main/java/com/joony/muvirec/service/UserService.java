package com.joony.muvirec.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joony.muvirec.model.Post;
import com.joony.muvirec.model.RoleType;
import com.joony.muvirec.model.User;
import com.joony.muvirec.repository.PostRepository;
import com.joony.muvirec.repository.UserRepository;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private BCryptPasswordEncoder encoder; //security 인코더 의존성 주입
	
	@Transactional
	public int save(User user) {
		int cnt = 0;
		if(user != null) {
			
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
		User respUser = userRepository.findByUsername(username).orElse(null);
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
		if(user != null ) {
			tempUser.setUsername(user.getUsername());
			tempUser.setEmail(user.getEmail());
			tempUser.setPassword(encoder.encode(user.getPassword())); //인코딩 후 저장
			// JPA에서 영속화된 데이터에 변화를 감지하면 자동으로 update
			cnt=1; // update 완료시 1을 반환한다.
		}//end if
		return cnt;
	}//update
	
	
	@Transactional(readOnly = true)
	public List<Post> findByUserId(int id){
		
		List<Post> posts = postRepository.findByUserIdOrderByCreateTimeDesc(id).orElseGet(null);
		return posts;
	}//findByUserId
	
}//class