package com.example.store.model.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.store.model.order.dto.OrderDTO;
import com.example.store.model.order.dto.SearchOrderDTO;
//주문 테이블
@Mapper
public interface OrderDAO {
	//주문정보 삽입
	public void insertOrder(OrderDTO orderDto);
	
	//사용자 아이디로 주문내역 조회
	public List<OrderDTO> selectById(String mem_id, String startDate, String endDate);
	
	//주문코드로 주문내역 조회
	public OrderDTO selectByCode(String order_code);
	
	//주문코드에 해당하는 주문 내역 삭제
	public void deleteByCode(String order_code);
	
	//주문의 개수 조회
	public int countOrder(SearchOrderDTO searchOrderDto);
	
	//조건에 맞는 주문 검색
	public List<OrderDTO> selectOrder(int start, int size, SearchOrderDTO searchOrderDto);
	
	//주문 상태 변경
	public void updateState(String order_code, String order_state);
	
	//merchant_uid 에 맞는 주문내역 조회
	public List<OrderDTO> selectByMerchantUid(String merchant_uid);
}
