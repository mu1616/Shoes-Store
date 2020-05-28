package com.example.store.service.admin.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store.model.notice.dao.NoticeDAO;
import com.example.store.model.notice.dto.NoticeDTO;
import com.example.store.model.product.qna.dto.QnaDTO;

@Service
public class ManageNoticeService {
	@Autowired
	NoticeDAO noticeDao;
	
	//페이지당 레코드 수 = 20
	public List<NoticeDTO> selectNotice(int currentPage, int size){
		int start = (currentPage-1) * size;
		int length = size;
		List<NoticeDTO> noticeList = noticeDao.selectNotice(start, length);		
		return noticeList;
	}
}
