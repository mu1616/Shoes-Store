package com.example.store.controller.product;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.store.model.category.dao.CategoryDAO;
import com.example.store.model.pagination.dto.PageDTO;
import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.product.dto.ProductDTO;
import com.example.store.model.product.dto.ProductEntity;
import com.example.store.model.product.qna.dao.QnaDAO;
import com.example.store.model.product.qna.dto.QnaDTO;
import com.example.store.model.product.qna.dto.SearchQnaDTO;
import com.example.store.model.search.dto.SearchDTO;
import com.example.store.model.stock.dao.StockDAO;
import com.example.store.model.stock.dto.StockDTO;
import com.example.store.service.product.ProductService;
import com.example.store.service.product.qna.ProductQnaServiceImpl;
import com.example.store.util.Paging;

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
	ProductQnaServiceImpl qnaService;
	@Autowired
	Paging pageService;
	
	//상품 리스트 페이지
	@RequestMapping("/product/list/{idx}")
	public String productList(Model model, @PathVariable(value = "idx", required = false) int currentPage,
			SearchDTO searchDto, String searchWord) {
		searchDto.setProduct_isDisplay("1");
		
		if(searchWord != null) { //검색어를 통해 요청한 경우
			searchDto.setOption(1);
			searchDto.setProduct_brand("%"+searchWord+"%");
			searchDto.setProduct_category("%"+searchWord+"%");
			searchDto.setProduct_name("%"+searchWord+"%");
		}			
		
		int totalRecord = productDao.countProduct(searchDto);
		PageDTO pageDto = pageService.calPage(currentPage, 20, totalRecord, 10);
		List<ProductDTO> productList = productService.selectProduct(currentPage, 20, searchDto);
		
		model.addAttribute("productList",productList);
		model.addAttribute("pageDto", pageDto);
		model.addAttribute("searchDto",searchDto);

		if(searchWord == null) //카테고리를 통해 요청한 경우 
			model.addAttribute("category", searchDto.getProduct_category());
		else  //검색어를 통해 요청한 경우
			model.addAttribute("searchWord",searchWord);
		
		return "product/productList";
	}
	
	//상품 상세정보 페이지
	@RequestMapping("/product/detail")
	public String productDetail(String product_idx, Model model) {
		SearchDTO searchDto = new SearchDTO();
		searchDto.setProduct_idx(product_idx);
		ProductDTO productDto = productDao.selectProduct(0, 1, searchDto).get(0);
		List<StockDTO> stockList = stockDao.selectByProduct(productDto.getProduct_idx());
		
		model.addAttribute("productDto",productDto);
		model.addAttribute("stockList", stockList);
		
		return "product/productDetail";
	}
}
