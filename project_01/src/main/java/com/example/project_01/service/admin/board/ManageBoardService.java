package com.example.project_01.service.admin.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.qna.dao.QnaDAO;
import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;

@Service
public class ManageBoardService {
	@Autowired
	QnaDAO qnaDao;
	
	public PageDTO calPage(int currentPage, SearchQnaDTO searchQnaDto) {
		int countRecord;
		int startPage;
		int endPage;
		int totalPage;
		PageDTO pageDto = new PageDTO();
		pageDto.setCurrentPage(currentPage);
		countRecord = qnaDao.countQna(searchQnaDto);
		pageDto.setCountRecord(countRecord);
		totalPage = (int)Math.ceil(countRecord/(double)20);
		pageDto.setTotalPage(totalPage);
		//페이지버튼에서 가장 첫 페이지버튼의 숫자
		startPage = (currentPage-1)/10*10+1;
		pageDto.setStartPage(startPage);
		//페이지버튼에서 가장 마지막 페이지버튼의 숫자
		endPage = (startPage+9>totalPage)?totalPage:startPage+9;
		pageDto.setEndPage(endPage);
		return pageDto;
	}
	//페이지당 상품개수 = 20
	public List<QnaDTO> selectQna(int currentPage, SearchQnaDTO searchQnaDto) {
		int start = (currentPage-1) * 20;
		int length = 20;
		List<QnaDTO> qnaList = qnaDao.selectQna(start, length, searchQnaDto);
		return qnaList;
	}
}
