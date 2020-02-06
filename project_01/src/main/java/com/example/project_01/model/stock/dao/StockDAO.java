package com.example.project_01.model.stock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.stock.dto.StockDTO;
@Mapper
public interface StockDAO {
	
	public void insertStock(int product_idx, int size, int count);
	public List<StockDTO> selectByProduct(int product_idx);
	public void updateStock(int product_idx, int size, int count);
	public void deleteStock(int product_idx, int size);
}
