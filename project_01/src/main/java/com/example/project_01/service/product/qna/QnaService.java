package com.example.project_01.service.product.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.qna.dao.QnaDAO;
import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;

@Service
public class QnaService {
	@Autowired
	QnaDAO qnaDao;
	
	//페이지당 상품개수 = 10
	public List<QnaDTO> selectQna(int currentPage, int size, SearchQnaDTO searchQnaDto) {
			int start = (currentPage-1) * size;
			int length = size;
			List<QnaDTO> qnaList = qnaDao.selectQnaByProduct(start, length, searchQnaDto.getQna_product());
			return qnaList;
		}
}
