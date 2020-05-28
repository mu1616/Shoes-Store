package com.example.store.controller.admin.product.maindisplay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.product.dto.ProductDTO;
import com.example.store.model.search.dto.SearchDTO;
import com.example.store.service.admin.product.maindisplay.ManageMainDisplayService;

@Controller
public class ManageMainDisplayController {
	@Autowired
	ProductDAO productDao;
	@Autowired
	ManageMainDisplayService MainDisplayService;
	
	//메인진열 관리 페이지
	@RequestMapping("/admin/product/mainDisplay")
	public String mainDisplay(@RequestParam(value="display_md", defaultValue="1")int display_md,
			Model model) {
		List<ProductDTO> productList = productDao.selectProductByDisplay(display_md);
		model.addAttribute("productList",productList);
		model.addAttribute("category",display_md);
		return "admin/admin_mainDisplay";
	}
	
	//메인진열 상품 추가하기위한 검색 폼 페이지
	@RequestMapping(value="/admin/product/mainDisplay/add", method=RequestMethod.GET)
	public String mainDisplayAdd(int display_md, Model model) {
		model.addAttribute("display_md",display_md);
		return "popup/mainDisplayAdd";
	}
	
	//진열할 상품 검색요청시 처리
	@RequestMapping(value="/admin/product/mainDisplay/add/search", method=RequestMethod.POST)
	public String mainDisplayAdd_Search(String searchOption, String searchWord, Model model) {
		SearchDTO searchDto = new SearchDTO();
		if(searchOption.equals("상품명"))
			searchDto.setProduct_name("%"+searchWord+"%");
		if(searchOption.equals("상품번호"))
			searchDto.setProduct_idx(searchWord);
		searchDto.setProduct_isDisplay("1");
		List<ProductDTO> productList = productDao.selectProduct(0, 20, searchDto);
		model.addAttribute("productList",productList);
		return "popup/searchList";
	}
	
	//메인진열 상품 추가시 처리
	@ResponseBody
	@RequestMapping("/admin/product/mainDisplay/add/save")
	public void mainDisplayAdd_save(int []product_idx, int display_md) {
		for(int idx : product_idx)
			productDao.addProductMainDisplay(idx, display_md);
		
	}
	
	//메인진열 정보 저장
	@ResponseBody
	@RequestMapping("/admin/product/mainDisplay/save")
	public void mainDisplay_save(int []product_idx, int display_md) {
		MainDisplayService.saveDisplay(display_md, product_idx);
	}
}
