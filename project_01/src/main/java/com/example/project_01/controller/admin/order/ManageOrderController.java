package com.example.project_01.controller.admin.order;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;
import com.example.project_01.model.order.dto.SearchOrderDTO;
import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.service.admin.order.ManageOrderService;
import com.example.project_01.util.Paging;

@Controller
public class ManageOrderController {
	@Autowired
	ManageOrderService orderService;
	@Autowired
	OrderDAO orderDao;
	@Autowired
	Paging pageService;
	
	//주문관리 페이지
	@RequestMapping("/admin/order/list/{page}")
	public String orderList(SearchOrderDTO searchOrderDto, @PathVariable(value = "page")int currentPage,
			Model model) {
		int totalRecord = orderDao.countOrder(searchOrderDto);
		PageDTO pageDto = pageService.calPage(currentPage, 10, totalRecord, 10);
		List<OrderDTO> orderList = orderService.orderList(currentPage, 10, searchOrderDto);
		
		model.addAttribute("pageDto",pageDto);
		model.addAttribute("orderList",orderList);
		model.addAttribute("searchOrderDto",searchOrderDto);
		
		return "admin/admin_orderList";
	}
	
	//주문상태 변경시 처리
	@ResponseBody
	@RequestMapping("/admin/order/updateState")
	public void updateState(String order_code, String order_state) {
		orderService.updateState(order_code, order_state);
	}
	
	//주문 상세정보
	@ResponseBody
	@RequestMapping("/admin/order/detail")
	public OrderDTO orderDetail(String order_code) {
		OrderDTO orderDto = orderDao.selectByCode(order_code);
		orderDto.setDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderDto.getOrder_date())));
		return orderDto;
	}
}
