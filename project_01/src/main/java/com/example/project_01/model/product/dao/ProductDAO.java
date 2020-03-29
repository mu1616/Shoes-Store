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
	//ProductEntity 반환
	public ProductEntity selectOne(int product_idx);
	public void updateProduct(ProductEntity productEntity, int product_idx);
	//ProductDTO 반환
	public ProductDTO selectProductDTO(int product_idx);
	public List<ProductDTO> selectProductByDisplay(int display_md);
	//메인진열 상품추가
	public void addProductMainDisplay(int product_idx, int display_md);
	//메인진열 상품삭제(모든상품)
	public void delProductMainDisplay(int display_md);
	
}
