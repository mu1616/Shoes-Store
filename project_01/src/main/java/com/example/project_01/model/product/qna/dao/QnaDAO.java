package com.example.project_01.model.product.qna.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;
//상품문의 테이블
@Mapper
public interface QnaDAO {
	//상품문의 등록
	public void insert(QnaDTO qnaDto);
	
	//특정 상품에 맞는 상품문의 가져오기
	public List<QnaDTO> selectQnaByProduct(int start, int length, int qna_product);
	
	//특정 상품에 맞는 상품문의 개수 가져오기
	public int countByProduct(int qna_product);
	
	//특정 조건에 맞는 상품문의 가져오기
	public List<QnaDTO> selectQna(int start, int length, SearchQnaDTO searchQnaDto);
	
	//특정 조건에 맞는 상품문의 개수 가져오기
	public int countQna(SearchQnaDTO searchQnaDto);
	
	//상품문의(한개) 가져오기
	public QnaDTO selectOne(int qna_idx, SearchQnaDTO searchQnaDto);
	
	//상품문의 수정
	public void updateAnswer(int qna_idx, String qna_answer);
	
	//상품문의 삭제
	public void deleteOne(int qna_idx);
}
