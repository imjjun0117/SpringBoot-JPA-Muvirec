package com.joony.muvirec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.joony.muvirec.model.Post;
import com.joony.muvirec.model.User;
import com.joony.muvirec.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	
	@Transactional
	public Page<Post> findAll(Pageable pageable) {
		return postRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public String findVideo() {
		return postRepository.findVideo();
	}//findVideo
	
	@Transactional
	public int save(Post post, User user) {
		int cnt = 0;
		if (post != null && user != null) {
			post.setUser(user);
			postRepository.save(post);
			cnt = 1;
		}//end if
		return cnt;
	}// save

	@Transactional
	public int udpatePost(int id, Post post) {
		int cnt = 0;
		if(post != null && id != 0) {
			
		Post respPost = postRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("포스트를 찾을 수 없습니다");
				});//영속화
		respPost.setTitle(post.getTitle());
		respPost.setDescription(post.getDescription());
		respPost.setSinger(post.getSinger());
		respPost.setVideoId(post.getVideoId());
		respPost.setTag(post.getTag());
		cnt = 1;
		}
		//update 수행
		return cnt;
	}//udpate
	
	@Transactional(readOnly = true)
	public Post findById(int id) {
		Post post = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("포스트를 찾을 수 없습니다");
		});
		return post;
	}//findById
	
	@Transactional
	public int deleteById(int id) {
		int cnt = 0;
		if(id != 0) {
			postRepository.deleteById(id);
			cnt = 1;
		}//end if
		return cnt;
	}//deleteById
	
	
	/**
	 * post 상세 페이지로 들어갈 시 조회수 추가
	 * @param view
	 * @param id
	 */
	@Transactional
	public void updateView(int id) {
		Post post = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 포스트를 찾을 수 없습니다.");
		});//영속화 완료
		
		post.setView(post.getView()+1); //update
		
	}//updateView
	
	/**
	 * 검색 기능 구현
	 * @return
	 */
	public Page<Post> searchPost(String keyword,Pageable pageable){
		Page<Post> posts =
				postRepository.findByTitleContainsOrTagContains(keyword,keyword,pageable).orElseGet(null);
		
		return posts;
	}//searchPost
	
}// class
