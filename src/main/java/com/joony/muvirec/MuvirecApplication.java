package com.joony.muvirec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MuvirecApplication extends SpringBootServletInitializer{

	//war 빌드시 상속받고 오바라이딩
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}//configure

	public static void main(String[] args) {
		SpringApplication.run(MuvirecApplication.class, args);
	}//main
}//class
