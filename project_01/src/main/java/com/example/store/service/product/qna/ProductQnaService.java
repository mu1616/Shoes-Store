package com.example.store.service.product.qna;

import java.util.List;

import com.example.store.model.product.qna.dto.QnaDTO;
import com.example.store.model.product.qna.dto.SearchQnaDTO;

public interface ProductQnaService {
	public List<QnaDTO> selectQna(int currentPage, int size, SearchQnaDTO searchQnaDto);
}
