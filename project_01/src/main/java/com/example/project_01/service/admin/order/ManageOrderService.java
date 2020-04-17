package com.example.project_01.service.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;
import com.example.project_01.model.order.dto.SearchOrderDTO;
import com.example.project_01.model.pagination.dto.PageDTO;

@Service
public class ManageOrderService {
	@Autowired
	OrderDAO orderDao;
	
	public PageDTO calPage(int currentPage, int size, SearchOrderDTO searchOrderDto) {
		int countRecord;
		int startPage;
		int endPage;
		int totalPage;
		PageDTO pageDto = new PageDTO();
		pageDto.setCurrentPage(currentPage);
		countRecord = orderDao.countOrder(searchOrderDto);
		pageDto.setCountRecord(countRecord);
		totalPage = (int)Math.ceil(countRecord/(double)size);
		pageDto.setTotalPage(totalPage);
		startPage = (currentPage-1)/10*10+1;
		pageDto.setStartPage(startPage);
		endPage = (startPage+9>totalPage)?totalPage:startPage+9;
		pageDto.setEndPage(endPage);
		return pageDto;
	}
	public List<OrderDTO> orderList(int currentPage, int size, SearchOrderDTO searchOrderDto){
		int start = (currentPage-1) * size;
		if(searchOrderDto.getSearchWord() != null && searchOrderDto.getSearchWord().equals(""))
			searchOrderDto.setSearchWord("%");
		List<OrderDTO> orderList = orderDao.selectOrder(start, size, searchOrderDto);
		if(searchOrderDto.getSearchWord() != null && searchOrderDto.getSearchWord().equals("%"))
			searchOrderDto.setSearchWord("");
		return orderList;
	}
	public void updateState(String order_code, String order_state) {
		order_state = orderDao.selectByCode(order_code).getOrder_state();
		if(order_state.equals("배송준비중")) {
			order_state = "배송중";
		} else if(order_state.equals("배송중")) {
			order_state = "배송완료";
		} else if(order_state.equals("배송완료")) {
			order_state = "구매확정";
		}
		orderDao.updateState(order_code, order_state);
	}
}