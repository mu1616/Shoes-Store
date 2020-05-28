package com.example.store.service.product.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.review.dao.ReviewDAO;
import com.example.store.model.review.dto.ReviewDTO;

@Service
public class ProductReviewService {
	@Autowired
	ReviewDAO reviewDao;
	@Autowired
	ProductDAO productDao;

	// 리뷰 작성
	@Transactional
	public void insert(ReviewDTO reviewDto) {
		reviewDao.insert(reviewDto);
		productDao.updateReviewCount(reviewDto.getReview_product(), 1);
		productDao.updateRating(reviewDto.getReview_product());
	}
	
	//특정 상품 구매후기 가져오기
	public List<ReviewDTO> selectByProduct(int currentPage, int size, int review_product) {
		int start = (currentPage - 1) * size;
		List<ReviewDTO> reviewList = reviewDao.selectByProduct(review_product, start, size);
		return reviewList;
	}

	//특정 회원 구매후기 가져오기
	public List<ReviewDTO> selectByMember(int currentPage, int size, String review_member) {
		int start = (currentPage - 1) * size;
		List<ReviewDTO> reviewList = reviewDao.selectByMember(review_member, start, size);
		return reviewList;
	}

	@Transactional
	public void deleteReview(ReviewDTO reviewDto) {
		reviewDao.deleteOne(reviewDto.getReview_ordercode());
		productDao.updateReviewCount(reviewDto.getReview_product(), -1);
		
		//리뷰작성자가 0명이라면 0으로 나누지 못함 
		if (productDao.selectOne(reviewDto.getReview_product()).getProduct_reviewcount() == 0) {
			productDao.initRating(reviewDto.getReview_product()); //rating 을 0으로 초기화
		} else {
			productDao.updateRating(reviewDto.getReview_product());
		}
	}
}
