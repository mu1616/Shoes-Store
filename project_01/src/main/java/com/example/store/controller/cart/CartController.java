package com.example.store.controller.cart;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.store.model.cart.dao.CartDAO;
import com.example.store.model.cart.dto.CartDTO;
import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.product.dto.ProductDTO;
import com.example.store.service.cart.CartService;

@Controller
public class CartController {
	@Autowired
	CartDAO cartDao;
	@Autowired
	ProductDAO productDao;
	@Autowired
	CartService cartService;

	// 장바구니에 상품 추가
	@ResponseBody
	@RequestMapping("/cart/insert")
	public String insertCart(@RequestBody CartDTO[] cartList, Principal principal) {
		cartService.insertCart(cartList, principal.getName());
		return "hi";
	}

	// 장바구니 리스트 페이지
	@RequestMapping("/cart/list")
	public String cartList(Principal principal, Model model) {
		List<CartDTO> cartList = cartDao.selectCart(principal.getName());

		for (int i = 0; i < cartList.size(); i++) {
			ProductDTO productDto = productDao.selectProductDTO(cartList.get(i).getCart_product());
			cartList.get(i).setProduct_brand(productDto.getProduct_brand());
			cartList.get(i).setProduct_image(productDto.getProduct_image());
			cartList.get(i).setProduct_name(productDto.getProduct_name());
			cartList.get(i).setProduct_price(productDto.getProduct_price());
		}

		model.addAttribute("cartList", cartList);

		return "cart/cartList";
	}

	// 장바구니 상품 삭제시 처리
	@ResponseBody
	@RequestMapping("/cart/deleteProduct")
	public void deleteProduct(@RequestParam("idx") String[] idx, Principal principal) {
		CartDTO cartDto;
		if (idx != null) {
			cartDto = cartDao.selectOne(Integer.parseInt(idx[0]));
			// 삭제하려는 사람이 현재로그인중인 사용자가 맞는지 확인
			if (cartDto.getCart_member().equals(principal.getName()))
				cartService.deleteProduct(idx);
		}
	}
}
