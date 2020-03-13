package com.example.project_01.controller.cart;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.cart.dao.CartDAO;
import com.example.project_01.model.cart.dto.CartDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;

@Controller
public class CartController {
	@Autowired
	CartDAO cartDao;
	@Autowired
	ProductDAO productDao;
	@ResponseBody
	@RequestMapping("/cart/insert")
	public String insertCart(@RequestBody CartDTO [] cartList, Principal principal) {
		for(CartDTO cartDto : cartList) {
			cartDto.setCart_member(principal.getName());
			cartDao.insertCart(cartDto);
		}
		return "hi";
	}
	
	@RequestMapping("/cart/list")
	public String cartList(Principal principal, Model model) {
		List<CartDTO> cartList = cartDao.selectCart(principal.getName());
		for(int i=0; i<cartList.size(); i++) {
			ProductDTO productDto = productDao.selectProductDTO(cartList.get(i).getCart_product());
			cartList.get(i).setProduct_brand(productDto.getProduct_brand());
			cartList.get(i).setProduct_image(productDto.getProduct_image());
			cartList.get(i).setProduct_name(productDto.getProduct_name());
			cartList.get(i).setProduct_price(productDto.getProduct_price());
		}
		model.addAttribute("cartList",cartList);
		return "cartList";
	}
	
}
