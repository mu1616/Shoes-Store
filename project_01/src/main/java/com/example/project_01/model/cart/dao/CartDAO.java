package com.example.project_01.model.cart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.cart.dto.CartDTO;

@Mapper
public interface CartDAO {
	//장바구니에 추가
	public void insertCart(CartDTO cartDto);
	
	//특정 회원의 장바구니 정보 select
	public List<CartDTO> selectCart(String cart_member);
	
	//장바구니에 담긴 특정 레코드 select
	public CartDTO selectOne(int cart_idx);
	
	//장바구니에 담긴 특정 레코드 삭제
	public void deleteOne(int cart_idx);
}
