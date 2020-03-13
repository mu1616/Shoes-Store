package com.example.project_01.model.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.search.dto.SearchDTO;

@Mapper
public interface ProductDAO {
	public void productRegister(ProductEntity ProductDto);
	public void displayRegister(int product_idx, int md_idx);
	public int countProduct(SearchDTO searchDto);
	public List<ProductDTO> selectProduct(int start, int length, SearchDTO searchDto);
	public void deleteProduct(String [] product_idx);
	public ProductEntity selectOne(int product_idx);
	public void updateProduct(ProductEntity productEntity, int product_idx);
	public ProductDTO selectProductDTO(int product_idx);
}
