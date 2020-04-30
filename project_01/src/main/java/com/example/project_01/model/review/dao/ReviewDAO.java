package com.example.project_01.model.review.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.review.dto.ReviewDTO;

@Mapper
public interface ReviewDAO {
	//리뷰 작성
	public void insert(ReviewDTO reviewDto);
	//review_ordercode 에 맞는 레코드 Select
	public ReviewDTO selectOne(String review_ordercode);
	//특정 상품 리뷰횟수 조회
	public int countByProduct(int review_product);
}
