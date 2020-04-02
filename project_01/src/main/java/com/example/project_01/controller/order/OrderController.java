package com.example.project_01.controller.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project_01.model.cart.dto.CartDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.stock.dao.StockDAO;
import com.example.project_01.service.order.OrderService;

@Controller
public class OrderController {
	@Autowired
	StockDAO stockDao;
	@Autowired
	ProductDAO productDao;
	@Autowired
	OrderService orderService;
	@RequestMapping("/order/orderForm")
	public String orderForm(int[] size, int[] count, int[] product, Model model) {
		List<CartDTO> soldOutList = orderService.checkSoldOut(size, product, count);
		//품절인 상품이 존재할 때
		if(soldOutList.size() != 0) {
			model.addAttribute("soldOutList",soldOutList);
			for(CartDTO dto : soldOutList)
				System.out.println(dto);
			return "soldOut";
		}
		//품절인 상품이 없을 때
		return "orderForm";
	}
}
