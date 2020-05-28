package com.example.store.service.admin.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.store.model.product.qna.dao.QnaDAO;
import com.example.store.model.product.qna.dto.QnaDTO;
import com.example.store.model.product.qna.dto.SearchQnaDTO;
import com.example.store.service.product.qna.ProductQnaService;

@Service
@Qualifier("ManageProductQna")
public class ManageProductQnaService implements ProductQnaService{
	@Autowired
	QnaDAO qnaDao;
	
	@Override
	public List<QnaDTO> selectQna(int currentPage, int size, SearchQnaDTO searchQnaDto) {
		int start = (currentPage-1) * size;
		int length = size;
		List<QnaDTO> qnaList = qnaDao.selectQna(start, length, searchQnaDto);
		return qnaList;
	}
}
