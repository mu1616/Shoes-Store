package com.example.store.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.store.model.cart.dao.CartDAO;
import com.example.store.model.cart.dto.CartDTO;
import com.example.store.model.product.dao.ProductDAO;

@Service
public class CartService {
	@Autowired
	CartDAO cartDao;
	@Autowired
	ProductDAO productDao;
	
	@Transactional
	public void insertCart(CartDTO [] cartList, String mem_id) {
		for(CartDTO cartDto : cartList) {
			cartDto.setCart_member(mem_id);
			cartDao.insertCart(cartDto);
		}
	}
	
	@Transactional
	public void deleteProduct(String[] idx) {
		for(String cart_idx : idx)
			cartDao.deleteOne(Integer.valueOf(cart_idx));
	}
}
