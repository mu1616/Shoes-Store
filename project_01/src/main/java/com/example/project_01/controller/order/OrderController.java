package com.example.project_01.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project_01.model.cart.dto.CartDTO;

@Controller
public class OrderController {
	
	@RequestMapping("/order/orderForm")
	public String orderForm(int []size, int []count, int []product) {
		for(int s : size)
			System.out.println(s);
		for(int c : count)
			System.out.println(c);
		for(int p : product)
			System.out.println(p);
		return "orderForm";
	}
}
