package com.example.project_01.controller.product.qna;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.product.qna.dao.QnaDAO;
import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;
import com.example.project_01.service.admin.board.ManageProductQnaService;
import com.example.project_01.service.product.qna.ProductQnaServiceImpl;
import com.example.project_01.util.Paging;

@Controller
public class ProductQnaController {
	@Autowired
	QnaDAO qnaDao;
	@Autowired
	ProductDAO productDao;
	@Autowired
	Paging pageService;
	@Autowired
	ProductQnaServiceImpl qnaService;
	@Autowired
	ManageProductQnaService productQnaService;
	
	//상품문의 작성 폼 페이지
	@RequestMapping(value="/product/qna/{product_idx}" , method = RequestMethod.GET)
	public String qnaGet(@PathVariable(value = "product_idx", required = false)int product_idx,
			Model model) {
		ProductEntity productEntity = productDao.selectOne(product_idx);
		model.addAttribute("productEntity",productEntity);		
		return "popup/productQnA";
	}
	
	//상품문의 등록
	@ResponseBody
	@RequestMapping(value="/product/qna", method = RequestMethod.POST)
	public void qnaPost(QnaDTO qnaDto) {
		qnaDao.insert(qnaDto);
	}
	
	//상품문의 내용 출력
	@RequestMapping("/product/qna/qnaShow")
	public String qnaShow(int currentPage, int qna_product, Model model) {
		SearchQnaDTO searchQnaDto = new SearchQnaDTO();
		searchQnaDto.setQna_product(qna_product);
		int totalRecord = qnaDao.countByProduct(qna_product);
		PageDTO pageDto = pageService.calPage(currentPage, 10, totalRecord, 5);
		List<QnaDTO> qnaList = qnaService.selectQna(currentPage, 10, searchQnaDto);
		
		model.addAttribute("qna_pageDto",pageDto);
		model.addAttribute("qnaList",qnaList);
		
		return "product/qnaTable";
	}
	
	//상품문의 비밀글 보기
	@ResponseBody
	@RequestMapping("/product/qna/showSecret")
	public QnaDTO showSecret(int qna_idx, Principal principal) {
		//비로그인 일때
		if(principal == null) 
			return new QnaDTO();
		
		QnaDTO qnaDto = qnaDao.selectOne(qna_idx, new SearchQnaDTO());
		
		//인가되지않은 사용자일 때
		if(!principal.getName().equals(qnaDto.getQna_member())) 
			return new QnaDTO();
		
		return qnaDto;
	}
	
	//상품문의 삭제
	@ResponseBody
	@RequestMapping("/product/qna/delete")
	public String deleteOne(Principal principal, int qna_idx)  {
		String msg = null;
		QnaDTO qnaDto = qnaDao.selectOne(qna_idx, new SearchQnaDTO());
		
		if(principal.getName().equals(qnaDto.getQna_member())) {
			qnaDao.deleteOne(qna_idx);
			msg = "삭제하였습니다.";
		}else {
			msg = "권한없음";
		}
		
		return msg;
	}
	
	//내가 작성한 상품문의 리스트 페이지
	@RequestMapping("/product/qna/myqna/{currentPage}")
	public String myqna(Principal principal, Model model,@PathVariable("currentPage")int currentPage) {
		String mem_id = principal.getName();
		SearchQnaDTO searchQnaDto = new SearchQnaDTO();
		searchQnaDto.setQna_member(mem_id);
		
		int totalRecord = qnaDao.countQna(searchQnaDto);
		PageDTO pageDto = pageService.calPage(currentPage, 10, totalRecord, 10);
		List<QnaDTO> qnaList = productQnaService.selectQna(currentPage, 10, searchQnaDto);
		List<ProductDTO> productList = new ArrayList<>();
		
		//상품문의에 해당하는 상품의 정보 가져오기
		for(QnaDTO qnaDto : qnaList)
			productList.add(productDao.selectProductDTO(qnaDto.getQna_product()));
		
		model.addAttribute("pageDto",pageDto);
		model.addAttribute("qnaList",qnaList);
		model.addAttribute("productList",productList);
		
		return "product/myqna";
	}
}
