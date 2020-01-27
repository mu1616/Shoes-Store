package com.example.project_01.model.product.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.product.dto.ProductDTO;

@Mapper
public interface ProductDAO {
	public void productRegister(ProductDTO ProductDto);
	public void displayRegister(int product_idx, int md_idx);
}
