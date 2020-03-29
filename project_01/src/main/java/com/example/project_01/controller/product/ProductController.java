package com.example.project_01.controller.product;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.category.dao.CategoryDAO;
import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.product.qna.dao.QnaDAO;
import com.example.project_01.model.product.qna.dto.QnaDTO;
import com.example.project_01.model.product.qna.dto.SearchQnaDTO;
import com.example.project_01.model.search.dto.SearchDTO;
import com.example.project_01.model.stock.dao.StockDAO;
import com.example.project_01.model.stock.dto.StockDTO;
import com.example.project_01.service.product.ProductService;
import com.example.project_01.service.product.qna.QnaService;

@Controller
public class ProductController {
	@Autowired
	ProductDAO productDao;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryDAO categoryDao;
	@Autowired
	StockDAO stockDao;
	@Autowired
	QnaDAO qnaDao;
	@Autowired
	QnaService qnaService;
	@RequestMapping("/product/list/{idx}")
	public String productList(Model model, @PathVariable(value = "idx", required = false) int idx,
			SearchDTO searchDto, String searchWord) {
		searchDto.setProduct_isDisplay("1");
		System.out.println(searchDto.getSort());
		if(searchWord != null) { //검색어를 통해 요청한 경우
			searchDto.setOption(1);
			searchDto.setProduct_brand("%"+searchWord+"%");
			searchDto.setProduct_category("%"+searchWord+"%");
			searchDto.setProduct_name("%"+searchWord+"%");
		}			
		PageDTO pageDto = productService.calPage(idx, searchDto);
		List<ProductDTO> productList = productService.selectProduct(idx, searchDto);	
		model.addAttribute("productList",productList);
		model.addAttribute("pageDto", pageDto);
		model.addAttribute("searchDto",searchDto);

		if(searchWord == null) //카테고리를 통해 요청한 경우 
			model.addAttribute("category", categoryDao.getCategoryName(
				Integer.parseInt(searchDto.getProduct_category())));
		else  //검색어를 통해 요청한 경우
			model.addAttribute("searchWord",searchWord);
		System.out.println(searchDto);
		return "productList";
	}
	
	@RequestMapping("/product/detail")
	public String productDetail(String product_idx, Model model) {
		SearchDTO searchDto = new SearchDTO();
		searchDto.setProduct_idx(product_idx);
		ProductDTO productDto = productDao.selectProduct(0, 1, searchDto).get(0);
		List<StockDTO> stockList = stockDao.selectByProduct(productDto.getProduct_idx());
		model.addAttribute("productDto",productDto);
		model.addAttribute("stockList", stockList);
		return "productDetail";
	}
	
	@RequestMapping(value="/product/qna/{product_idx}" , method = RequestMethod.GET)
	public String qnaGet(@PathVariable(value = "product_idx", required = false)int product_idx,
			Model model) {
		ProductEntity productEntity = productDao.selectOne(product_idx);
		model.addAttribute("productEntity",productEntity);		
		return "popup/productQnA";
	}
		
	@ResponseBody
	@RequestMapping(value="/product/qna", method = RequestMethod.POST)
	public void qnaPost(QnaDTO qnaDto) {
		qnaDao.insert(qnaDto);
	}
	
	@RequestMapping("/product/qnaShow")
	public String qnaShow(int currentPage, int qna_product, Model model) {
		PageDTO pageDto = qnaService.calPage(currentPage, qna_product);
		model.addAttribute("qna_pageDto",pageDto);
		int start = (currentPage-1)*10;
		List<QnaDTO> qnaList = qnaDao.selectQnaByProduct(start, 10, qna_product);
		model.addAttribute("qnaList",qnaList);
		return "qnaTable";
	}
	
	@ResponseBody
	@RequestMapping("/product/showSecret")
	public QnaDTO showSecret(int qna_idx, Principal principal) {
		if(principal == null) return new QnaDTO();
		QnaDTO qnaDto = qnaDao.selectOne(qna_idx, new SearchQnaDTO());
		if(!principal.getName().equals(qnaDto.getQna_member())) return new QnaDTO();
		System.out.println(qnaDto);
		System.out.println(principal.getName());
		return qnaDto;
	}
	@ResponseBody
	@RequestMapping("/product/qna/delete")
	public void deleteOne(Principal principal, int qna_idx) {
		QnaDTO qnaDto = qnaDao.selectOne(qna_idx, new SearchQnaDTO());
		if(principal.getName().equals(qnaDto.getQna_member())) {
			qnaDao.deleteOne(qna_idx);
		}
	}
}
