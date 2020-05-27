package com.example.project_01.controller.admin.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.qna.dao.QnaDAO;
import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;
import com.example.project_01.service.pagination.PageService;
import com.example.project_01.service.product.qna.ProductQnaService;

@Controller
public class ManageProductQnaController {
	@Autowired
	@Qualifier("ManageProductQna")
	ProductQnaService qnaService;
	@Autowired
	QnaDAO qnaDao;
	@Autowired
	ProductDAO productDao;
	@Autowired
	PageService pageService;
	//상품문의 리스트페이지
	@RequestMapping("/admin/board/qna/{idx}")
	public String qnaList(@PathVariable("idx")int currentPage, Model model, SearchQnaDTO searchQnaDto) {
		if(searchQnaDto.getQna_member().equals(""))
			searchQnaDto.setQna_member("%");
		int totalRecord = qnaDao.countQna(searchQnaDto);
		PageDTO pageDto = pageService.calPage(currentPage, 20, totalRecord, 10);
		List<QnaDTO> qnaList = qnaService.selectQna(currentPage, 20, searchQnaDto);
		model.addAttribute("pageDto",pageDto);
		model.addAttribute("qnaList",qnaList);
		if(searchQnaDto.getQna_member().equals("%"))
			searchQnaDto.setQna_member("");
		model.addAttribute("searchQnaDto",searchQnaDto);
		return "admin/admin_productQna";
	}
	//상품문의 상세정보 페이지
	@RequestMapping(value= "/admin/board/qna/detail", method=RequestMethod.GET)
	public String qnaDetail(int qna_idx,int qna_product, Model model) {
		ProductDTO productDto = productDao.selectProductDTO(qna_product);
		model.addAttribute("productDto",productDto);	
		QnaDTO qnaDto = qnaDao.selectOne(qna_idx, new SearchQnaDTO());
		model.addAttribute("qnaDto",qnaDto);
		return "popup/qnaDetail";
	}
	
	//상품문의 답변작성 요청시 처리
	@ResponseBody
	@RequestMapping(value= "/admin/board/qna/detail", method=RequestMethod.POST)
	public void updateAnswer(int qna_idx, String qna_answer) {
		qnaDao.updateAnswer(qna_idx, qna_answer);	
	}
	
	//상품문의 삭제요청 처리
	@ResponseBody
	@RequestMapping("/admin/board/qna/delete")
	public void deleteOne(int qna_idx) {
		qnaDao.deleteOne(qna_idx);
	}
	
}
