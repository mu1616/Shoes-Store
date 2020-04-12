package com.example.project_01.model.order.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.order.dto.OrderDTO;

@Mapper
public interface OrderDAO {
	//주문 추가
	public void insertOrder(OrderDTO orderDto);
	//사용자 아이디로 주문내역 조회
	public List<OrderDTO> selectById(String mem_id, String startDate, String endDate);
	//주문코드로 주문내역 조회
	public OrderDTO selectByCode(String order_code);
	//주문코드에 해당하는 주문 내역 삭제
	public void deleteByCode(String order_code);
}
