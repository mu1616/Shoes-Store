package com.example.project_01.model.stock.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.project_01.model.stock.dto.StockDTO;
//재고 테이블
@Mapper
public interface StockDAO {
	//재고 등록
	public void insertStock(int product_idx, int size, int count);
	
	//특정 상품의 재고정보 select
	public List<StockDTO> selectByProduct(int product_idx);
	
	//재고 추가
	public void addStock(int product_idx, int size, int add);
	
	//재고 삭제
	public void deleteStock(int product_idx, int size);
	
	//특정 상품,사이즈의 재고 select
	public int getStock(int product_idx, int size);
	
	//재고 수정
	public void updateStock(int product_idx, int size, int count);
}
