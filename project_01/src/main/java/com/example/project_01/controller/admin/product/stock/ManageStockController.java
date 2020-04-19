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
	
	@RequestMapping("/admin/product/stock")
	public String showStock(Model model, int product_idx) {
		List<StockDTO> stockList = stockDao.selectByProduct(product_idx);
		model.addAttribute("stockList", stockList);
		model.addAttribute("product_idx",product_idx);
		ProductEntity productEntity = productDao.selectOne(product_idx);
		model.addAttribute("productEntity",productEntity);
		return "popup/stock";
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/add")
	public void addStock(int product_idx, int size, int count) {
		stockDao.insertStock(product_idx, size, count);		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/modify")
	public void modifyStock(int product_idx, int size, int count) {
		stockDao.updateStock(product_idx, size, count);
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/delete")
	public void deleteStock(int product_idx, int size) {
		stockDao.deleteStock(product_idx, size);
		
	}
}
