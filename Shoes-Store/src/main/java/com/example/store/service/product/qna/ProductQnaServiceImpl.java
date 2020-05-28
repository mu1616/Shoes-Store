package com.example.store.service.product.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store.model.product.qna.dao.QnaDAO;
import com.example.store.model.product.qna.dto.QnaDTO;
import com.example.store.model.product.qna.dto.SearchQnaDTO;

@Service
public class ProductQnaServiceImpl implements ProductQnaService{
	@Autowired
	QnaDAO qnaDao;
	
	@Override
	public List<QnaDTO> selectQna(int currentPage, int size, SearchQnaDTO searchQnaDto) {
			int start = (currentPage-1) * size;
			int length = size;
			List<QnaDTO> qnaList = qnaDao.selectQnaByProduct(start, length, searchQnaDto.getQna_product());
			return qnaList;
		}
}
