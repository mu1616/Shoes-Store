package com.example.project_01.service.product.qna;

import java.util.List;

import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;

public interface ProductQnaService {
	public List<QnaDTO> selectQna(int currentPage, int size, SearchQnaDTO searchQnaDto);
}
