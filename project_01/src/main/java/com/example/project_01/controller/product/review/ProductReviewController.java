package com.example.project_01.controller.product.review;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.review.dao.ReviewDAO;
import com.example.project_01.model.review.dto.ReviewDTO;
import com.example.project_01.service.product.review.ProductReviewService;

@Controller
public class ProductReviewController {
	@Autowired
	ProductDAO productDao;
	@Autowired
	OrderDAO orderDao;
	@Autowired
	ReviewDAO reviewDao;
	@Autowired
	ProductReviewService productReviewService;
	@RequestMapping("/product/review/writeForm/{order_code}")
	public String writeForm(@PathVariable("order_code")String order_code, Model model) {
		ProductDTO productDto = productDao.selectProductDTO(orderDao.selectByCode(order_code).getProduct_idx());
		model.addAttribute("productDto",productDto);
		model.addAttribute("order_code",order_code);
		return "popup/reviewForm";
	}
	
	@ResponseBody
	@RequestMapping("/product/review/write/{order_code}")
	public void write(@PathVariable("order_code")String order_code, String review_contents, int review_rating,
			Principal principal) {
		OrderDTO orderDto = orderDao.selectByCode(order_code);
		//구매정보와 현재 로그인한 사용자 정보가 다르다면 return
		if(!orderDto.getMem_id().equals(principal.getName())) return;
		ReviewDTO reviewDto = new ReviewDTO();
		reviewDto.setReview_contents(review_contents);
		reviewDto.setReview_product(orderDto.getProduct_idx());
		reviewDto.setReview_rating(review_rating);
		reviewDto.setReview_member(principal.getName());
		reviewDto.setReview_ordercode(order_code);
		productReviewService.insert(reviewDto);
	}
	
	//이미 리뷰를 작성했는지 판단
	@ResponseBody
	@RequestMapping("/product/review/check/{order_code}")
	public int checkReview(@PathVariable("order_code")String order_code) {
		int check;
		ReviewDTO reviewDto = reviewDao.selectOne(order_code);
		if(reviewDto == null)
			check = 1;
		else 
			check = 0;
		return check;
	}
	
}
