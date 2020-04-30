package com.example.project_01.service.product.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.review.dao.ReviewDAO;
import com.example.project_01.model.review.dto.ReviewDTO;

@Service
public class ProductReviewService {
	@Autowired
	ReviewDAO reviewDao;
	@Autowired
	ProductDAO productDao;
	
	//리뷰 작성
	@Transactional
	public void insert(ReviewDTO reviewDto) {
		reviewDao.insert(reviewDto);
		productDao.updateRating(reviewDto.getReview_product(), reviewDto.getReview_rating());
		productDao.updateReviewCount(reviewDto.getReview_product(), 1);
	}
}
