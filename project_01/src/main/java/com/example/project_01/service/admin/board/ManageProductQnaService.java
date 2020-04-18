package com.example.project_01.service.admin.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.qna.dao.QnaDAO;
import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;
import com.example.project_01.service.product.qna.QnaService;

@Service
public class ManageProductQnaService {
	@Autowired
	QnaDAO qnaDao;
	
	//페이지당 상품개수 = 20
	public List<QnaDTO> selectQna(int currentPage, int size, SearchQnaDTO searchQnaDto) {
		int start = (currentPage-1) * size;
		int length = size;
		List<QnaDTO> qnaList = qnaDao.selectQna(start, length, searchQnaDto);
		return qnaList;
	}
}
