package com.joony.muvirec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joony.muvirec.dto.ReplySaveRepositoryDto;
import com.joony.muvirec.repository.ReplyRepository;

@Service
public class ReplyService {
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public int saveReply(ReplySaveRepositoryDto replyDto) {
		int cnt = 0;
		int userId = replyDto.getUserId();
		int postId = replyDto.getPostId();
		String content = replyDto.getContent();
		if(userId != 0 && postId != 0 && content != null && !"".equals(content)) {
			cnt = replyRepository.mSave(userId, postId, content);
		}
		return cnt;
	}//saveReply
	
	@Transactional
	public void deleteReply(int id) {
		replyRepository.deleteById(id);
	}//deleteReply
	
	
}//class
