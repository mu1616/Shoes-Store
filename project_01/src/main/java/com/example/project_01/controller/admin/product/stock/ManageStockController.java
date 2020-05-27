package com.example.project_01.controller.admin.product.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.stock.dao.StockDAO;
import com.example.project_01.model.stock.dto.StockDTO;

@Controller
public class ManageStockController {
	@Autowired
	StockDAO stockDao;
	@Autowired
	ProductDAO productDao;
	
	//상품재고 페이지
	@RequestMapping("/admin/product/stock")
	public String showStock(Model model, int product_idx) {
		List<StockDTO> stockList = stockDao.selectByProduct(product_idx);
		model.addAttribute("stockList", stockList);
		model.addAttribute("product_idx",product_idx);
		ProductEntity productEntity = productDao.selectOne(product_idx);
		model.addAttribute("productEntity",productEntity);
		return "popup/stock";
	}
	
	//재고 생성시 처리
	@ResponseBody
	@RequestMapping("/admin/product/stock/insert")
	public void addStock(int product_idx, int size, int count) {
		stockDao.insertStock(product_idx, size, count);		
	}
	
	//재고 추가시 처리
	@ResponseBody
	@RequestMapping("/admin/product/stock/add")
	public void modifyStock(int product_idx, int size, int add) {
		stockDao.addStock(product_idx, size, add);		
	}
	
	//재고 삭제시 처리
	@ResponseBody
	@RequestMapping("/admin/product/stock/delete")
	public void deleteStock(int product_idx, int size) {
		stockDao.deleteStock(product_idx, size);
		
	}
}
