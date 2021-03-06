package com.example.store.controller.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.product.dto.ProductDTO;

@Controller
public class HomeController {
	@Autowired
	ProductDAO productDao;
	
	//메인 페이지
	@RequestMapping("/")
	public String home(Model model) {
		List<ProductDTO> bestProducts = productDao.selectProductByDisplay(1);
		List<ProductDTO> saleProducts = productDao.selectProductByDisplay(2);
		List<ProductDTO> newProducts = productDao.selectProductByDisplay(3);
		model.addAttribute("bestProducts",bestProducts);
		model.addAttribute("saleProducts",saleProducts);
		model.addAttribute("newProducts",newProducts);
		
		return "home";
	}
	
	//관리자 페이지
	@RequestMapping("/admin")
	public String admin() {
		return "redirect:/admin/product/register";
	}
	
	//인가되지 않은 요청
	@RequestMapping("/access-denied")
	public String accessDenied() {
		return "error/accessError";
	}
	
	@RequestMapping("/test")
	public String test() {
		return "test";
	}
}
