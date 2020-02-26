package com.example.project_01.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project_01.model.category.dao.CategoryDAO;
import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.search.dto.SearchDTO;
import com.example.project_01.service.product.ProductService;

@Controller
public class ProductController {
	@Autowired
	ProductDAO productDao;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryDAO categoryDao;
	
	@RequestMapping("/productList/{idx}")
	public String productList(Model model, @PathVariable(value = "idx", required = false) int idx,
			SearchDTO searchDto, String searchWord) {
		if(searchWord != null) { //검색어를 통해 요청한 경우
			searchDto.setSearchOption(1);
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
		return "productList";
	}
}
