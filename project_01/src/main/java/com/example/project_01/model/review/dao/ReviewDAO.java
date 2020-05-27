package com.example.project_01.model.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.review.dto.ReviewDTO;

@Mapper
public interface ReviewDAO {
	//리뷰 작성
	public void insert(ReviewDTO reviewDto);
	
	//review_ordercode 에 맞는 레코드가져오기
	public ReviewDTO selectOne(String review_ordercode);
	
	//특정 상품 리뷰횟수 조회
	public int countByProduct(int review_product);
	
	//특정 상품의 구매후기가져오기
	public List<ReviewDTO> selectByProduct(int review_product, int start, int length);
	
	//특정 회원의 구매후기 가져오기
	public List<ReviewDTO> selectByMember(String review_member, int start, int length);
	
	//특정 회원의 구매후기 개수 가져오기
	public int countByMember(String review_member);
	
	//구매후기 삭제
	public void deleteOne(String review_ordercode);
}
