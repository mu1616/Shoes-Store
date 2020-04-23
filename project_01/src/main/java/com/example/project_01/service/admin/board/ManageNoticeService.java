package com.example.project_01.service.admin.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.notice.dao.NoticeDAO;
import com.example.project_01.model.notice.dto.NoticeDTO;
import com.example.project_01.model.product.qna.dto.QnaDTO;

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
