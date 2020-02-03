package com.example.project_01.model.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;

@Mapper
public interface ProductDAO {
	public void productRegister(ProductEntity ProductDto);
	public void displayRegister(int product_idx, int md_idx);
	public int countProduct();
	public List<ProductDTO> selectProduct(int start, int length);
}
