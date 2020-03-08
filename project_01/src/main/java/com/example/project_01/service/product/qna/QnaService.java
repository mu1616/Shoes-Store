package com.example.project_01.service.product.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.qna.dao.QnaDAO;

@Service
public class QnaService {
	@Autowired
	QnaDAO qnaDao;
	//페이지당 10개, 1블럭당 페이지 5개
	public PageDTO calPage(int currentPage, int qna_product) {
		int countRecord;
		int startPage;
		int endPage;
		int totalPage;
		PageDTO pageDto= new PageDTO();
		pageDto.setCurrentPage(currentPage);
		countRecord = qnaDao.count(qna_product);
		pageDto.setCountRecord(countRecord);
		totalPage = (int)Math.ceil(countRecord/(double)10);
		pageDto.setTotalPage(totalPage);
		startPage = (currentPage-1)/5*5+1;
		pageDto.setStartPage(startPage);
		endPage = (startPage+4>totalPage)?totalPage:startPage+4;
		pageDto.setEndPage(endPage);
		return pageDto;
	}
}
