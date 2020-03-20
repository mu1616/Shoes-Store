package com.example.project_01.controller.admin.board;

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
import com.example.project_01.model.product.qna.dao.QnaDAO;
import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;
import com.example.project_01.service.admin.board.ManageBoardService;

@Controller
public class ManageBoardController {
	@Autowired
	ManageBoardService boardService;
	@Autowired
	QnaDAO qnaDao;
	@Autowired
	ProductDAO productDao;
	
	@RequestMapping("/admin/board/qna/{idx}")
	public String qnaList(@PathVariable("idx")int idx, Model model, SearchQnaDTO searchQnaDto) {
		if(searchQnaDto.getQna_member().equals(""))
			searchQnaDto.setQna_member("%");
		PageDTO pageDto = boardService.calPage(idx, searchQnaDto);
		List<QnaDTO> qnaList = boardService.selectQna(idx, searchQnaDto);
		model.addAttribute("pageDto",pageDto);
		model.addAttribute("qnaList",qnaList);
		if(searchQnaDto.getQna_member().equals("%"))
			searchQnaDto.setQna_member("");
		model.addAttribute("searchQnaDto",searchQnaDto);
		return "admin_productQna";
	}
	@RequestMapping(value= "/admin/board/qna/detail", method=RequestMethod.GET)
	public String qnaDetail(int qna_idx,int qna_product, Model model) {
		ProductDTO productDto = productDao.selectProductDTO(qna_product);
		model.addAttribute("productDto",productDto);	
		QnaDTO qnaDto = qnaDao.selectOne(qna_idx, new SearchQnaDTO());
		model.addAttribute("qnaDto",qnaDto);
		return "popup/qnaDetail";
	}
	@ResponseBody
	@RequestMapping(value= "/admin/board/qna/detail", method=RequestMethod.POST)
	public void updateAnswer(int qna_idx, String qna_answer) {
		qnaDao.updateAnswer(qna_idx, qna_answer);	
	}
	@ResponseBody
	@RequestMapping("/admin/board/qna/delete")
	public void deleteOne(int qna_idx) {
		qnaDao.deleteOne(qna_idx);
	}
}
