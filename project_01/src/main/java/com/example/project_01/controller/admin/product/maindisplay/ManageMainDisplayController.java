package com.example.project_01.controller.admin.product.maindisplay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.search.dto.SearchDTO;
import com.example.project_01.service.admin.product.maindisplay.ManageMainDisplayService;

@Controller
public class ManageMainDisplayController {
	@Autowired
	ProductDAO productDao;
	@Autowired
	ManageMainDisplayService MainDisplayService;
	
	
	@RequestMapping("/admin/product/mainDisplay")
	public String mainDisplay(@RequestParam(value="display_md", defaultValue="1")int display_md,
			Model model) {
		List<ProductDTO> productList = productDao.selectProductByDisplay(display_md);
		model.addAttribute("productList",productList);
		model.addAttribute("category",display_md);
		return "admin/admin_mainDisplay";
	}
	
	@RequestMapping(value="/admin/product/mainDisplay/add", method=RequestMethod.GET)
	public String mainDisplayAdd(int display_md, Model model) {
		model.addAttribute("display_md",display_md);
		return "popup/mainDisplayAdd";
	}
	
	@RequestMapping(value="/admin/product/mainDisplay/add/search", method=RequestMethod.POST)
	public String mainDisplayAdd_Search(String searchOption, String searchWord, Model model) {
		SearchDTO searchDto = new SearchDTO();
		if(searchOption.equals("상품명"))
			searchDto.setProduct_name("%"+searchWord+"%");
		if(searchOption.equals("상품번호"))
			searchDto.setProduct_idx(searchWord);
		List<ProductDTO> productList = productDao.selectProduct(0, 20, searchDto);
		model.addAttribute("productList",productList);
		return "popup/searchList";
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/mainDisplay/add/save")
	public void mainDisplayAdd_save(int []product_idx, int display_md) {
		for(int idx : product_idx)
			productDao.addProductMainDisplay(idx, display_md);
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/mainDisplay/save")
	public void mainDisplay_save(int []product_idx, int display_md) {
		MainDisplayService.saveDisplay(display_md, product_idx);
	}
}
