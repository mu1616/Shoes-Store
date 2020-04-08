package com.example.project_01.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_01.model.cart.dto.CartDTO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.stock.dao.StockDAO;

@Service
public class OrderService {
	@Autowired
	StockDAO stockDao;
	@Autowired
	ProductDAO productDao;
	@Autowired
	OrderDAO orderDao;

	// 품절 체크
	public List<CartDTO> checkSoldOut(int[] size, int[] product, int[] count) {
		List<CartDTO> soldOutList = new ArrayList<>();
		for (int i = 0; i < product.length; i++) {
			int stock = stockDao.getStock(product[i], size[i]);
			if (stock < count[i]) {
				CartDTO dto = new CartDTO();
				dto.setCart_count(stock);
				dto.setCart_size(size[i]);
				dto.setProduct_name(productDao.selectOne(product[i]).getProduct_name());
				soldOutList.add(dto);
			}
		}
		return soldOutList;
	}

	public List<CartDTO> getOrderList(int[] product, int[] size, int[] count) {
		List<CartDTO> orderList = new ArrayList<>();
		for (int i = 0; i < product.length; i++) {
			ProductDTO productDto = productDao.selectProductDTO(product[i]);
			CartDTO cartDto = new CartDTO();
			cartDto.setCart_product(productDto.getProduct_idx());
			cartDto.setProduct_name(productDto.getProduct_name());
			cartDto.setCart_size(size[i]);
			cartDto.setCart_count(count[i]);
			cartDto.setProduct_brand(productDto.getProduct_brand());
			cartDto.setProduct_image(productDto.getProduct_image());
			cartDto.setProduct_price(productDto.getProduct_price());
			orderList.add(cartDto);
		}
		return orderList;
	}

	@Transactional
	public void insertOrder(int[] size, int[] count, int[] product, MemberDTO memberDto) {
		OrderDTO[] orderList = new OrderDTO[product.length];
		System.out.println(memberDto);
		for (int i = 0; i < orderList.length; i++) {
			orderList[i] = new OrderDTO();
			orderList[i].setProduct_idx(product[i]);
			orderList[i].setSize(size[i]);
			orderList[i].setCount(count[i]);
			orderList[i].setMem_id(memberDto.getMem_id());
			orderList[i].setMem_name(memberDto.getMem_name());
			orderList[i].setOrder_addr1(memberDto.getMem_addr1());
			orderList[i].setOrder_addr2(memberDto.getMem_addr2());
			orderList[i].setOrder_phone(memberDto.getMem_phone());
			orderList[i].setOrder_postcode(memberDto.getMem_postcode());
			orderList[i].setPay(productDao.selectOne(product[i]).getProduct_price() * count[i]);
		}

		for (OrderDTO orderDto : orderList) {
			orderDao.insertOrder(orderDto);
			int stock = stockDao.getStock(orderDto.getProduct_idx(), orderDto.getSize());
			stockDao.updateStock(orderDto.getProduct_idx(), orderDto.getSize(), stock - orderDto.getCount());
		}
	}
}
