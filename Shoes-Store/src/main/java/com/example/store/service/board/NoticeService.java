package com.example.store.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store.model.notice.dao.NoticeDAO;
import com.example.store.model.notice.dto.NoticeDTO;

@Service
public class NoticeService {
	@Autowired
	NoticeDAO noticeDao;
	
	//페이지당 레코드 수 = 15
	public List<NoticeDTO> selectNotice(String notice_type, int currentPage, int size){
		int start = (currentPage-1) * size;
		int length = size;
		List<NoticeDTO> noticeList = noticeDao.selectByType(notice_type, start, length);		
		return noticeList;
	}
	
}
