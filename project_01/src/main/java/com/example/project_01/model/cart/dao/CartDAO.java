package com.example.project_01.model.cart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.cart.dto.CartDTO;

@Mapper
public interface CartDAO {
	public void insertCart(CartDTO cartDto);
	public List<CartDTO> selectCart(String cart_member);
	public CartDTO selectOne(int cart_idx);
	public void deleteOne(int cart_idx);
}
