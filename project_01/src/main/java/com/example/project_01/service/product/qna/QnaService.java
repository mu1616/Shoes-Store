package com.example.project_01.service.product.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.qna.dao.QnaDAO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;

@Service
public class QnaService {
	@Autowired
	QnaDAO qnaDao;
	//페이지당 10개, 1블럭당 페이지 5개
	public PageDTO calPage(int currentPage, int size, int block, SearchQnaDTO searchQnaDto) {
		int countRecord;
		int startPage;
		int endPage;
		int totalPage;
		PageDTO pageDto= new PageDTO();
		pageDto.setCurrentPage(currentPage);
		countRecord = qnaDao.countQna(searchQnaDto);
		pageDto.setCountRecord(countRecord);
		totalPage = (int)Math.ceil(countRecord/(double)size);
		pageDto.setTotalPage(totalPage);
		startPage = (currentPage-1)/block*block+1;
		pageDto.setStartPage(startPage);
		endPage = (startPage+(block-1)>totalPage)?totalPage:startPage+(block-1);
		pageDto.setEndPage(endPage);
		return pageDto;
	}
}
