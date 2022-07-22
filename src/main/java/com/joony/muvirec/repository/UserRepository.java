package com.joony.muvirec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joony.muvirec.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	
	//naming Query : select * from user where username = ?1;
	//스프링 시큐리티 DB에 저장된 비밀번호를 들고오기 위해..
	Optional<User> findByUsername(String username);
	
}//class
