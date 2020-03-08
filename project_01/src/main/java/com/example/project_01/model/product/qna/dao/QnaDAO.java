package com.example.project_01.model.product.qna.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.product.qna.dto.QnaDTO;

@Mapper
public interface QnaDAO {
	public void insert(QnaDTO qnaDto);
	public List<QnaDTO> selectQna(int start, int length, int qna_product);
	public int count(int qna_product);
	public QnaDTO selectOne(int idx, int qna_product);
}
