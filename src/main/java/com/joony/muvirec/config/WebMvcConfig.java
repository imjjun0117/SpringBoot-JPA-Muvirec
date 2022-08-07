package com.joony.muvirec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig {
	//데이터를 그대로 저장하고 Response시에 치환한다
	
    private final ObjectMapper mapper;
    // JSON -> Object 또는 Object -> JSON에서 ObjectMapper가 사용
    // 치환과정 : Object -> characterEscapeConverter(치환) -> JSON  

    
    @Bean//Spring에서 자동으로 converter를 실행시키기 위해 Bean 등록
    public MappingJackson2HttpMessageConverter characterEscapeConverter() {
        ObjectMapper objectMapper = mapper.copy();
        objectMapper.getFactory().setCharacterEscapes(new XssProtectSupport());
        return new MappingJackson2HttpMessageConverter(objectMapper);
        //치환된 mapper를 반환한다.
    }//characterEscapeConverter

}//class