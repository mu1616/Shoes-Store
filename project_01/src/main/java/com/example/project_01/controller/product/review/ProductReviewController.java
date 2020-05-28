package com.example.project_01.controller.product.review;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;
import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.review.dao.ReviewDAO;
import com.example.project_01.model.review.dto.ReviewDTO;
import com.example.project_01.service.product.review.ProductReviewService;
import com.example.project_01.util.Paging;

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
	@Autowired
	Paging pageService;
	
	//구매후기작성 페이지
	@RequestMapping("/product/review/writeForm/{order_code}")
	public String writeForm(@PathVariable("order_code")String order_code, Model model) {
		ProductDTO productDto = productDao.selectProductDTO(orderDao.selectByCode(order_code).getProduct_idx());
		
		model.addAttribute("productDto",productDto);
		model.addAttribute("order_code",order_code);
		
		return "popup/reviewForm";
	}
	
	//구매후기 작성 요청시 처리
	@ResponseBody
	@RequestMapping("/product/review/write/{order_code}")
	public void write(@PathVariable("order_code")String order_code, String review_contents, int review_rating,
			Principal principal) {
		OrderDTO orderDto = orderDao.selectByCode(order_code);
		
		//상품후기를 작성할 수 있는 권한이 없다면 return
		if(!orderDto.getMem_id().equals(principal.getName()))
			return;
		
		ReviewDTO reviewDto = new ReviewDTO();
		reviewDto.setReview_contents(review_contents);
		reviewDto.setReview_product(orderDto.getProduct_idx());
		reviewDto.setReview_rating(review_rating);
		reviewDto.setReview_member(principal.getName());
		reviewDto.setReview_ordercode(order_code);
		productReviewService.insert(reviewDto);
	}
	
	//이미 구매후기를 작성했는지 판단
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
	
	//구매후기 리스트 뿌려주기 -> 상품상세정보에 출력됨
	@RequestMapping("/product/review/show")
	public String show(int currentPage, int review_product, int totalRecord, Model model) {
		PageDTO pageDto = pageService.calPage(1, 10, totalRecord, 5);
		List<ReviewDTO> reviewList = productReviewService.selectByProduct(currentPage, 10, review_product);
		
		model.addAttribute("pageDto", pageDto);
		model.addAttribute("reviewList", reviewList);
		
		return "product/reviewTable";
	}
	
	//내가 작성한 구매후기 리스트 페이지
	@RequestMapping("/product/review/myReview/{currentPage}")
	public String myReview(@PathVariable("currentPage")int currentPage, Model model, Principal principal) {
		int totalRecord = reviewDao.countByMember(principal.getName());
		PageDTO pageDto = pageService.calPage(currentPage, 10, totalRecord, 10);
		List<ReviewDTO> reviewList = productReviewService.selectByMember(currentPage, 10, principal.getName());
		List<ProductDTO> productList = new ArrayList<>();
		
		//구매후기에 해당하는 상품의 정보 가져오기
		for(ReviewDTO reviewDto : reviewList)
			productList.add(productDao.selectProductDTO(reviewDto.getReview_product()));
		
		model.addAttribute("pageDto", pageDto);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("productList", productList);
		
		return "product/myReview";
	}
	
	//구매후기 삭제요청 시 처리
	@ResponseBody
	@RequestMapping("/product/review/delete")
	public void deleteReview(String review_ordercode, Principal principal) {
		ReviewDTO reviewDto = reviewDao.selectOne(review_ordercode);
		if(principal.getName().equals(reviewDto.getReview_member())) {
			productReviewService.deleteReview(reviewDto);
		}
			
	}
}
