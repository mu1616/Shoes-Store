package com.example.project_01.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.cart.dto.CartDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.stock.dao.StockDAO;

@Service
public class OrderService {
	@Autowired
	StockDAO stockDao;
	@Autowired
	ProductDAO productDao;
	
	//품절 체크
	public List<CartDTO> checkSoldOut(int[] size, int[] product, int[] count){
		List<CartDTO> soldOutList = new ArrayList<>();
		for(int i=0; i<product.length; i++) {
			int stock = stockDao.getStock(product[i], size[i]);
			if(stock < count[i]) {
				CartDTO dto = new CartDTO();
				dto.setCart_count(stock);
				dto.setCart_size(size[i]);
				dto.setProduct_name(productDao.selectOne(product[i]).getProduct_name());
				soldOutList.add(dto);
			}
		}
		return soldOutList;
	}
}
