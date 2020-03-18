package com.example.project_01.model.product.qna.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;

@Mapper
public interface QnaDAO {
	public void insert(QnaDTO qnaDto);
	public List<QnaDTO> selectQnaByProduct(int start, int length, int qna_product);
	public int countByProduct(int qna_product);
	public List<QnaDTO> selectQna(int start, int length, SearchQnaDTO searchQnaDto);
	public int countQna(SearchQnaDTO searchQnaDto);
	public QnaDTO selectOne(int qna_idx, SearchQnaDTO searchQnaDto);
	public void updateAnswer(int qna_idx, String qna_answer);
	public void deleteOne(int qna_idx);
}
